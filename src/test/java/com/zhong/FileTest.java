package com.zhong;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2022/1/4 19:36
 */
public class FileTest {

    @Test
    public void write() throws IOException {

        String fullPath = "D:/test/test.json";
//        String fullPath = filePath + File.separator + fileName + ".json";
        File file = new File(fullPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            // 如果已存在,删除旧文件
            file.delete();
        }
        file.createNewFile();
        Map<String, String> map = new HashMap<>();
        map.put("1", "nihao");
        map.put("2", "okkk");
        map.put("3", "uuuu");
        String jsonString = JSON.toJSONString(map);
        Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        write.write(jsonString);
        write.flush();
        write.close();
    }

    @Test
    public void writeBuffer() throws IOException {
        String content = "hello";
        FileWriter writer = new FileWriter("app.json");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(content);

        System.out.println(bufferedWriter);


    }


}
