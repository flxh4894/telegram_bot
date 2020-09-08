package com.example.telegram.telegram_bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class MyTelegramHandler extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getText());
        System.out.println(update.getMessage().getChatId());

        Crawler crawler = new Crawler();
        List<String> list = crawler.URLInfo();


        SendMessage req = new SendMessage();
        req.setChatId(update.getMessage().getChatId());
        req.setText(list.toString()+ " chat_id : " + update.getMessage().getChatId());

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
