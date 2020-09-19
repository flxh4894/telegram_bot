package com.example.telegram.telegram_bot.crawler;

import com.example.telegram.telegram_bot.util.CrawligUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MyTelegramHandler extends TelegramLongPollingBot {

    private final SSGCrawler ssgCrawler;
    private final Crawler crawler;
    private final NaverCrwaler naverCrwaler;
    private final CrawligUtil crawligUtil;

    private List<String> userInfo = new ArrayList<>();
    private List<HashMap<String,String>> userSearchList = new ArrayList<>(); // chat id , CRUD 상태
    private List<HashMap<String,String>> alarmList = new ArrayList<>(); // chat id, 키워드 조합

    private static final Logger logger = LoggerFactory.getLogger(MyTelegramHandler.class);

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {

        String message = "";
        String min = "";
        String max = "";
        String user = "";

        // Callback message 생성 및 초기화
        SendMessage req = new SendMessage();

        user = update.getMessage().getChatId().toString();
        message = update.getMessage().getText();

        req.setChatId(user);
        req.enableMarkdown(true);
        
        // 봇 시작 및 종료 구분 -> 함수화 필요 갯수등...
        switch (message) {
            case "시작":
                req.setText("어서오세요! 메뉴를 선택해 주세요\uD83D\uDE0A");
                req.setReplyMarkup(crawligUtil.setCustonButton());
                break;
            case "처음으로 돌아가기 \uD83D\uDEE0":
                userInfo.remove(user);
                for(HashMap<String,String > a : userSearchList){
                    if(a.get("user").equals(user)){
                        userSearchList.remove(a);
                    }
                }

                req.setText("처음화면으로 돌아갑니다.");
                req.setReplyMarkup(crawligUtil.setCustonButton());
                break;
            case "검색으로 추가 \uD83D\uDE0A":
                userInfo.add(user); // char id 추가

                HashMap<String,String> map = new HashMap<>();
                map.put("user",user);
                map.put("stat","C");
                userSearchList.add(map);

                req.setText("검색어를 입력해 주세요.");
                req.setReplyMarkup(crawligUtil.setCustonButton());
                break;
            default:

                for(HashMap<String,String > a : userSearchList){
                    if(a.get("user").equals(user)){
                        System.out.println(a.get("stat"));
                        System.out.println(userSearchList.indexOf(a));
                    }
                }

                if(!userInfo.contains(user)) {
                    req.setText("올바른 명령어를 선택해 주세요.");
                    req.setReplyMarkup(crawligUtil.setCustonButton());
                }

                break;
        }

        if(userInfo.contains(user) & !message.equals("검색으로 추가 \uD83D\uDE0A")) {
            String[] item = message.split("ㅡ");
            boolean flag = true; // 검색어 입력 형태 체크
            //최소, 최대 입력 없을 시 추가
            if (item.length == 3) {
                min = item[1];
                max = item[2];
            } else if (item.length != 1) {
                message = "정확한 형태로 입력해 주세요. \n " +
                        "✔입력형태1 : 검색어ㅡ최소가격ㅡ최대가격 \n " +
                        "✔입력형태2 : 검색어";
                flag = false;
            }

            if (flag) {
//                // SSG 상품검색
//                List<String> list = null;
//                String index = "";
//                // 해당 고객 매체 값 불러오기
//                System.out.println(map);
//                for(HashMap<String,String> id : map){
//                    if(id.get("user").equals(user)){
//                        index = id.get("index");
//                    }
//                }
//
//                // 인덱스에 따라 분기 -> 함수화 필요
//                if(index.equals("Naver")){
//                    naverCrwaler.setKeyword(item[0]);
//                    naverCrwaler.setMaxPrice("2000000");
//                    naverCrwaler.setMinPrice("10000");
//                    list = naverCrwaler.getCrwalingData();
//                } else if(index.equals("SSG")){
//                    list = ssgCrawler.getItemInfo(item[0], min, max);
//                }

                List<String> list = null;
                naverCrwaler.setKeyword(item[0]);
                naverCrwaler.setMaxPrice(max);
                naverCrwaler.setMinPrice(min);
                list = naverCrwaler.getCrwalingData();

                if (list.size() == 0) {
                    message = "검색 결과가 존재하지 않습니다 ㅠㅠ... \n 다시 검색해주세요.";
                } else {


                    // 걍 디비 퀄 삭제 요청 필요 ㅡㅡ
                    int index = 0;
                    for(HashMap<String, String> a : userSearchList){
                        if(a.get("user").equals(user)){
                            index = userSearchList.indexOf(a);
                        }
                    }
                    userSearchList.remove(index);

                    userInfo.remove(user); // 검색완료 후 큐 삭제
                    req.setReplyMarkup(crawligUtil.setCustonButton()); // 메뉴 버튼 셋업
                    
                    StringBuffer stringBuffer = new StringBuffer(list.toString());
                    stringBuffer.deleteCharAt(0);
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                    message = stringBuffer.toString();
                }
            }

            req.setText(message);
        }

        try {
            execute(req);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @PostConstruct
    public void start() {
        logger.info("username: {}, token: {}", username, token);
    }

}
