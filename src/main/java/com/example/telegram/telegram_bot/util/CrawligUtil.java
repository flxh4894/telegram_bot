package com.example.telegram.telegram_bot.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class CrawligUtil {

    List<HashMap<String,String>> btns = new ArrayList<>();

    @Bean
    public void setBtnsInfo(){
        // 데이터베이스로 변환
        btns.add(new HashMap<>(){{put("btn_title","SSG"); put("btn_data","SSG");}});
        btns.add(new HashMap<>(){{put("btn_title","naver"); put("btn_data","Naver");}});
        btns.add(new HashMap<>(){{put("btn_title","11st"); put("btn_data","11st");}});
    }


    /**
     * @author 도원
     * @version 1.0
     * 텔레그램 채팅방 하단부 버튼 생성
     * List< List<>> 형태로 사용. 안쪽 List에 생성 원하는 만큼 입력
     **/
    public ReplyKeyboardMarkup setCustonButton(){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRow = new ArrayList<>();

        try {
            // 메뉴 생성
            List<List<KeyboardButton>> rowsInButton = new ArrayList<>();

            List<KeyboardButton> rowInButton1 = new ArrayList<>();
            rowInButton1.add(new KeyboardButton().setText("검색으로 추가 \uD83D\uDE0A"));
            rowInButton1.add(new KeyboardButton().setText("종목으로 추가 \uD83C\uDFE0"));
            List<KeyboardButton> rowInButton2 = new ArrayList<>();
            rowInButton2.add(new KeyboardButton().setText("알림등록 ⭕"));
            rowInButton2.add(new KeyboardButton().setText("알림삭제 ❌"));
            List<KeyboardButton> rowInButton3 = new ArrayList<>();
            rowInButton3.add(new KeyboardButton().setText("처음으로 돌아가기 \uD83D\uDEE0"));

            rowsInButton.add(rowInButton1);
            rowsInButton.add(rowInButton2);
            rowsInButton.add(rowInButton3);
            // 메뉴 생성 끝

            for(List<KeyboardButton> rowInButton : rowsInButton){
                KeyboardRow k1 = new KeyboardRow(); // 리스트 형태가 들어가야함
                k1.addAll(rowInButton);             // 리스트 넣고
                keyboardRow.add(k1);                // 한줄 등록
            }

            replyKeyboardMarkup.setKeyboard(keyboardRow);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return replyKeyboardMarkup;
    }

    /**
     * @author 도원
     * @version 1.0
     * 텔레그램 채팅방 내 버튼 생성
     * List< List<>> 형태로 사용. 안쪽 List에 생성 원하는 만큼 입력
     **/
    public InlineKeyboardMarkup setCustomInlineButton(){

        InlineKeyboardMarkup markupInline = null;
        try {


            markupInline = new InlineKeyboardMarkup();
            List < List <InlineKeyboardButton>> rowsInline = new ArrayList< >();
            List < InlineKeyboardButton > rowInline = new ArrayList < > ();

            for(HashMap<String, String>btn : btns){
                System.out.println(btn);
                String btn_title = btn.get("btn_title");
                String btn_data = btn.get("btn_data");
                rowInline.add(new InlineKeyboardButton().setText(btn_title).setCallbackData(btn_data));
            }
            rowsInline.add(rowInline);
            markupInline.setKeyboard(rowsInline);

            return markupInline;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
