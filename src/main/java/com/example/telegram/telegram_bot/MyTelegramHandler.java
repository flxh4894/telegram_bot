package com.example.telegram.telegram_bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class MyTelegramHandler extends TelegramLongPollingBot {

    SSGCrawler ssgCrawler;
    Crawler crawler;
    public MyTelegramHandler(SSGCrawler ssgCrawler, Crawler crawler){
        this.ssgCrawler = ssgCrawler;
        this.crawler = crawler;
    }

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



        String getMsg = update.getMessage().getText();
        String message = "";
        String min = "";
        String max = "";
        boolean flag = true;

        String[] item = getMsg.split("ㅡ");
        //최소, 최대 입력 없을 시 추가
        if(item.length == 3){
            min = item[1];
            max = item[2];
        } else if (item.length != 1 ){
            message = "정확한 형태로 입력해 주세요. \n -입력형태1 : 검색어ㅡ최소가격ㅡ최대가격 \n " +
                    "-입력형태2 : 검색어";
            flag = false;
        }

        if(flag){
            // SSG 상품검색
            List<String> list;
            list = ssgCrawler.getItemInfo(item[0],min,max);
            if(list.size() == 0){
                message = "검색 결과가 존재하지 않습니다 ㅠㅠ...";
            } else {
                StringBuffer stringBuffer = new StringBuffer(list.toString());
                stringBuffer.deleteCharAt(0);
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                message = stringBuffer.toString();
            }
        }

        SendMessage req = new SendMessage();
        req.setChatId(update.getMessage().getChatId());
        req.setText(message);
        req.enableMarkdown(true);

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
