package com.zhong;

import com.zhong.gitee.DrawingBed;
import org.junit.Test;

import java.util.List;

/**
 * @author: juzi
 * @date: 2023/12/20
 * @desc:
 */
public class DrawingDedTest {

    //gitee

    // 测试上传文件
    @Test
    public void uploadingForGitee() {
        String resp = DrawingBed.GiteeFileUtil.uploading("C:\\Users\\root\\Pictures\\Saved Pictures\\美女.jpg", ".jpg", "测试提交");
        System.out.println("访问|下载地址 = " + resp);
    }

    @Test
    public void getTreeForGitee() {
        String resp = DrawingBed.GiteeFileUtil.getTree();
        System.out.println("resp = " + resp);
    }

    @Test
    public void getImgForGitee() {
        String resp = DrawingBed.GiteeFileUtil.getImg();
        System.out.println("resp = " + resp);
    }

    //github

    // 测试上传文件
    @Test
    public void uploading() {
        String resp = DrawingBed.GitHubFileUtil.uploading("C:\\Users\\root\\Pictures\\Saved Pictures\\美女.jpg", ".jpg", "测试提交");
        System.out.println("访问|下载地址 = " + resp);
    }

    // 查询所有文件的访问地址
    @Test
    public void getFileAll() {
        List<String> imagesUrlList = DrawingBed.GitHubFileUtil.getFileAll("img");
        imagesUrlList.forEach(System.out::println);
    }

    // 替换文件
    @Test
    public void updateFile() {
        String sha = DrawingBed.GitHubFileUtil.getSha("img/12.png");
        String url = DrawingBed.GitHubFileUtil.updateFile("D:\\12.png", "替换文件", sha, "img/12.png");
        System.out.println("url = " + url);
    }

    // 删除文件
    @Test
    public void delFile() {
        String sha = DrawingBed.GitHubFileUtil.getSha("img/12.png");
        Integer code = DrawingBed.GitHubFileUtil.delFile("测试删除", sha, "img/12.png");
        System.out.println("结果状态 = " + code);
    }

    // 获取存储库自述文件README.md
    @Test
    public void getReadme() {
        String resp = DrawingBed.GitHubFileUtil.getReadme();
        System.out.println("结果 = " + resp);
    }
}
