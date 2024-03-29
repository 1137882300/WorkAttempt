package com.zhong.ding.excel.ding;

import com.google.common.collect.Maps;
import com.zhong.Utils.FileUtils;
import com.zhong.ding.excel.Entity;
import com.zhong.ding.excel.ExcelUtil;
import lombok.SneakyThrows;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 属性类型的初始化<br>
 *
 * @date 2022/7/15 9:42
 */
public class PropertyUpdateSql {

    public static Long update_time = 1657677211000L;

    /**
     * SELECT property_id FROM t_property where is_deleted = 0;
     * <p>
     * SELECT property_id, property_name FROM t_property_language
     * WHERE is_deleted = 0 and preferred_language ='en_US';
     */
    @SneakyThrows
    public static void main(String[] args) {

        String propertyPath = "C:\\Users\\EDZ\\Documents\\0初始化\\商货品模型优化\\属性code-pre.xls";
        String languagePath = "C:\\Users\\EDZ\\Documents\\0初始化\\商货品模型优化\\属性语言-pre.xls";

        List<String> propertyList = readProperty(propertyPath);
        Map<String, String> languageMap = readLanguage(languagePath);

        System.out.println("propertyList.size = " + propertyList.size());
        System.out.println("languageMap.size = " + languageMap.size());

        StringBuilder propertySql = convertToSqlProperty(propertyList, languageMap);

        FileUtils.writeFile(propertySql, "propertySql.sql");
    }

    public static List<String> readProperty(String path) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            List<Entity> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class, 1);
            return list.stream().map(Entity::getColumn1).collect(Collectors.toList());
        }
    }

    public static Map<String, String> readLanguage(String path) throws IOException {
        Map<String, String> hashMap = Maps.newHashMap();
        try (FileInputStream inputStream = new FileInputStream(path)) {
            List<Entity> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class, 1);
            list.forEach(x -> hashMap.put(x.getColumn1(), x.getColumn2()));
        }
        return hashMap;
    }

    private static StringBuilder convertToSqlProperty(List<String> propertyList, Map<String, String> languageMap) {
        StringBuilder sb = new StringBuilder();
        propertyList.forEach(x -> {
            if (languageMap.containsKey(x)) {
                String name = languageMap.get(x);
                String code = name
                        .trim()
                        .replace(" ", "_")
                        .toLowerCase();
                sb.append("update t_property set ");
                sb.append("version = version + 1 ").append(", ");
                sb.append("update_time = ").append(update_time).append(", ");
                sb.append("property_code = ").append("'").append(code).append("'").append(" ");
                sb.append("where property_id = ").append(x).append(" ;").append("\n");
            }
        });
        return sb;
    }


    public static void main2(String[] args) {
        String name = " asd oip asd 中文 ";
        String code = name
                .trim()
                .replace(" ", "_")
                .toLowerCase();
        System.out.println(code);
    }

}
