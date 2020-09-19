package com.example.telegram.telegram_bot;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TelegramBotApplication {


    //Properties 설정
    public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static String WEB_DRIVER_PATH = "D:/workspace/chromedriver.exe";
    public static String TEST_URL = "https://www.naver.com";

    public static void main(String[] args) {
//        //WebDriver 설정
//         WebDriver driver;
//         WebElement element;
//         String url;
//        //System Property SetUp
//        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
//
//        //Driver SetUp
//        ChromeOptions options = new ChromeOptions();
//        options.setHeadless(true);
//        options.setCapability("ignoreProtectedModeSettings", true);
//        driver = new ChromeDriver(options);
//
//        driver.get(TEST_URL);
//
//
//        System.out.println(driver.findElement(By.tagName("body")).getText());



        SpringApplication.run(TelegramBotApplication.class, args);
    }
}