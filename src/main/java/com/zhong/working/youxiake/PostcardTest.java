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

            String ids = "137,136,135,134,133,132,131,130,129,128,127,126,125,124,123,122,121,120,119,118,117,116,115,114,113,112,111,110,109,108,107,106,105,104,103,102,101,100,99,98,97,96,95,94,93,92,91,90,89,88,87,86,85,84,83,82,81,80,79,78,77,76,75,74,73,72,71,70,69,68,67,66,65,64,63,62,61,60,59,58,57,56,55,54,53,52,51,50,49,48,47,46,45,44,43,42,41,40,39,38,37,36,35,34,33,32,31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8";

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
        try (FileInputStream inputStream = new FileInputStream("F:\\工作记录\\电子名片信息表模板(杭州艺术学校)-2.4.xlsx")) {
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
