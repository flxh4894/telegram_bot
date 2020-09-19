package com.example.telegram.telegram_bot.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NaverCrwaler extends CrawlerFrame{

    private String max;
    private String min;
    private String keyword;

    @Override
    public void setMaxPrice(String max) {
        this.max = max;
    }

    @Override
    public void setMinPrice(String min) {
        this.min = min;
    }

    @Override
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public List<String> getCrwalingData() {

        List<String> list = new ArrayList<>();
        String URL = "https://search.shopping.naver.com/search/all?query="+keyword+"&sort=review&maxPrice="+max+"&minPrice="+min;
        try {
            Document document = getHTMLData(URL);
            Elements elements = document.select(".basicList_title__3P9Q7 a");
            Elements price = document.select(".price_num__2WUXn");
            Elements img = document.select("img");

            String[] change = {"(",")","[","]","-","_"};
            for(Element el : elements){
                if(elements.indexOf(el) == 25)
                    break;

                String title = el.text();
                for(String index : change){
                    if(title.contains(index)){
                        title = title.replace(index, "");
                    }
                }

                String link = el.attr("href");
                String newPrice = price.get(elements.indexOf(el)).text();
                String newImg = "https://shopping-phinf.pstatic.net/main_8124560/81245607534.5.jpg?type=f140";
                System.out.println("상품명 : " + title +"\n 가격 : " + newPrice + "\n [구매링크]("+link+")\n\n");
                list.add(title + "\n" + "-가격 : " + newPrice + "\n [*구매링크]("+ link +")\n ![이미지]("+ newImg +") \\n\\n");
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
