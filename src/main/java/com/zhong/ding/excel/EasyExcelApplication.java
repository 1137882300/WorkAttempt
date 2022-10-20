package com.zhong.ding.excel;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/20 19:14
 */
public class EasyExcelApplication {


    public static void main(String[] args) {
        try (FileInputStream inputStream = new FileInputStream("C:\\Users\\EDZ\\Documents\\0订数据\\订正G B G\\缺少relation关系\\源文件2.xlsx")) {
            List<Entity> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class, 1);
            String s = JSON.toJSONString(list);
            System.out.println(s);
            long id = 30000000249L;
            List<String> sql = Lists.newArrayList();
            ArrayList<Entity> collect = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                    () -> new TreeSet<>(Comparator.comparing(o -> o.getColumn1() + ";" + o.getColumn2()))
            ), ArrayList::new));
            System.out.println("collect.size= " + collect.size());
            for (Entity entity : collect) {
                String column1 = entity.getColumn1();
                String column2 = entity.getColumn2();
                sql.add(
                        "insert into t_global_relation (id, `type`, master, slave, status, platform, creator_id, create_time, update_time, is_deleted, version)" +
                                "values (" + id-- + ",'ITEMTMP_ITEM', " + column1 + ", " + column2 + ",0 ,'bbmall_GLO', -1, 1666187870000, 1666187870000, 0, 1);"
                );
            }
            FileUtil.writeUtf8Lines(sql, new File("relation.sql"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}