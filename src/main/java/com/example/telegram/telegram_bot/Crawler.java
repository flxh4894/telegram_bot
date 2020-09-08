package com.example.telegram.telegram_bot;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Crawler {

    String tempProd = "";

    // 디씨인사이드 개념글 리스트 가져오기
    public List<String> URLInfo(){

        List<String> list = new ArrayList<>();
        JSONObject obj = new JSONObject();

        try {
            String URL = "https://gall.dcinside.com/mgallery/board/lists?id=ragog&exception_mode=recommend";
            Connection conn = Jsoup.connect(URL);
            Document html = conn.get();
            Elements element = html.select(".ub-content.us-post");

            for(Element el : element.select("td.gall_tit.ub-word")) {
                el = el.select("a:nth-child(1)").first();
                list.add("[" + el.text() + "]" + "\n");
                System.out.println(el.text());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public String DC_Newopic(String data) {

        List<String> list = new ArrayList<>();
        tempProd = data;

        try {
            String URL = "http://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu";
            Connection conn = Jsoup.connect(URL);
            Document html = conn.get();
            Element element = html.select(".list_title").get(1);
            String prod = element.text();

            SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
            Date time = new Date();
            String time1 = format1.format(time);

            if(tempProd.equals("")){
                tempProd = prod;
                System.out.println(prod + "TEMP" + time1);
                return tempProd;
            } else {
                // 같지 않으면 출력
                if (!tempProd.equals(prod)){
                    System.out.println(prod + "" + time1);
                    tempProd = prod;

                    return tempProd;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
