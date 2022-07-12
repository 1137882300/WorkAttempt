package com.zhong.excel;

import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @date 2022/7/12 20:03
 */
public class InsertSqlTest {

    @SneakyThrows
    public static void main(String[] args) {

        String propertyValuePath = "C:\\Users\\EDZ\\Documents\\初始化\\产品预归类\\境内货源地代码表.xlsx";

        List<Entity> list = readValue(propertyValuePath);

        System.out.println("list.size()=" + list.size());

        StringBuilder propertyValueSql = convertToSqlValueAndLanguage(list);


    }

    private static StringBuilder convertToSqlValueAndLanguage(List<Entity> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (Entity entity : list) {
                String name = entity.getColumn1();
                String code = entity.getColumn2();


            }
        }


        return null;
    }


    private static List<Entity> readValue(String path) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            return ExcelUtil.readExcelByHeadLine(new BufferedInputStream(inputStream), Entity.class, 1, 2);
        }
    }

}
