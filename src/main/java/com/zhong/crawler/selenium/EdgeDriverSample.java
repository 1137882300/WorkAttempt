package com.zhong.crawler.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author: juzi
 * @date: 2023/9/11
 * @desc:
 */
public class EdgeDriverSample {

    public static void main(String[] args) throws Exception {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setBinary("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");

        // 全屏
        edgeOptions.addArguments("disable-infobars", "kiosk");
        edgeOptions.addArguments("--disable-popup-blocking");
        // 告诉浏览器我不是自动化测试 可以在浏览器控制台输入window.navigator.webdriver
        edgeOptions.addArguments("disable-blink-features=AutomationControlled");
        edgeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.5060.114 Safari/537.36 Edg/103.0.1264.49");

        // 不加载图片, 提升速度
        edgeOptions.addArguments("--blink-settings=imagesEnabled=false");
        // 弹框取消
        edgeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        edgeOptions.setExperimentalOption("useAutomationExtension", false);


        System.setProperty("webdriver.edge.driver", "C:\\Users\\root\\Downloads\\edgedriver_win64\\msedgedriver.exe");
        EdgeDriver driver = new EdgeDriver(edgeOptions);
        try {
            driver.navigate().to("https://bing.com");
            WebElement element = driver.findElement(By.id("sb_form_q"));
            element.sendKeys("WebDriver");
            element.submit();

            Thread.sleep(5000);
        } finally {
            driver.quit();
        }
    }

}
