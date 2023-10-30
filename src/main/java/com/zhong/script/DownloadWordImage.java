package com.zhong.script;


import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author: juzi
 * @date: 2023/10/30
 * @desc: 下载word文档中的图片
 */
import org.apache.commons.io.IOUtils;

import java.io.*;

public class DownloadWordImage {
    public static void main(String[] args) {
        try {
            // 读取Word文档
            FileInputStream fileInputStream = new FileInputStream("F:\\工作记录\\杭州红叶十佳观赏地.docx");
            XWPFDocument document = new XWPFDocument(fileInputStream);

            // 创建保存图片的文件夹
            File folder = new File("F:\\工作记录\\图片");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            int imageCount = 1;
            // 获取所有段落
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                // 获取段落中的所有运行
                for (XWPFRun run : paragraph.getRuns()) {
                    // 检查运行中是否包含图片
                    for (XWPFPicture picture : run.getEmbeddedPictures()) {
                        // 获取图片字节数据
                        byte[] pictureData = picture.getPictureData().getData();
                        // 构造图片文件路径
                        String imagePath = folder.getPath() + "/image" + imageCount + ".jpg";
                        // 保存图片到文件夹中
                        FileOutputStream fileOutputStream = new FileOutputStream(imagePath);
                        IOUtils.write(pictureData, fileOutputStream);
                        fileOutputStream.close();
                        imageCount++;
                    }
                }
            }

            document.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}