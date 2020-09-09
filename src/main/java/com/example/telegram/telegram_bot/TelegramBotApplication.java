package com.example.telegram.telegram_bot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@SpringBootApplication
public class TelegramBotApplication {

    public static void main(String[] args) {

/*        ApiContextInitializer.init ();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new MyTelegramHandler());
        } catch (TelegramApiException e) {
            System.out.println("bbbb" + e);
        }*/
        SpringApplication.run(TelegramBotApplication.class, args);
    }
}