package com.example.telegram.telegram_bot.inteface;

import java.util.List;

public interface Crawling {

    void setMaxPrice(String max);
    void setMinPrice(String min);
    void setKeyword(String keyword);
    List<String>getCrwalingData();
}
