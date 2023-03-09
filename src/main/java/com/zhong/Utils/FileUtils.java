package com.zhong.Utils;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.zhong.ding.excel.Entity;
import com.zhong.ding.excel.ExcelUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @date 2022/6/29 15:15
 */
public class FileUtils {

    public static void writeFile(Collection<T> list, String fileName) {
        FileUtil.writeUtf8Lines(list, new File(fileName));
    }

    public static void writeFile(StringBuilder sb, String fileName) {
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            writer = new FileWriter(fileName);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(sb.toString());
        } catch (IOException e) {

        } finally {
            assert bufferedWriter != null;
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @SneakyThrows
    public static List<Entity> readExcelByPath(String path, int sheetNo, int headLine) {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            return ExcelUtil.readExcelByHeadLine(new BufferedInputStream(inputStream), Entity.class, sheetNo, headLine);
        }
    }

    @SneakyThrows
    public static List<Entity> readExcel(String path) {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            return ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class, 1);
        }
    }

    @SneakyThrows
    public static List<Entity> readLineCSV(String path) {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        reader.readLine();//先读取第一行(过滤第一行)
        String line;
        List<Entity> list = Lists.newLinkedList();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            Entity entity = new Entity();
            entity.setColumn1(split[0]);
            entity.setColumn2(split[1]);
            entity.setColumn3(split[2]);
            entity.setColumn4(split[3]);
            list.add(entity);
        }
        return list;
    }

    public static List<Entity> readJson(String path) {
        File file = new File(path);
        String string = FileUtil.readUtf8String(file);
        return JSON.parseObject(string, new TypeReference<List<Entity>>() {
        });
    }

    public static Map<String, String> readJsonToMap(String path) {
        File file = new File(path);
        String string = FileUtil.readUtf8String(file);
        return JSON.parseObject(string, new TypeReference<Map<String, String>>() {
        });
    }

    @SneakyThrows
    public static void downImages(String filePath, String imgUrl, String fileName) {
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String name = "";
        if (StringUtils.isBlank(fileName)) {
            name = imgUrl.substring(imgUrl.lastIndexOf('/') + 1);
        } else {
            name = fileName;
        }
        // 文件名里面可能有中文或者空格，所以这里要进行处理。但空格又会被URLEncoder转义为加号
        String urlTail = URLEncoder.encode(name, "UTF-8");
        imgUrl = imgUrl.substring(0, imgUrl.lastIndexOf('/') + 1) + urlTail.replaceAll("\\+", "\\%20");

        File file = new File(filePath + File.separator + name);
        // 获取图片URL
        URL url = new URL(imgUrl);
        URLConnection connection = url.openConnection();
//        connection.setRequestProperty("accept-encoding", "gzip, deflate, br");
//        connection.setRequestProperty("accept-language", "zh-CN,zh;q=0.9");
//        connection.setRequestProperty("accept-encoding", "gzip, deflate, br");
//        connection.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
//        connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36");
//        connection.setRequestProperty("User-Agent", "Mozilla/4.76");
        connection.setConnectTimeout(10 * 1000);
        // 获得输入流
        InputStream in = connection.getInputStream();
        // 获得输出流
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        // 构建缓冲区
        byte[] buf = new byte[1024];
        int size;
        // 写入到文件
        while (-1 != (size = in.read(buf))) {
            out.write(buf, 0, size);
        }
        out.close();
        in.close();
    }

}
