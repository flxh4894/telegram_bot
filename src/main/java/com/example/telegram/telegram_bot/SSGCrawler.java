package com.example.telegram.telegram_bot;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SSGCrawler {

    public List<String> getItemInfo(String item, String minPrc, String maxPrc){
        // 신세계 긁어오기
        List<String > list = new ArrayList<>();
        try {

            String URL = "http://www.ssg.com/search.ssg?target=all&query="+item+"&minPrc="+minPrc+"&maxPrc="+maxPrc+"&page=&sort=prcasc";
            Connection conn = Jsoup.connect(URL);

            Document html = conn.get();
            Elements elements = html.select(".tmpl_itemlist .cunit_lst_v .title a");
            Elements price = html.select(".ssg_price");

            String[] change = {"(",")","[","]","-","_"};

            for (Element element : elements){
                if(elements.indexOf(element) == 25)
                    break;

                String title = element.select(".tx_ko").text();
                for(String index : change){
                    if(title.contains(index)){
                        title = title.replace(index, "");
                    }
                }
                System.out.println(title);
                String link = "http://www.ssg.com" + element.attr("href");
                String newPrice = price.get(elements.indexOf(element)).text();


                list.add(title + "\n" + "-가격 : " + newPrice + "원\n [*구매링크]("+ link +")\n\n");
            }

            System.out.println(list);

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
