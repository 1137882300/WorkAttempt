package com.zhong.Utils;

import com.zhong.excel.Entity;
import com.zhong.excel.ExcelUtil;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

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

}
