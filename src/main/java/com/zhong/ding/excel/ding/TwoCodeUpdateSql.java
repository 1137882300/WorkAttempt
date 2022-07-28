package com.zhong.ding.excel.ding;

import com.zhong.Utils.FileUtils;
import com.zhong.ding.excel.Entity;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @date 2022/7/19 20:16
 */
public class TwoCodeUpdateSql {

    /**
     * sql ：二位简称
     * select a.country_id,
     * max(case x.preferred_language when 'en_US' then x.country_name else null end ) as 英文版,
     * max(case x.preferred_language when 'zh_CN' then x.country_name else null end ) as 中文版,
     * a.country_two_abbreviation as 二位简称
     * from t_country_locale as a
     * inner join t_country_locale_language as x
     * on a.country_id = x.country_id
     * where a.is_deleted= 0 and x.is_deleted= 0
     * group by a.country_id
     * ;
     */

    @SneakyThrows
    public static void main(String[] args) {

        String prodPath = "C:\\Users\\EDZ\\Documents\\test环境更新数据\\国家二位简称.xls";
        String testPath = "C:\\Users\\EDZ\\Documents\\test环境更新数据\\test国家.xlsx";

        List<Entity> prodCountryList = FileUtils.readExcelByPath(prodPath, 1, 1);
        List<Entity> testCountryList = FileUtils.readExcelByPath(testPath, 1, 1);

        if (CollectionUtils.isEmpty(prodCountryList) || CollectionUtils.isEmpty(testCountryList)) {
            return;
        }

        Optional.of(testCountryList).ifPresent(x -> {
            x.removeIf(o -> o.getColumn4() != null);
        });
        System.out.println("testCountryList.size:" + testCountryList.size());

        Map<String, String> prodMap = prodCountryList.stream().collect(Collectors.toMap(Entity::getColumn3, Entity::getColumn4, (s, e) -> e));

        StringBuilder sb = new StringBuilder();
        for (Entity entity : testCountryList) {
            String zhName = entity.getColumn3().trim();
            if (prodMap.containsKey(zhName)) {
                String twoCode = prodMap.get(zhName);

                sb.append(" update t_country_locale set `country_two_abbreviation` = ");
                sb.append("'").append(twoCode).append("'").append(", ");
                sb.append("modified_id = -333 ");
                sb.append("where `country_id` = ");
                sb.append(entity.getColumn1()).append(";");
                sb.append("\n");

            }
        }

        FileUtils.writeFile(sb, "更新国家二位简码.sql");

    }

}
