package com.zhong.script;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaWangYe {

    //携带请求参数
    public static final String gid = "gid";
    public static final String uc = "uc";
    public static final String img = "img";
    //域名
    public static final String webUrl = "https://www.buscdn.lol";
    //单个下载地址
    public static final String strUrl = "https://www.buscdn.lol/DORI-086";//https://www.buscdn.lol/DORI-085 https://www.buscdn.lol/GAID-024 https://www.buscdn.lol/FNEO-070 https://www.buscdn.lol/NINE-070

    public static void main(String[] args) throws IOException {

        URL url = new URL(strUrl);
        Document document = Jsoup.parse(url, 10000);

        Elements elementsByClass = document.getElementsByClass("bigImage");
        Elements imgs = elementsByClass.select("img");
        String img1 = getAttr(imgs, "src");
        String title = getAttr(imgs, "title");

        System.err.println(webUrl + img1);
        System.err.println(title);

        //图集
        Element id = document.getElementById("sample-waterfall");
        //样品图像
        Elements imgjp = id.select("img");
        imgjp.forEach(i -> System.err.println(webUrl + i.attr("src")));
        Elements script = document.select("script");
        Element scripts = findAttr(script, "gid");
        Map<String, String> value = getValue(scripts);
        String floor = String.valueOf(Math.floor(1e3 * Math.random() + 1));
        value.put("floor", floor.substring(0, floor.length() - 2));
        value.put("title", title);

        String res = sendGet(webUrl + "/ajax/uncledatoolsbyajax.php?gid=" + value.get(gid) + "&lang=zh&img=" + value.get(img) + "&uc=" + value.get(uc) + "&floor=" + floor);

        //返回结果解析种子
        Document parse = Jsoup.parseBodyFragment("<table>" + res + "</table>");
        Elements tr = parse.select("tr");
        List<HentityVo> list = new ArrayList<>();
        tr.forEach(r -> {
            Elements td = r.select("td");
            Elements a = td.select("a");
            HentityVo hentity = new HentityVo();
            if (a.size() == 4) {
                hentity.setSeed(a.get(0).attr("href"));
                hentity.setName(a.get(0).html());
                hentity.setSize(a.get(2).html());
                hentity.setDate(a.get(3).html());
            } else if (a.size() == 3) {
                hentity.setSeed(a.get(0).attr("href"));
                hentity.setName(a.get(0).html());
                hentity.setSize(a.get(1).html());
                hentity.setDate(a.get(2).html());
                list.add(hentity);
            } else if (a.size() == 5) {
                hentity.setSeed(a.get(0).attr("href"));
                hentity.setName(a.get(0).html());
                hentity.setSize(a.get(3).html());
                hentity.setDate(a.get(4).html());
            }
            list.add(hentity);
        });

        list.forEach(o -> {
            if (o.getSize().contains("GB")) {
                o.setSize(String.valueOf(Double.parseDouble(o.getSize().replace("GB", "")) * 1024));
            }
            if (o.getSize().contains("MB")) {
                o.setSize(o.getSize().replace("MB", ""));
            }
        });

        System.err.println(list);
//        map.forEach((k, v) -> {
//            if(v.getSize().contains("GB")){
//                v.setSize(String.valueOf(Double.parseDouble(v.getSize().replace("GB",""))*1024));
//            }
//            if(v.getSize().contains("MB")){
//                v.setSize(v.getSize().replace("MB",""));
//            }
//        });
//        map.forEach((k, v) -> {
//            System.err.println(v);
//        });

    }


    public static Map<String, String> getValue(Element element) {
        Map<String, String> map = new HashMap<>();
        List<Node> nodes = element.childNodes();
        nodes.forEach(n -> {
            String str = n.toString().replace("\t", "").replace("\n", "").replace("\r", "").trim();
            //var gid = 10351750508;var uc = 1;var img = '/imgs/cover/1o6g_b.jpg';
            if (!StringUtils.isBlank(str)) {
                map.put(gid, str.substring(10, 21));
                map.put(uc, str.substring(31, 32));
                map.put(img, str.substring(44, str.length() - 2));
            }
        });
        return map;
    }

    public static Element findAttr(Elements elements, String str) {
        Element element = null;
        for (Element str1 : elements) {
            if (str1.toString().contains(str)) {
                element = str1;
            }
        }
        return element;
    }

    public static String getAttr(Elements elements, String str) {
        String str1 = null;
        for (Element img : elements) {
            str1 = img.attr(str);
        }
        return str1;
    }


    public static String sendGet(String url) {
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        String response = "";

        //创建连接
        try {
            //创建URL
            //HTTP URL类 用这个类来创建连接
            URL httpUrl = new URL(url);
            //建立连接
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setRequestProperty("referer", strUrl);
            conn.setUseCaches(false);//设置不要缓存
            conn.setInstanceFollowRedirects(true);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //GET请求
            out = new OutputStreamWriter(
                    conn.getOutputStream());
            out.flush();
            //读取响应
            reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), StandardCharsets.UTF_8);
                response += lines;
            }
            reader.close();
            // 断开连接
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("发送 GET 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return response;
    }

    @Data
    static class HentityVo {
        private String seed;
        private String name;
        private String size;
        private String date;
    }
}
