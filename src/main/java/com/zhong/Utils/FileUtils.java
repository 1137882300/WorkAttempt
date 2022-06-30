package com.zhong.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

}
