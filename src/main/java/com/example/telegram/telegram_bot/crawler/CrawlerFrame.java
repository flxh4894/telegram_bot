package com.example.telegram.telegram_bot.crawler;

import com.example.telegram.telegram_bot.inteface.Crawling;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public abstract class CrawlerFrame implements Crawling {

    Document getHTMLData(String URL) {
        List<String> list = new ArrayList<>();
        try {
            Connection connection = Jsoup.connect(URL);
            Document html = connection.get();

            return html;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
