package com.zhong.Utils;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.zhong.ding.excel.Entity;
import com.zhong.ding.excel.ExcelUtil;
import lombok.SneakyThrows;

import java.io.*;
import java.util.List;
import java.util.function.Function;

/**
 * @date 2022/6/29 15:15
 */
public class FileUtils {

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


    public static List<Entity> readExcelByPath(String path, int sheetNo, int headLine) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            return ExcelUtil.readExcelByHeadLine(new BufferedInputStream(inputStream), Entity.class, sheetNo, headLine);
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
}
