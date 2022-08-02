package com.zhong.ding.csv;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhong.Utils.FileUtils;
import com.zhong.ding.excel.Entity;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @date 2022/7/27 14:06
 */
public class Britain_Address {

    /**
     * -- 查询英国下的地区数据
     * select a.id, a.parent_id, a.area_level ,b.area_name from t_area_info as a
     * inner join t_area_info_language as b
     * on a.id = b.area_info_id
     * where a.is_deleted = 0 and b.is_deleted = 0
     * and a.country_id = 2000000001 and b.preferred_language =  'en_US'
     * order by a.area_level
     * ;
     */

    public static Long update_time = 1658827003000L;
    public static Long creator_id = -19L;

    @SneakyThrows
    public static void main(String[] args) {

        String sourcePath = "C:\\Users\\EDZ\\Downloads\\英国所有地区（全）.csv";
        String prodPath = "C:\\Users\\EDZ\\Documents\\0订数据\\补全地区邮编\\英国下的地区.xls";

        List<Entity> entityList = FileUtils.readExcelByPath(prodPath, 1, 1);
        List<Entity> list = readLineCSV(sourcePath);

        if (CollectionUtils.isEmpty(entityList) || CollectionUtils.isEmpty(list)) {
            return;
        }

        StringBuilder sqlBritainAddress = convertToUpdateBritainAddressSql(entityList, list);

        FileUtils.writeFile(sqlBritainAddress, "sqlBritainAddress.sql");

    }

    private static StringBuilder convertToUpdateBritainAddressSql(List<Entity> entityList, List<Entity> list) {
        //同一级会有相同的name，但parent不同

//            k:name,v:firstId
        Map<String, String> oneMap = entityList.stream()
                .filter(x -> x.getColumn3().equals(Integer.toString(1)))
                .collect(Collectors.toMap(Entity::getColumn4, Entity::getColumn1, (s, e) -> s));
//            k:name,v:list
        Map<String, Entity> twoGroupMap = entityList.stream()
                .filter(x -> x.getColumn3().equals(Integer.toString(2)))
                .collect(Collectors.toMap(Entity::getColumn4, Function.identity()));

        Map<String, List<Entity>> threeGroupMap = entityList.stream()
                .filter(x -> x.getColumn3().equals(Integer.toString(3)))
                .collect(Collectors.groupingBy(Entity::getColumn4));

        CaseInsensitiveMap<String, String> caseInsensitiveMap = new CaseInsensitiveMap<>(oneMap);
        CaseInsensitiveMap<String, Entity> caseInsensitiveMap2 = new CaseInsensitiveMap<>(twoGroupMap);
        CaseInsensitiveMap<String, List<Entity>> caseInsensitiveMap3 = new CaseInsensitiveMap<>(threeGroupMap);

        StringBuilder sb = new StringBuilder();
        for (Entity entity : list) {
            String one = entity.getColumn1().replace("\"", "");
            String two = entity.getColumn2().replace("\"", "");
            String three = entity.getColumn3().replace("\"", "");
            String postalCode = entity.getColumn4().replace("\"", "");
            if (caseInsensitiveMap.containsKey(one)) {
                String firstId = caseInsensitiveMap.get(one);
                if (caseInsensitiveMap2.containsKey(two)) {
                    Entity entityTwo = caseInsensitiveMap2.get(two);
                    if (entityTwo.getColumn2().equals(firstId)) {
                        //匹配到一级和二级。二级的parentID == 一级的ID
                        if (caseInsensitiveMap3.containsKey(three)) {
                            List<Entity> list1 = caseInsensitiveMap3.get(three);
                            for (Entity entityThree : list1) {
                                if (entityThree.getColumn2().equals(entityTwo.getColumn1())) {
                                    //一二三级都匹配到。三级的parentID == 二级的ID
                                    String areaId = entityThree.getColumn1();
                                    sb.append("update t_area_info set ");
                                    sb.append("version = version + 1").append(", ");
                                    sb.append("update_time = ").append(update_time).append(", ");
                                    sb.append("modified_id = ").append(creator_id).append(", ");
                                    sb.append("features = ").append("'{\"post_code\":\"").append(postalCode).append("\"}'").append(" ");
                                    sb.append("where id = ").append(areaId).append(" ");
                                    sb.append(";\n");
                                }
                            }
                        }
                    }
                }
            }
        }
        return sb;
    }


    @SneakyThrows
    public static List<Entity> readLineCSV(String path) {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        reader.readLine();//先读取第一行(过滤第一行)
        String line;
        List<Entity> list = Lists.newLinkedList();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            if (split[1].equalsIgnoreCase("No")) {
                continue;
            }
            if (StringUtils.isBlank(split[12]) || StringUtils.isBlank(split[7])
                    || StringUtils.isBlank(split[8]) || StringUtils.isBlank(split[0])) {
                continue;
            }
            if (split[12].equals("\"\"") || split[7].equals("\"\"")
                    || split[8].equals("\"\"") || split[0].equals("\"\"")) {
                continue;
            }

            Entity entity = new Entity();
            entity.setColumn1(split[12]);//一级
            entity.setColumn2(split[7]);//二级
            entity.setColumn3(split[8]);//三级
            entity.setColumn4(split[0].substring(0, split[0].indexOf(" ")));//邮编
            if (Objects.nonNull(entity.getColumn1()) && Objects.nonNull(entity.getColumn2())
                    && Objects.nonNull(entity.getColumn3()) && Objects.nonNull(entity.getColumn4())) {
                list.add(entity);
//                if ("0.409385".equals(split[42])) {
//                    System.out.println(Arrays.toString(split));
//                }
//                if ("EX14".equals(split[42])){
//                    System.out.println(Arrays.toString(split));
//                }
            }
        }

        //去重
        List<Entity> duplicate = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(Entity::getColumn1).thenComparing(Entity::getColumn2).thenComparing(Entity::getColumn3))
        ), ArrayList::new));


//        System.out.println("list.size=" + list.size());
//        System.out.println("list.去重后=" + duplicate.size());
//        System.out.println(JSON.toJSONString(duplicate));
        return duplicate;
    }


}
/**
 * select a.id, a.parent_id, a.area_level ,b.area_name, count(b.area_name) from t_area_info as a
 * inner join t_area_info_language as b
 * on a.id = b.area_info_id
 * where a.is_deleted = 0 and b.is_deleted = 0
 * and a.country_id = 2000000001 and b.preferred_language =  'en_US'
 * and area_level = 2
 * group by area_name
 * having count(b.area_name)> 1
 * order by area_name
 * ;
 */