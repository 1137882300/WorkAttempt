package com.zhong.ding.excel.ding;

import cn.hutool.core.collection.ArrayIter;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.alibaba.nacos.shaded.com.google.gson.annotations.SerializedName;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhong.Utils.FileUtils;
import com.zhong.ding.excel.Entity;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @date 2022/8/16 11:23
 */
public class TTRelationSql {

    /**
     * select m.tmp, n.item_id as tt_itemId ,n.features from t_item_base_info_copy as n
     * inner join
     * (
     * select  item_id, u.tmp from t_sku_copy as y
     * inner join
     * (
     * select `slave` as skuId,l.tmp FROM t_global_relation as t
     * inner join
     * (
     * select cspu_id, h.tmp from t_cspu as f
     * inner join
     * (
     * select `master` as spuId, c.tmp from t_global_relation as b
     * inner join
     * (
     * select a.item_id as tmp from t_item_base_info_copy as a
     * where a.is_deleted = 0
     * and status = 0
     * and a.features -> '$.auditStatus' = '0'
     * and a.features -> '$.bbmallGloItemRelation' is null
     * and a.item_id not in (
     * 3000003045, 3000002415
     * )
     * )as c
     * on b.`slave` = c.tmp
     * where type = 'SPU_ITEMTMP'
     * and is_deleted = 0
     * )as h
     * on f.item_id = h.spuId
     * where f.is_deleted = 0
     * )as l
     * on t.`master` = l.cspu_id
     * where t.type = 'CSPU_SKU'
     * and t.is_deleted = 0
     * )as u
     * on y.sku_id = u.skuId
     * where y.is_deleted = 0
     * and y.item_id not in (u.tmp)
     * )as m
     * on n.item_id = m.item_id
     * where n.features -> '$.bbmallGloItemTikTokProductId' is not null
     * and n.is_deleted = 0
     * <p>
     * -- 替换 in 查询
     * select master from t_global_relation
     * where type = 'ITEMTMP_ITEM'
     * and is_deleted = 0
     * ;
     * -- 最后还需要排除掉item里是tmp的数据
     */
    //30000000359
    private static Long id = 30000170000L;

    @SneakyThrows
    public static void main(String[] args) {
//     ITEMTMP_ITEM 一对多
        String tmp2item = "C:\\Users\\EDZ\\Documents\\0订数据\\TT\\tmp对item（V2）.xls";

        List<Entity> list = FileUtils.readExcelByPath(tmp2item, 1, 1);
        //去重
        List<Entity> distinct = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(Entity::getColumn1).thenComparing(Entity::getColumn2))
        ), ArrayList::new));

        System.out.println("tmp和item联合去重：" + distinct.size());

        List<String> tmpJudge = distinct.stream().map(Entity::getColumn1).collect(Collectors.toList());
        List<String> itemJudge = distinct.stream().map(Entity::getColumn2).collect(Collectors.toList());
        ArrayList<String> strings = new ArrayList<>(CollectionUtils.intersection(tmpJudge, itemJudge));
        System.out.println("tmp 和 item 是否有交集：" + strings);

        List<String> sql = Lists.newArrayList();
        List<Entity> skip = Lists.newArrayList();
        Map<String, String> skipMap = Maps.newHashMap();
        Map<String, List<Entity>> group = distinct.stream().collect(Collectors.groupingBy(Entity::getColumn2));
        for (Map.Entry<String, List<Entity>> entry : group.entrySet()) {
            String slave = entry.getKey();
            List<Entity> value = entry.getValue();
            if (value.size() > 1) {
                skip.addAll(value);
                StringBuffer sb = new StringBuffer();
                value.forEach(x -> {
                    sb.append(x.getColumn1()).append(",");
                });
                skipMap.put(slave, sb.toString());
                continue;
            }

            String master = value.get(0).getColumn1();

            sql.add(
                    "insert into `t_global_relation` " +
                            "(`create_time`, `creator_id`, `features`, `id`, `is_deleted`, `master`, `modified_id`, `platform`, `slave`, `status`, `system_update_time`, `type`, `update_time`, `version`) " +
                            "values " +
                            "('1660637968000', '-1', NULL, '" + id++ + "', 0, '" + master + "', '-1', 'bbmall_GLO', '" + slave + "', 0, '1660637968000', 'ITEMTMP_ITEM', '1660637968000', '1');"
            );
        }

        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
        Set<String> includes = simplePropertyPreFilter.getIncludes();
        includes.add("column1");
        includes.add("column2");
//        System.out.println(JSON.toJSONString(skip,simplePropertyPreFilter));


//        FileUtil.writeUtf8Lines(sql,new File("relation.sql"));
//        FileUtil.writeUtf8String(JSON.toJSONString(skip,simplePropertyPreFilter),new File("没有关联关系且itemID重复.json"));

//        System.out.println(JSON.toJSONString(skipMap));
//        FileUtil.writeUtf8String(JSON.toJSONString(skipMap),new File("没有关联关系且itemID对用多个tmp.json"));

    }

}
