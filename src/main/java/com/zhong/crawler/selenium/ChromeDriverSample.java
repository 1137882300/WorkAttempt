package com.zhong.crawler.selenium;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * @author: juzi
 * @date: 2023/9/11
 * @desc:
 */
public class ChromeDriverSample {

    @SneakyThrows
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        //通过配置参数禁止data;的出现,不会弹出浏览器，默认是后台静默运行
        options.addArguments("--headless", "--disable-gpu");
        //注意 第二个参数 改为你第二步下载 chromedriver.exe 所放在的路径
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\root\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        //创建浏览器窗口
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.baidu.com");
        //延迟5秒,动态网站数据填充比较慢，需要延迟才可以拿到数据
        Thread.sleep(5000);
        //拿到页面的数据
        String html = driver.getPageSource();
        System.out.println("The testing page title is: " + driver.getTitle());
        //将字符串变成document对象来获取某个节点的数据
        Document document = Jsoup.parse(html);
        System.out.println(document);
        //关闭浏览器窗口
        driver.quit();

    }

}
