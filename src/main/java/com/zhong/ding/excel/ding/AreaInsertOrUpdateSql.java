package com.zhong.ding.excel.ding;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhong.utils.FileUtils;
import com.zhong.ding.excel.Entity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @date 2022/8/26 14:43
 */
public class AreaInsertOrUpdateSql {

    //    public static Long id = 2001119980L;
//    public static Long languageId = 2001123180L;
    public static Long id = 2001121840L;
    public static Long languageId = 2001126890L;

    /**
     * select a.id, a.parent_id,a.area_level,
     * max(case x.preferred_language when 'en_US' then x.area_name else null end ) as 英文,
     * max(case x.preferred_language when 'zh_CN' then x.area_name else null end ) as 中文,
     * max(case x.preferred_language when 'th_TH' then x.area_name else null end ) as 泰语,
     * a.features
     * from t_area_info as a
     * inner join t_area_info_language as x
     * on a.id = x.area_info_id
     * where country_id = 2000000045
     * and a.is_deleted = 0 and x.is_deleted = 0
     * group by a.id
     * ;
     */
    public static void main2(String[] args) {

        String path_cn = "C:\\Users\\EDZ\\Documents\\0订数据\\订正test环境\\cn地区.xls";
        String path_test = "C:\\Users\\EDZ\\Documents\\0订数据\\订正test环境\\test地区.xls";

        List<Entity> list_cn = FileUtils.readExcelByPath(path_cn, 1, 1);
        List<Entity> list_test = FileUtils.readExcelByPath(path_test, 1, 1);
        System.out.println(
                "path_cn: " + list_cn.size() + " ; " +
                        "path_test: " + list_test.size()
        );
        if (CollectionUtils.isEmpty(list_cn) || CollectionUtils.isEmpty(list_test)) {
            return;
        }

        List<String> oneArea_cn = list_cn.stream().filter(x -> x.getColumn3().equals("1")).map(Entity::getColumn4).collect(Collectors.toList());
        List<String> oneArea_test = list_test.stream().filter(x -> x.getColumn3().equals("1.0") || x.getColumn3().equals("1")).map(Entity::getColumn4).collect(Collectors.toList());
        System.out.println(
                "oneArea_cn: " + oneArea_cn.size() + " ; " +
                        "oneArea_test: " + oneArea_test.size()
        );
        Collection<String> intersection = CollectionUtils.intersection(oneArea_cn, oneArea_test);
        System.out.println(JSON.toJSONString(intersection));

        //一级
        Map<String, Entity> cnMap = list_cn.stream().filter(x -> x.getColumn3().equals("1")).collect(Collectors.toMap(Entity::getColumn4, Function.identity()));
        Map<String, Entity> testMap = list_test.stream().filter(x -> x.getColumn3().equals("1.0") || x.getColumn3().equals("1")).collect(Collectors.toMap(Entity::getColumn4, Function.identity()));

        //一级
        Map<String, Entity> matchedMap = Maps.newHashMap();
        intersection.forEach(x -> {
            if (cnMap.containsKey(x) && testMap.containsKey(x)) {
                Entity entity = cnMap.get(x);
                String parentId_test = testMap.get(x).getColumn1();
                entity.setColumn2(parentId_test);
                matchedMap.put(entity.getColumn1(), entity);
            }
        });
        System.out.println("matchedMap.size()：" + matchedMap.size());

        //二级
        Map<String, Entity> cnMap_two = list_cn.stream().filter(x -> x.getColumn3().equals("2")).collect(Collectors.toMap(Entity::getColumn1, Function.identity()));
        Map<String, Entity> testMap_two = list_test.stream().filter(x -> x.getColumn3().equals("1.0") || x.getColumn3().equals("1")).collect(Collectors.toMap(Entity::getColumn4, Function.identity()));

        List<Entity> two_cn = list_cn.stream().filter(x -> x.getColumn3().equals("2")).collect(Collectors.toList());
        two_cn.forEach(x -> {
            String en = x.getColumn4();


        });


//  mapping关系
//  都拿二级,
//  prod: ID， parentID
//  test：ID， parentID
//  匹配：prod和test匹配到，mapping：prod的ID是三级的parentID，可以获取三级，对应test 二级的ID

//  一份所有数据，为了获取name

        ArrayList<String> sql = Lists.newArrayList();

        System.out.println(testMap.size());
        List<Entity> collect = list_cn.stream().filter(x -> !x.getColumn3().equals("1")).collect(Collectors.toList());
        for (Entity entity : collect) {
            String areaID = entity.getColumn1();
            String parentID = entity.getColumn2();
            String level = entity.getColumn3();
            String en = entity.getColumn4();
            String zh = entity.getColumn5();
            String th = entity.getColumn6();

            //生成二级地区
            if (matchedMap.containsKey(parentID)) {
                Entity area = matchedMap.get(parentID);
                String parentID_test = area.getColumn2();
                sql.add(
                        "INSERT INTO `t_area_info` (`id`, `parent_id`, `country_id`, `area_level`, `platform`, `creator_id`, `create_time`, `is_deleted`, `version`) " +
                                "VALUES (" + id + ", " + parentID_test + ", 2000000045, 2, 'product_GLO', -55, 1661505620000, 0, 0);"
                );

                sql.add(
                        "INSERT INTO `t_area_info_language` (`id`, `area_info_id`, `area_name`, `preferred_language`, `creator_id`, `create_time`,  `is_deleted`, `version`) " +
                                "VALUES (" + languageId + ", " + id + ", '" + en + "', 'en_US', -55, 1661505620000, 0, 0);"
                );
                languageId++;
                sql.add(
                        "INSERT INTO `t_area_info_language` (`id`, `area_info_id`, `area_name`, `preferred_language`, `creator_id`, `create_time`,  `is_deleted`, `version`) " +
                                "VALUES (" + languageId + ", " + id + ", '" + th + "', 'th_TH', -55, 1661505620000, 0, 0);"
                );
                id++;
                languageId++;
            }


            //生成三级地区
            if (cnMap_two.containsKey(parentID)) {

                String parentID_test = "";
                sql.add(
                        "INSERT INTO `t_area_info` (`id`, `parent_id`, `country_id`, `area_level`, `platform`, `creator_id`, `create_time`, `is_deleted`, `version`) " +
                                "VALUES (" + id + ", " + parentID_test + ", 2000000045, 2, 'product_GLO', -55, 1661505620000, 0, 0);"
                );

                sql.add(
                        "INSERT INTO `t_area_info_language` (`id`, `area_info_id`, `area_name`, `preferred_language`, `creator_id`, `create_time`,  `is_deleted`, `version`) " +
                                "VALUES (" + languageId + ", " + id + ", '" + en + "', 'en_US', -55, 1661505620000, 0, 0);"
                );
                languageId++;
                sql.add(
                        "INSERT INTO `t_area_info_language` (`id`, `area_info_id`, `area_name`, `preferred_language`, `creator_id`, `create_time`,  `is_deleted`, `version`) " +
                                "VALUES (" + languageId + ", " + id + ", '" + th + "', 'th_TH', -55, 1661505620000, 0, 0);"
                );
                id++;
                languageId++;
            }
        }

        FileUtil.writeUtf8Lines(sql, new File("segment.sql"));

        System.out.println(sql.size());
    }

    //生成三级的地区
    public static void main(String[] args) {

//    查出test的二级地区，和 prod的匹配，拿到prod的三级地址，生成这些地址即可

        String path_cn = "C:\\Users\\EDZ\\Documents\\0订数据\\订正test环境\\prod-cn-地区.xls";
        String path_test = "C:\\Users\\EDZ\\Documents\\0订数据\\订正test环境\\test地区v2.xls";

        List<Entity> list_cn = FileUtils.readExcelByPath(path_cn, 1, 1);
        List<Entity> list_test = FileUtils.readExcelByPath(path_test, 1, 1);
        System.out.println(
                "path_cn: " + list_cn.size() + " ; " +
                        "path_test: " + list_test.size()
        );
        if (CollectionUtils.isEmpty(list_cn) || CollectionUtils.isEmpty(list_test)) {
            return;
        }

        Map<String, String> one_cn = list_cn.stream().filter(x -> x.getColumn3().equals("1") || x.getColumn3().equals("1.0"))
                .collect(Collectors.toMap(Entity::getColumn1, Entity::getColumn2));
        Map<String, List<Entity>> two_cn = list_cn.stream().filter(x -> x.getColumn3().equals("2") || x.getColumn3().equals("2.0"))
                .collect(Collectors.groupingBy(Entity::getColumn2));
        Map<String, List<Entity>> three_cn = list_cn.stream().filter(x -> x.getColumn3().equals("3") || x.getColumn3().equals("3.0"))
                .collect(Collectors.groupingBy(Entity::getColumn2));

        Map<String, String> one_test = list_test.stream().filter(x -> x.getColumn3().equals("1") || x.getColumn3().equals("1.0"))
                .collect(Collectors.toMap(Entity::getColumn4, Entity::getColumn1, (s, e) -> s));
        Map<String, List<Entity>> two_test = list_test.stream().filter(x -> x.getColumn3().equals("2") || x.getColumn3().equals("2.0"))
                .collect(Collectors.groupingBy(Entity::getColumn4));
        CaseInsensitiveMap<String, String> caseInsensitiveMap = new CaseInsensitiveMap<>(one_test);
        CaseInsensitiveMap<String, List<Entity>> caseInsensitiveMap2 = new CaseInsensitiveMap<>(two_test);

        ArrayList<String> sql = Lists.newArrayList();
        for (Entity entity : list_cn) {
            String areaID = entity.getColumn1();
            String parentID = entity.getColumn2();
            String level = entity.getColumn3();
            String en = entity.getColumn4();
            String features = entity.getColumn7();

            String oneName = one_cn.get(areaID);
            if (caseInsensitiveMap.containsKey(oneName)) {//一级匹配到
                String oneId = caseInsensitiveMap.get(oneName);
                List<Entity> list = two_cn.get(areaID);//取出二级
                list.forEach(x -> {
                    if (caseInsensitiveMap2.containsKey(x.getColumn4())) {
                        List<Entity> entityList = caseInsensitiveMap2.get(x.getColumn4());
                        entityList.forEach(o -> {
                            if (o.getColumn2().equals(oneId)) {//二级匹配到
                                String parent = o.getColumn1();
                                List<Entity> entities = three_cn.get(x.getColumn1());


                            }
                        });
                    }
                });
            }
        }
    }
}
