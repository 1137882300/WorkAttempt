package com.zhong.crawler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhong.utils.FileUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * @date 2022/8/12 16:22
 */
public class ImgCrawling {

    @SneakyThrows
    public static void main(String[] args) {

        String url = "http://www.360doc.com/content/15/0907/18/137012_497518192.shtml";

        Document document = Jsoup.connect("http://www.360doc.com/content/15/0907/18/137012_497518192.shtml")
                .header("accept-encoding", "gzip, deflate, br")
                .header("accept-language", "zh-CN,zh;q=0.9")
                .header("accept-encoding", "gzip, deflate, br")
                .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("referer", "https://www.djcargo.cn/worldwide-post-code/")
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36")
                .get();

        Document doc = Jsoup.parse(new URL(url).openStream(), "utf-8", url);

        List<String> list = Lists.newLinkedList();
        Element element = doc.getElementById("artContent");
        Optional.ofNullable(element)
                .map(e -> element.select("p"))
//                .map(Elements::next)
                .ifPresent(x -> {
                    x.forEach(o -> {
                        String img = o.select("img").attr("doc360img-src");
                        if (StringUtils.isNotBlank(img)) {
                            list.add(img);
                        }
                    });

                });

        System.out.println(element);
//        System.out.println(list.size());
        System.out.println(JSON.toJSONString(list));

        list.forEach(x -> {
            FileUtils.downImages("C:\\Users\\EDZ\\Pictures\\下载图片", x, null);
        });

    }

}
