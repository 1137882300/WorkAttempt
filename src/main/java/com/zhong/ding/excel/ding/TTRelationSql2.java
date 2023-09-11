package com.zhong.ding.excel.ding;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import com.zhong.utils.FileUtils;
import com.zhong.ding.excel.Entity;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @date 2022/8/19 21:50
 */
public class TTRelationSql2 {


    public static Long id = 30000000329L;

    public static void main(String[] args) {

        String item_spu = "C:\\Users\\EDZ\\Documents\\0订数据\\待订正的脏数据\\cspu合并遗漏数据\\item-spu2.xls";
        String relation_sku_cspu = "C:\\Users\\EDZ\\Documents\\0订数据\\待订正的脏数据\\cspu合并遗漏数据\\relation-sku-cspu2.xls";
        String sku_cspu = "C:\\Users\\EDZ\\Documents\\0订数据\\待订正的脏数据\\cspu合并遗漏数据\\sku-cspu2.xls";
        String tmp_item = "C:\\Users\\EDZ\\Documents\\0订数据\\待订正的脏数据\\cspu合并遗漏数据\\tmp-item.xls";
        String excludeId = "C:\\Users\\EDZ\\Documents\\0订数据\\待订正的脏数据\\cspu合并遗漏数据\\tmp-item-排除的id.xls";
//        select g.item_id as spu_id, r.item_id
        List<Entity> item_spulist = FileUtils.readExcelByPath(item_spu, 1, 1);
//        select id,master as cspu_id, r.new_cspu_id , slave as sku_id
        List<Entity> relation_sku_cspulist = FileUtils.readExcelByPath(relation_sku_cspu, 1, 1);
//        select master as new_cspu_id, slave as old_cspu_id,t.sku_id
        List<Entity> sku_cspulist = FileUtils.readExcelByPath(sku_cspu, 1, 1);
//        select m.slave as tmp, n.item_id
        List<Entity> tmp_itemlist = FileUtils.readExcelByPath(tmp_item, 1, 1);
//        select master as tmp, slave as item_id
        List<Entity> excludeIdlist = FileUtils.readExcelByPath(excludeId, 1, 1);
        List<Entity> distinct = excludeIdlist.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(Entity::getColumn1).thenComparing(Entity::getColumn2))
        ), ArrayList::new));
        Map<String, String> excludeMap = distinct.stream().collect(Collectors.toMap(Entity::getColumn2, Entity::getColumn1));

        System.out.println(
                item_spulist.size() + " , " +
                        relation_sku_cspulist.size() + " , " +
                        sku_cspulist.size() + " , " +
                        tmp_itemlist.size() + " , " +
                        excludeIdlist.size()
        );

        ArrayList<String> sql = Lists.newArrayList();

        for (Entity entity : item_spulist) {
            String itemId = entity.getColumn2();
            String spuId = entity.getColumn1();
            sql.add(
                    "UPDATE `t_item_base_info_copy` SET " +
                            "`spu_id` = " + spuId + ", `system_update_time` = 1660919120000,`version` = version+1  WHERE `item_id` = " + itemId + ";"
            );
            sql.add(
                    "UPDATE `t_item_base_master` SET " +
                            "`spu_id` = " + spuId + ", `system_update_time` = 1660919120000,`version` = version+1  WHERE `item_id` = " + itemId + ";"
            );
        }

        for (Entity entity : relation_sku_cspulist) {
            String id = entity.getColumn1();
            String cspuId = entity.getColumn3();
            sql.add(
                    "UPDATE `t_global_relation` SET  `master` = '" + cspuId + "',  `system_update_time` = 1660919120000,`version` = version+1  WHERE `id` = " + id + ";"
            );
        }

        for (Entity entity : sku_cspulist) {
            String skuId = entity.getColumn3();
            String cspuId = entity.getColumn1();
            sql.add(
                    "UPDATE `t_sku_copy` SET `cspu_id` = " + cspuId + ",`version` = version+1  WHERE `sku_id` = " + skuId + ";"
            );
            sql.add(
                    "UPDATE `t_sku_master` SET `cspu_id` = " + cspuId + ",`version` = version+1  WHERE `sku_id` = " + skuId + ";"
            );
        }

        for (Entity entity : tmp_itemlist) {
            String tmpId = entity.getColumn1();
            String itemId = entity.getColumn2();
            if (excludeMap.containsKey(itemId)) {
                if (excludeMap.get(itemId).equals(tmpId)) {
                    continue;
                }
            }
            sql.add(
                    "INSERT INTO `t_global_relation` " +
                            "(`id`, `type`, `master`, `slave`, `status`, `platform`, `creator_id`, `modified_id`, `create_time`, `update_time`, `system_update_time`, `is_deleted`, `version`, `features`) " +
                            "VALUES " +
                            "(" + id-- + ", 'ITEMTMP_ITEM', '" + tmpId + "', '" + itemId + "', 0, 'bbmall_GLO', -1, -1, 1660919120000, 1660919120000, 1660919120000, 0, 1, null);"
            );
        }

        FileUtil.writeUtf8Lines(sql, new File("all.sql"));
    }
}
