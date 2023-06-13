package com.zhong.ffmpeg;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FFmpegExample {

    public static void main(String[] args) throws Exception {
        // RTMP 直播流地址
        String liveUrl = "rtmp://playtx.shangzhibo.tv/onelive/10998209-dFwCP1Q1-";
        // 拉流命令
        String command = String.format("ffmpeg -i %s -c:v copy -c:a copy -f flv -vsync vfr output.flv", liveUrl);

        // 执行命令
        Process process = Runtime.getRuntime().exec(command);

        // 读取输出流
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        // 等待命令执行完成
        int exitCode = process.waitFor();
        System.out.println("命令执行完成：" + exitCode);
    }

    public static void main1(String[] args) throws IOException, InterruptedException {
        // RTMP 直播流地址
        String liveUrl = "rtmp://playtx.shangzhibo.tv/onelive/10998209-dFwCP1Q1-";
        // FFmpeg 命令行
        String command = String.format("ffmpeg -i %s -c:v copy -c:a copy -f flv -vsync vfr output.flv", liveUrl);

        // 执行 FFmpeg 命令行
        Process process = Runtime.getRuntime().exec(command);
        OutputStream outputStream = process.getOutputStream();
        InputStream errorStream = process.getErrorStream();
        System.out.println(outputStream);
        System.out.println(errorStream.toString());


        process.waitFor();
        System.out.println("拉取直播流完成！");
    }
}