package com.example.telegram.telegram_bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class MyTelegramHandler extends TelegramLongPollingBot {

    SSGCrawler ssgCrawler = new SSGCrawler();

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getText());
        System.out.println(update.getMessage().getChatId());

        Crawler crawler = new Crawler();
        //List<String> list = crawler.URLInfo();

        String msg = update.getMessage().getText();
        String min = "";
        String max = "";

        String[] item = msg.split("ㅡ");
        if(item.length == 3){
            min = item[1];
            max = item[2];
        } else if(item.length == 1){

        } else {

        }

        // SSG 상품검색
        List<String> list;
        list = ssgCrawler.getItemInfo(item[0],min,max);

        StringBuffer stringBuffer = new StringBuffer(list.toString());
        stringBuffer.deleteCharAt(0);
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        SendMessage req = new SendMessage();
        req.setChatId(update.getMessage().getChatId());
        req.setText(stringBuffer.toString());
        req.enableMarkdown(true);

        try {
            execute(req);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getBotUsername() {
        return "plovelyBot";
    }

    @Override
    public String getBotToken() {
        return "1132709981:AAEVzFP079An7ica_ZM7Cg5JKm3T88-oa1I";
    }
}
