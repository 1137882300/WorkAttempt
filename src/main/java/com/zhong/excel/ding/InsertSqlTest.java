package com.zhong.excel.ding;

import com.alibaba.fastjson.JSON;
import com.zhong.Utils.FileUtils;
import com.zhong.excel.Entity;
import com.zhong.excel.ExcelUtil;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * 属性值的初始化<br>
 *
 * @date 2022/7/12 20:03
 */
public class InsertSqlTest {
    /*
        确认线上ID起始
     */
    public static final Long propertyId = 172802L;
    public static Long valueId = 4000L;
    public static Long languageId = 4000L;

    public static Long is_deleted = 0L;
    public static Long create_time = 1657677211000L;
    public static Long update_time = 1657677211000L;
    public static Long creator_id = -1L;
    public static Long modified_id = -1L;
    public static Long version = 1L;

    /*
        START TRANSACTION;
           xxxxx
        COMMIT;
     */

    @SneakyThrows
    public static void main(String[] args) {

        String propertyValuePath = "C:\\Users\\EDZ\\Documents\\初始化\\产品预归类\\境内货源地代码表.xlsx";

        List<Entity> list = readValue(propertyValuePath);

        System.out.println("文档总数=" + list.size());

        StringBuilder valueSql = convertToSqlValue(list);
        StringBuilder languageSql = convertToSqlLanguage(list);

        //写入文件
        FileUtils.writeFile(valueSql, "valueSql.sql");
        FileUtils.writeFile(languageSql, "languageSql.sql");
    }

    private static StringBuilder convertToSqlLanguage(List<Entity> list) {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(list)) {
            sb.append("insert into t_property_value_language (`property_value_lan_id`, `property_value_id`, `preferred_language`, `is_deleted`, `create_time`, `update_time`, `creator_id`, `modified_id`, `version`, `property_value_name`)");
            sb.append(" values ").append("\n");
            int i = 0;
            for (Entity entity : list) {
                i++;
                String name = entity.getColumn1();
                String code = entity.getColumn2();
                Long valueId = entity.getId();
                sb.append(" ( ");
                sb.append(++languageId).append(",");
                sb.append(valueId).append(",");
                sb.append("'").append(Locale.SIMPLIFIED_CHINESE.toString()).append("'").append(",");
                sb.append(is_deleted).append(",");
                sb.append(create_time).append(",");
                sb.append(update_time).append(",");
                sb.append(creator_id).append(",");
                sb.append(modified_id).append(",");
                sb.append(version).append(",");
                sb.append("'").append(name).append("-").append(code).append("'");
                //最后一次 不加,
                if (list.size() == i) {
                    sb.append(" );").append("\n");
                } else {
                    sb.append(" ),").append("\n");
                }
            }
        }
        System.out.println("languageSql: " + list.size());
        return sb;
    }

    private static StringBuilder convertToSqlValue(List<Entity> list) {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(list)) {
            sb.append("insert into t_property_value (`property_value_id`, `property_id`, `is_deleted`, `create_time`, `update_time`, `creator_id`, `modified_id`, `version`, `platform`, `status`)");
            sb.append(" values ").append("\n");
            int i = 0;
            for (Entity entity : list) {
                i++;
                sb.append(" ( ");
                sb.append(++valueId).append(",");
                sb.append(propertyId).append(",");
                sb.append(is_deleted).append(",");
                sb.append(create_time).append(",");
                sb.append(update_time).append(",");
                sb.append(creator_id).append(",");
                sb.append(modified_id).append(",");
                sb.append(version).append(",");
                sb.append("'").append("product_GLO").append("'").append(",");
                sb.append(0);
                //最后一次 不加,
                if (list.size() == i) {
                    sb.append(" );").append("\n");
                } else {
                    sb.append(" ),").append("\n");
                }
                entity.setId(valueId);
            }
        }
        System.out.println("valueSql: " + list.size());
        return sb;
    }

    private static List<Entity> readValue(String path) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            return ExcelUtil.readExcelByHeadLine(new BufferedInputStream(inputStream), Entity.class, 1, 2);
        }
    }

}
