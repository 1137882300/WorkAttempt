//package com.zhong.script;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Index {
//    public static final String webUrl = "https://www.buscdn.lol";
//
//    public static void main(String[] args) throws IOException {
//        URL url = new URL(webUrl);
//        Document document = Jsoup.parse(url, 10000);
//        Element id = document.getElementById("waterfall");
//        Elements elementsByClass = id.getElementsByClass("item");
//        elementsByClass.forEach(e -> {
//            Elements elementsByClass1 = e.getElementsByClass("movie-box");
//            //子集菜单链接
//            System.err.println(elementsByClass1.attr("href"));
//            Elements img = e.select("img");
//            //首页视频大图
//            String imgSrc = img.attr("src");
//            if(imgSrc.contains(WebConfig.Https)){
//                System.err.println(imgSrc);
//            }else{
//                System.err.println(webUrl + imgSrc);
//            }
//            //首页视频标题
//            System.err.println(img.attr("title"));
//            Elements date = e.select("date");
//            Map<String, String> map = new HashMap<>();
//            map.put(date.get(0).html(), date.get(1).html());
//            map.forEach((k, v) -> {
//                System.err.println(k);
//                System.err.println(v);
//            });
//            System.err.println("");
//
//        });
//
//    }
//}
