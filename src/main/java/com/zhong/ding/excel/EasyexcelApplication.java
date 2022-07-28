package com.zhong.ding.excel;

import com.alibaba.fastjson.JSON;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/20 19:14
 */
public class EasyexcelApplication {


    public static void main(String[] args) {
        try (FileInputStream inputStream = new FileInputStream("C:\\Users\\EDZ\\Desktop\\数据初始化表.xls")) {
            List<Entity> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class,1);
            String s = JSON.toJSONString(list);
            System.out.println(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}