package com.zhong.crawler.selenium;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author: juzi
 * @date: 2023/9/11
 * @desc: 教程：<a href="https://learn.microsoft.com/zh-cn/microsoft-edge/webdriver-chromium/?tabs=java">...</a>
 */
public class EdgeDriverSample {

    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\root\\Downloads\\edgedriver_win64 (1)\\msedgedriver.exe");

        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setBinary("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");

        // 全屏
        edgeOptions.addArguments("disable-infobars", "kiosk");
        edgeOptions.addArguments("--disable-popup-blocking");
        edgeOptions.addArguments("--user-data-dir=C:/Users/root/AppData/Local/Microsoft/Edge/User Data");
        edgeOptions.addArguments("--unsafely-treat-insecure-origin-as-secure");
        // 告诉浏览器我不是自动化测试 可以在浏览器控制台输入window.navigator.webdriver
        edgeOptions.addArguments("disable-blink-features=AutomationControlled");
        edgeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.5060.114 Safari/537.36 Edg/103.0.1264.49");
        edgeOptions.addArguments("--no-startup-window");
        edgeOptions.addArguments("--enable-override-bookmarks-ui");
        edgeOptions.addArguments("--enable-supervised-user-managed-bookmarks-folder");

        // 不加载图片, 提升速度
        edgeOptions.addArguments("--blink-settings=imagesEnabled=false");
        // 弹框取消
        edgeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        edgeOptions.setExperimentalOption("useAutomationExtension", false);


        EdgeDriver driver = new EdgeDriver(edgeOptions);
//        Thread.sleep(3000);
        driver.manage().window().maximize();
        driver.get("https://promptszone.com/login");
        WebElement element = driver.findElement(By.id("login-email"));
        element.sendKeys("923828431110@qq.com");
//        element.submit();

//
//
//        WebElement element1 = driver.findElement(By.className("form-control"));
//        element1.sendKeys("923828430@qq.com");
//        element1.submit();

//        try {
//            driver.navigate().to("https://bing.com");
//            WebElement element = driver.findElement(By.id("sb_form_q"));
//            element.sendKeys("美女");
//            element.submit();
//            Thread.sleep(20000);
//        } finally {
//            driver.quit();
//        }
    }

}
