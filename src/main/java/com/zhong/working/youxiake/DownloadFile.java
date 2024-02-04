//package com.zhong.working.youxiake;
//
//import com.zhong.ding.excel.Entity;
//import com.zhong.ding.excel.ExcelUtil;
//import com.zhong.utils.FileUtils;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * @author: juzi
// * @date: 2024/1/19
// * @desc:
// */
//public class DownloadFile {
//
//    public static void main(String[] args) {
//
//        String path = "C:\\Users\\root\\Desktop\\查询11.xlsx";
//        List<Entity> entityList = FileUtils.readExcel(path);
//        List<String> list = entityList.stream().map(Entity::getColumn4).toList();
//
//        ExecutorService executorService = Executors.newFixedThreadPool(20);
//
//        for (String imageUrl : new HashSet<>(list)) {
//            Runnable task = new FileUtils.ImageDownloader.DownloadTask(imageUrl, "mp4");
//            executorService.submit(task);
//        }
//        executorService.shutdown();
//
//    }
//}
