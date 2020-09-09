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

        System.out.println(crawler.URLInfo());

        String msg = update.getMessage().getText();
        String min = "";
        String max = "";

        String[] item = msg.split("ㅡ");

        //최소, 최대 입력 없을 시 추가
        if(item.length == 3){
            min = item[1];
            max = item[2];
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

    @PostConstruct
    public void start() {
        logger.info("username: {}, token: {}", username, token);
    }

}
