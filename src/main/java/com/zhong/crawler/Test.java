package com.zhong.crawler;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;

/**
 * 爬虫类
 *
 * @date 2022/8/8 19:38
 */
public class Test {


    @SneakyThrows
    public static void main(String[] args) {
        Document document = Jsoup.connect("https://www.cnblogs.com/-mrl/p/10955676.html")
                .header("accept", "text/plain, */*; q=0.01")
                .header("accept-language", "zh-CN,zh;q=0.9")
                .header("accept-encoding", "gzip, deflate, br")
                .header("referer", "https://www.cnblogs.com/-mrl/p/10955676.html")
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36")
                .get();

        List<String> list = Lists.newArrayList();

//        System.out.println(document);
        Elements table = document.select("table");
        Elements tbody = table.select("tbody");
        Elements tr = tbody.select("tr");
        Optional.of(tr)
                .map(Elements::next)
                .ifPresent(x -> {
                    x.forEach(o -> {
                        Elements elements = o.select("td");
                        String one = elements.get(0).text();
                        String two = elements.get(1).text();

                        System.out.printf("\"%s\":\"%s\",%n", one, two);
                        if (one.matches("^\\-$")) {
                            System.out.println(one);
                        }

                        list.add(one);
                    });
                });

        System.out.println(list.size());
    }


}
