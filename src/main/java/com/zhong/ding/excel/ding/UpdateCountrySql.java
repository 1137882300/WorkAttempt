package com.zhong.ding.excel.ding;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhong.Utils.FileUtils;
import com.zhong.ding.excel.Entity;
import com.zhong.ding.excel.ExcelUtil;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @date 2022/8/11 15:45
 */
public class UpdateCountrySql {
    /**
     * select a.country_id,post_code, phone_area_code ,country_name
     * from t_country_locale as a
     * inner join
     * (
     * select country_name, country_id from t_country_locale_language where is_deleted = 0 and preferred_language = 'zh_CN'
     * )as x
     * on a.country_id = x.country_id
     * where post_code is null
     * or phone_area_code is null
     * and a.is_deleted = 0
     * ;
     * C:\Users\EDZ\Documents\0订数据\补全国家邮编和区号\国家邮编和区号（prod导出）.xls
     */
    @SneakyThrows
    public static void main(String[] args) {

        String prodPath = "C:\\Users\\EDZ\\Documents\\0订数据\\补全国家邮编和区号\\国家邮编和区号（prod导出）.xls";
//        String testPath = "C:\\Users\\EDZ\\Desktop\\meta查询.xlsx";

        List<Entity> list = FileUtils.readExcelByPath(prodPath, 1, 1);
        List<Entity> json = FileUtils.readJson("country.json");

        if (CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(json)) {
            return;
        }

        System.out.println(
                list.size() + "\n" + json.size()
        );

        System.out.println(
                JSON.toJSONString(list.stream().map(Entity::getColumn4).collect(Collectors.toList()))
        );

        Map<String, String> map = list.stream().collect(Collectors.toMap(Entity::getColumn4, Entity::getColumn1));
        StringBuilder sql = convertToSql(map, json);

        FileUtils.writeFile(sql, "updateCountry.sql");

    }


    private static StringBuilder convertToSql(Map<String, String> map, List<Entity> json) {
        StringBuilder sb = new StringBuilder();
        List<String> noNameList = Lists.newArrayList();
        json.forEach(x -> {
            String name = x.getColumn1();
            String areaCode = x.getColumn3();
            String postCode = x.getColumn4();

            if (map.containsKey(name)) {
                sb.append("update t_country_locale set ");
                sb.append("version = version+1, update_time = 1660208096000 ,");
                if (StringUtils.isNotBlank(postCode)) {
                    sb.append("post_code = ").append(postCode).append(", ");
                }
                sb.append("phone_area_code = ").append(areaCode).append(" ");
                sb.append("where country_id = ").append(map.get(name)).append(";\n");
            } else {
                noNameList.add(name);
            }
        });
        System.out.println(JSON.toJSONString(noNameList));
        return sb;
    }


}
