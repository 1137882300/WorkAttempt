package com.zhong.working.youxiake;

import com.zhong.ding.excel.Entity;
import com.zhong.ding.excel.ExcelUtil;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: juzi
 * @date: 2024/1/8
 * @desc:
 */
public class ActivityExcelTest {


    @Test
    public void read(){
        try (FileInputStream inputStream = new FileInputStream("F:\\工作记录\\（12.29产品-83项）2024“宋”福杭州年文旅产品(定).xls")) {
            List<Entity> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class,2);


            for (Entity entity : list) {
                System.out.println(entity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
