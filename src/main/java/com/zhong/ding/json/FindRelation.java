package com.zhong.ding.json;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhong.Utils.FileUtils;
import com.zhong.ding.excel.Entity;
import lombok.SneakyThrows;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @date 2022/8/19 15:18
 */
public class FindRelation {

    /**
     * 查shopID
     * select item_id, shop_id from t_item_base_info_copy
     * where item_id in (
     * 3000051960, 3000051963, 3000051969, 3000015508, 3000015503, 3000092548, 3000017139, 3000043778, 3000043777, 3000021945, 3000017137, 3000017138, 3000049913, 3000093352, 3000054766, 3000051972, 3000051974, 3000054763, 3000050726, 3000015519, 3000051976, 3000015516, 3000051933, 3000051978, 3000043767, 3000015523, 3000043766, 3000015445, 3000017144, 3000017142, 3000092452, 3000064209, 3000017143, 3000017140, 3000017141, 3000050170, 3000022063, 3000022064, 3000054773, 3000015408, 3000054737, 3000061590, 3000051989, 3000085474, 3000064212, 3000015534, 3000015413, 3000015610, 3000015379, 3000015297, 3000043753, 3000015571, 3000064337, 3000064215, 3000064336, 3000015573, 3000056046, 3000021324, 3000015490, 3000050184, 3000062837, 3000050182, 3000050186, 3000056248, 3000052000, 3000056249, 3000052001, 3000049973, 3000051957, 3000015536, 3000051998, 3000043943, 3000064222, 3000043744, 3000021934, 3000015544, 3000015467, 3000043740, 3000064225, 3000064346, 3000021932, 3000043748, 3000056250
     * )
     * C:\Users\EDZ\Documents\0订数据\待订正的脏数据\item-shop.xls
     * ;
     */

    public static Long id = 30000000329L;

    @SneakyThrows
    public static void main(String[] args) {
//        item上的前台店铺属于哪个tmp上的后台店铺
//      item前台店铺-tmp上后台店铺,怎么知道这个前台店铺属于这个后台店铺

        List<Entity> itemList = FileUtils.readExcelByPath("C:\\Users\\EDZ\\Documents\\0订数据\\待订正的脏数据\\item-shop2.xls", 1, 1);
        List<Entity> tmpList = FileUtils.readExcelByPath("C:\\Users\\EDZ\\Documents\\0订数据\\待订正的脏数据\\tmp-shop2.xls", 1, 1);

        //前台店铺，item上
        ArrayList<String> shop1 = Lists.newArrayList("3000002053", "3000002073", "3000002074", "3000002075", "3000002077", "3000002078", "3000002079", "3000002080", "3000002083", "3000002085", "3000002087", "3000002113", "3000002194", "3000002263");
        ArrayList<String> shop2 = Lists.newArrayList("3000002181");
        //k：后台店铺，v：前台店铺
        Map<String, List<String>> shopMap = new HashMap<>();
        shopMap.put("3100000374", shop1);
        shopMap.put("3000000389", shop2);

        Map<String, String> item_tmp = FileUtils.readJsonToMap("没有关联关系且itemID对用多个tmp.json");
        //k:item, v:tmp
        Map<String, List<String>> item_tmpList = new HashMap<>();
        item_tmp.forEach((k, v) -> {
            item_tmpList.put(k, Arrays.asList(v.split(",")));
        });
        Map<String, String> item_shop = itemList.stream().collect(Collectors.toMap(Entity::getColumn1, Entity::getColumn2));
        Map<String, String> tmp_shop = tmpList.stream().collect(Collectors.toMap(Entity::getColumn1, Entity::getColumn2));
//        foreground
//        background
        ArrayList<String> repeat = Lists.newArrayList();
        Map<String, String> result = new HashMap<>();
//        取出 前台店铺，对比映射，取出后台店铺，对比映射。都能映射到就取出item和tmp
        AtomicInteger k = new AtomicInteger();
        for (Map.Entry<String, String> entry : item_shop.entrySet()) {
            String item = entry.getKey();
            String shop_foreground = entry.getValue();
            shopMap.values().forEach(x -> {
                if (x.contains(shop_foreground)) {
                    if (item_tmpList.containsKey(item)) {
                        List<String> tmps = item_tmpList.get(item);
                        tmps.forEach(tmp -> {
                            if (tmp_shop.containsKey(tmp)) {
                                String shop_background = tmp_shop.get(tmp);
                                if (shopMap.containsKey(shop_background)) {
                                    if (result.containsKey(item)) {
//                                        System.out.println("item:" + item + "   tmp:" + tmp + "   " + k.getAndIncrement());
                                        repeat.add(item);
                                    } else {
                                        result.put(item, tmp);
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }

        //删除重复
        repeat.forEach(result::remove);

//        System.out.println(result.size());
//        String string = JSON.toJSONString(result);
//        System.out.println(string);

        List<String> sql = Lists.newArrayList();
        for (Map.Entry<String, String> entry : result.entrySet()) {
            String master = entry.getValue();
            String slave = entry.getKey();

            sql.add(
                    "INSERT INTO `t_global_relation` " +
                            "(`id`, `type`, `master`, `slave`, `status`, `platform`, `creator_id`, `modified_id`, `create_time`, `update_time`, `system_update_time`, `is_deleted`, `version`, `features`) " +
                            "VALUES " +
                            "(" + id-- + ", 'ITEMTMP_ITEM', '" + master + "', '" + slave + "', 0, 'bbmall_GLO', -1, -1, 1660912776000, 1660912776000, 1660912776000, 0, 1, null);"

            );
        }

        FileUtil.writeUtf8Lines(sql, new File("relation-un.sql"));


        System.out.println(JSON.toJSONString(repeat.stream().distinct().collect(Collectors.toList())));

        //tmpID list
//        List<String> collect = new ArrayList<>(item_tmpList.values()).stream().flatMap(Collection::stream).collect(Collectors.toList());
//        System.out.println(JSON.toJSONString(collect));
    }


    public static void main2(String[] args) {
        ArrayList<String> list = Lists.newArrayList("3000054766", "3000054763", "3000092452", "3000064209", "3000054773", "3000061590", "3000064212", "3000064337", "3000064336", "3000064215", "3000062837", "3000056248", "3000056249", "3000051957", "3000051998", "3000064222", "3000064225", "3000056250");
        Map<String, String> item_tmp = FileUtils.readJsonToMap("没有关联关系且itemID对用多个tmp.json");

        HashMap<String, String> hashMap = Maps.newHashMap();
        list.forEach(x -> {
            if (item_tmp.containsKey(x)) {
                hashMap.put(x, item_tmp.get(x));
            }
        });
        FileUtil.writeUtf8String(JSON.toJSONString(hashMap), new File("itemID对用多个tmp.json"));
    }

}
