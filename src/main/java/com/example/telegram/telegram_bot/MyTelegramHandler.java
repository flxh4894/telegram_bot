package com.example.telegram.telegram_bot;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyTelegramHandler extends TelegramLongPollingBot {

    static List<String> userId = new ArrayList<>();
    String data = "";


    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getText());
        System.out.println(update.getMessage().getChatId());

        // 들어오는 모든 유저 아이디 저장
        userId.add(update.getMessage().getChatId().toString());
        System.out.println(userId);
/*
        Crawler crawler = new Crawler();
        List<String> list = crawler.URLInfo();

        SendMessage req = new SendMessage();
        req.setChatId(update.getMessage().getChatId());
        req.setText(list.toString()+ " chat_id : " + update.getMessage().getChatId());

        try {
            execute(req);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public String getBotUsername() {
        return "plovelyBot";
    }

    @Override
    public String getBotToken() {
        return "1132709981:AAEVzFP079An7ica_ZM7Cg5JKm3T88-oa1I";
    }

    // 5초마다 뽐뿌 긁어오기
    @Scheduled(fixedDelay = 5000)
    public void goMsg(){

        try {
            Crawler crawler = new Crawler();
            String prodInfo = crawler.DC_Newopic(data);
            data = prodInfo;

            if(! data.equals("")){
                SendMessage req = new SendMessage();
                for (String id : userId) {
                    System.out.println(id + "아니 이게 없다고?!");
                    req.setChatId(id);
                    req.setText(prodInfo);
                    execute(req);
                }
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
