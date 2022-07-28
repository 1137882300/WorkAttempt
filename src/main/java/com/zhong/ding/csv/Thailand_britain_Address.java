package com.zhong.ding.csv;

import com.zhong.Utils.FileUtils;
import com.zhong.ding.excel.Entity;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.commons.lang3.StringUtils;

import java.net.CookieManager;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @date 2022/7/26 14:18
 */
public class Thailand_britain_Address {

    /**
     * -- 查询泰国下的地区数据
     * select a.id, a.parent_id, a.area_level ,b.area_name from t_area_info as a
     * inner join t_area_info_language as b
     * on a.id = b.area_info_id
     * where a.is_deleted = 0 and b.is_deleted = 0
     * and a.country_id = 2000000045 and b.preferred_language =  'th_TH'
     * order by a.area_level
     * ;
     * <p>
     * -- 查询英国下的地区数据
     * select a.id, a.parent_id, a.area_level ,b.area_name from t_area_info as a
     * inner join t_area_info_language as b
     * on a.id = b.area_info_id
     * where a.is_deleted = 0 and b.is_deleted = 0
     * and a.country_id = 2000000001 and b.preferred_language =  'en_US'
     * order by a.area_level
     */

    public static Long update_time = 1658827003000L;
    public static Long creator_id = -19L;
    public static Long areaId = 100015310L;//要递减
    public static Long areaLanId = 1999837532L;//要递减
    public static Long thailandId = 2000000045L;//泰国ID
    public static String platform = "product_GLO";//泰国ID

    /**
     * 补全地区邮编
     */
    @SneakyThrows
    public static void main(String[] args) {

//忽略大小写比较

        String thailandAddressPath = "C:\\Users\\EDZ\\Documents\\0订数据\\补全地区邮编\\泰国下的地区.xls";
        String britainAddressPath = "C:\\Users\\EDZ\\Documents\\0订数据\\补全地区邮编\\英国下的地区.xls";

        String thailandAddressPostCodePath = "C:\\Users\\EDZ\\Documents\\0订数据\\补全地区邮编\\泰国地址20220617.csv";
        String britainAddressPostCodePath = "C:\\Users\\EDZ\\Documents\\0订数据\\补全地区邮编\\postcodes.csv";

        List<Entity> entityList = FileUtils.readExcelByPath(thailandAddressPath, 1, 1);
        List<Entity> csvList = FileUtils.readLineCSV(thailandAddressPostCodePath);
        if (CollectionUtils.isEmpty(entityList) || CollectionUtils.isEmpty(csvList)) {
            return;
        }

        System.out.printf("thailandAddressPath.size:%s%nthailandAddressPostCodePath:%s%n ", entityList.size(), csvList.size());

        StringBuilder sqlThailandAddress = convertToUpdateThailandAddressSql(entityList, csvList);

        assert sqlThailandAddress != null;
        FileUtils.writeFile(sqlThailandAddress, "sqlThailandAddress.sql");


    }

    private static StringBuilder convertToUpdateThailandAddressSql(List<Entity> entityList, List<Entity> csvList) {
        if (CollectionUtils.isNotEmpty(entityList) && CollectionUtils.isNotEmpty(csvList)) {

            //同一级会有相同的name，但parent不同

//            k:name,v:firstId
            Map<String, String> oneMap = entityList.stream()
                    .filter(x -> x.getColumn3().equals(Integer.toString(1)))
                    .collect(Collectors.toMap(Entity::getColumn4, Entity::getColumn1, (s, e) -> s));
//            k:name,v:list
            Map<String, List<Entity>> twoGroupMap = entityList.stream()
                    .filter(x -> x.getColumn3().equals(Integer.toString(2)))
                    .collect(Collectors.groupingBy(Entity::getColumn4));

            Map<String, Entity> threeGroupMap = entityList.stream()
                    .filter(x -> x.getColumn3().equals(Integer.toString(3)))
                    .collect(Collectors.toMap(Entity::getColumn4, Function.identity()));

            CaseInsensitiveMap<String, String> caseInsensitiveMap = new CaseInsensitiveMap<>(oneMap);
            CaseInsensitiveMap<String, List<Entity>> caseInsensitiveMap2 = new CaseInsensitiveMap<>(twoGroupMap);
            CaseInsensitiveMap<String, Entity> caseInsensitiveMap3 = new CaseInsensitiveMap<>(threeGroupMap);

            StringBuilder sb = new StringBuilder();
            StringBuilder sbUpdate = new StringBuilder();
            StringBuilder sbLanguage = new StringBuilder();
            for (Entity x : csvList) {
                String province = x.getColumn1().replace("\"", "");
                String city = x.getColumn2().replace("\"", "");
                String district = x.getColumn3().replace("\"", "");
                String postalCode = x.getColumn4();
                if (caseInsensitiveMap.containsKey(province)) {
                    String firstId = caseInsensitiveMap.get(province);
                    if (caseInsensitiveMap2.containsKey(city)) {
                        List<Entity> list = caseInsensitiveMap2.get(city);
                        for (Entity entity : list) {
                            if (caseInsensitiveMap3.containsKey(district)) {
                                //已存在走更新
                                Entity threeEntity = caseInsensitiveMap3.get(district);
                                if (entity.getColumn1().equals(threeEntity.getColumn2())) {
                                    //二级的ID == 三级的parentID
                                    sbUpdate.append("update t_area_info set ");
                                    sbUpdate.append("version = version + 1").append(", ");
                                    sbUpdate.append("update_time = ").append(update_time).append(", ");
                                    sbUpdate.append("modified_id = ").append(creator_id).append(", ");
                                    sbUpdate.append("post_code = ").append(postalCode).append(" ");
                                    sbUpdate.append("where id = ").append(threeEntity.getColumn1()).append(" ");
                                    sbUpdate.append(";\n");
                                }
                            } else {
                                //不存在走插入
                                if (entity.getColumn2().equals(firstId)) {
                                    sb.append("insert into t_area_info (`id`, `parent_id`, `country_id`, `area_level`, `platform`, `creator_id`, `modified_id`, `create_time`, `update_time`, `has_children`, `post_code`) ");
                                    sb.append("values ( ");
                                    sb.append(areaId).append(", ");
                                    sb.append(entity.getColumn1()).append(", ");
                                    sb.append(thailandId).append(", ");
                                    sb.append(3).append(", ");
                                    sb.append("\"").append(platform).append("\"").append(", ");
                                    sb.append(creator_id).append(", ");
                                    sb.append(creator_id).append(", ");
                                    sb.append(update_time).append(", ");
                                    sb.append(update_time).append(", ");
                                    sb.append(0).append(", ");
                                    sb.append(postalCode).append(" ");
                                    sb.append("); ").append("\n");

                                    sb.append("insert into t_area_info_language (`id`, `area_info_id`, `preferred_language`, `creator_id`, `modified_id`, `create_time`, `update_time`, `area_name`) ");
                                    sb.append("values ( ");
                                    sb.append(areaLanId).append(", ");
                                    sb.append(areaId).append(", ");
                                    sb.append("\"").append("th_TH").append("\"").append(", ");
                                    sb.append(creator_id).append(", ");
                                    sb.append(creator_id).append(", ");
                                    sb.append(update_time).append(", ");
                                    sb.append(update_time).append(", ");
                                    sb.append("\"").append(district).append("\"").append(" ");
                                    sb.append("); ").append("\n");

                                    areaId--;
                                    areaLanId--;
                                }
                            }
                        }
                    }
                }
            }
            return sb.append(sbUpdate);
        }
        return null;
    }

}


/**
 * select a.id, a.parent_id, a.area_level ,b.area_name from t_area_info as a
 * inner join t_area_info_language as b
 * on a.id = b.area_info_id
 * where a.is_deleted = 0 and b.is_deleted = 0
 * and a.country_id = 2000000045 and b.preferred_language =  'th_TH'
 * and area_level = 2 and  area_name = 'คลองสามวา'
 * order by a.area_level
 * ;
 * <p>
 * select a.id, a.parent_id, a.area_level ,b.area_name, count(b.area_name) from t_area_info as a
 * inner join t_area_info_language as b
 * on a.id = b.area_info_id
 * where a.is_deleted = 0 and b.is_deleted = 0
 * and a.country_id = 2000000045 and b.preferred_language =  'th_TH'
 * and area_level = 3
 * group by area_name
 * having count(b.area_name)> 1
 * order by area_name
 * ;
 * <p>
 * select b.area_name from t_area_info as a
 * inner join t_area_info_language as b
 * on a.id = b.area_info_id
 * where a.is_deleted = 0 and b.is_deleted = 0
 * and a.country_id = 2000000045 and b.preferred_language =  'th_TH'
 * and area_level = 3
 * ;
 */
