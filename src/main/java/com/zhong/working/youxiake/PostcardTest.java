package com.zhong.working.youxiake;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhong.ding.excel.Entity;
import com.zhong.ding.excel.ExcelUtil;
import com.zhong.network.HttpService;
import com.zhong.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author: juzi
 * @date: 2024/1/12
 * @desc:
 */
public class PostcardTest {


    static class preview {

        private static final int MAX_THREADS = 20; // 最大线程数

        public static void main(String[] args) {
            List<String> imageUrls = new ArrayList<>();


            String ids = "140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224,225";
            String[] split = ids.split(",");

//            String url = "http://192.168.200.109:8000/api/postcard/preview?id=";
            String url = "https://fxhz.wgly.hangzhou.gov.cn/dashboard/api/postcard/preview?id=";

            Map<String, String> header = Maps.newHashMap();
            header.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMSIsImlhdCI6MTcwNDg3MTczNiwiZXhwIjoxNzA3NDYzNzM2LCJuYmYiOjE3MDQ4NzE3MzZ9._namY6UlmDaKDWq1uVQBD3CjKZXxT-XtxvsJ9Llo9pU");

            for (String id : split) {
                JSONObject jsonObject = HttpService.get(url + id, header);
                JSONObject data = jsonObject.getJSONObject("data");
                String imgUrl = data.getString("url");
                System.out.println(imgUrl);
                imageUrls.add(imgUrl);
            }

            System.out.println("数量：" + imageUrls.size());

            ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);

            for (String imageUrl : new HashSet<>(imageUrls)) {
                Runnable task = new FileUtils.ImageDownloader.DownloadTask(imageUrl, "images");
                executorService.submit(task);
            }
            executorService.shutdown();
        }
    }

    public static void main(String[] args) {
        try (FileInputStream inputStream = new FileInputStream("F:\\工作记录\\杭州图书馆电子名片信息表.xlsx")) {
            List<Entity> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class, 1);

            List<String> sqlList = Lists.newArrayList();

            for (Entity entity : list) {

                String real_name = entity.getColumn2();
                String foreign_name = entity.getColumn3();
                String phone = entity.getColumn4();
                if (StringUtils.isNotBlank(phone)) {
                    phone = sm4Encrypt(phone, null);
                } else {
                    phone = "";
                }
                String telephone = entity.getColumn5();
                if (StringUtils.isNotBlank(telephone)) {
                    telephone = sm4Encrypt(telephone, null);
                } else {
                    telephone = "";
                }
                String email = "";

                String job_place = entity.getColumn6();

                String job_unit = entity.getColumn7();
                String job_position = entity.getColumn8();
                String address = entity.getColumn9();

                sqlList.add(
                        String.format("INSERT INTO \"postcard\"(\"real_name\", \"phone\", \"telephone\", \"email\", \"job_unit\", \"job_position\", \"status\", \"foreign_name\", \"address\", \"job_place\")" +
                                        " VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '1', '%s', '%s', '%s');",
                                real_name, phone, telephone, email, job_unit, job_position, foreign_name, address, job_place)
                );
            }

            FileUtil.writeUtf8Lines(sqlList, new File("postcard.sql"));

        } catch (IOException ignored) {

        }


    }

    public static String sm4Encrypt(String content, String secretKey) {
//        635a6df163f598f450192fc1e5ac730e
        byte[] key = HexUtil.decodeHex("635a6df163f598f450192fc1e5ac730e");
        SymmetricCrypto sm4 = SmUtil.sm4(key);
        return sm4.encryptHex(content);
    }


}
