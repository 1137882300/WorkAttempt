package com.zhong.crawler;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.collect.Lists;
import com.zhong.ding.excel.Entity;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @date 2022/8/11 13:31
 */
public class Test2 {

    @SneakyThrows
    public static void main(String[] args) {
        Document document = Jsoup.connect("https://www.djcargo.cn/worldwide-post-code/")
                .header("accept-encoding", "gzip, deflate, br")
                .header("accept-language", "zh-CN,zh;q=0.9")
                .header("accept-encoding", "gzip, deflate, br")
                .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("referer", "https://www.djcargo.cn/worldwide-post-code/")
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36")
                .post();


        List<Entity> list = Lists.newArrayList();

//        System.out.println(document);
        Element element = document.getElementById("oContent");
//        System.out.println(element);
        assert element != null;
        Elements tbody = element.select("tbody");
        Elements tr = tbody.select("tr");
//        System.out.println(tr);
        Optional.of(tr)
                .map(Elements::next)
                .ifPresent(x -> {
                    x.forEach(o -> {
                        Elements td = o.select("td");
                        Entity entity = new Entity();
                        if (td.size() >= 1) {
                            if (null != td.get(0)) {
                                entity.setColumn1(td.get(0).text());
                            }
                        }
                        if (td.size() >= 2) {
                            if (null != td.get(1)) {
                                entity.setColumn2(td.get(1).text());
                            }
                        }
                        if (td.size() >= 4) {
                            if (null != td.get(3)) {
                                entity.setColumn3(td.get(3).text());
                            }
                        }
                        if (td.size() >= 5) {
                            System.out.println(td);
                            if (null != td.get(4)) {
                                entity.setColumn4(td.get(4).text());
                            }
                        }
                        list.add(entity);
                    });
                });

        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
        Set<String> includes = simplePropertyPreFilter.getIncludes();
        includes.add("column1");
        includes.add("column2");
        includes.add("column3");
        includes.add("column4");
        FileUtil.writeUtf8String(JSON.toJSONString(list, simplePropertyPreFilter), new File("country.json"));

    }
}
