package com.example.telegram.telegram_bot;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Crawler {

    public List<String> URLInfo(){

        List<String> list = new ArrayList<>();
        JSONObject obj = new JSONObject();

        try {
            String URL = "https://sports.news.naver.com/wfootball/index.nhn";
            Connection conn = Jsoup.connect(URL);
            Document html = conn.get();
            Elements element = html.select("div.home_news");

            for(Element el : element.select("li")) {
                list.add("[" + el.text() + "]" + "\n");
                System.out.println(el.text());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
