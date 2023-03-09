package com.zhong.ding.excel.ding;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhong.Utils.FileUtils;
import com.zhong.ding.excel.Entity;
import com.zhong.ding.excel.ExcelUtil;
import lombok.SneakyThrows;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @date 2022/9/9 10:46
 */
public class CBGsaleProperty {
    /**
     * 订正销售属性
     *
     * @param args
     */
    @SneakyThrows
    public static void main(String[] args) {

        String spu = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正销售属性\\t_spu_base_language.xls";
        String cspu = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正销售属性\\t_cspu_language.xls";
        String itemCopy = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正销售属性\\t_item_base_info_copy_language.xls";
        String itemMaster = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正销售属性\\t_item_base_master_language.xls";
        String skuCopy = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正销售属性\\t_sku_copy_language.xls";
        String skuMaster = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正销售属性\\t_sku_master_language.xls";

        List<Entity> spuList = readProperty(spu);
        List<Entity> cspuList = readProperty(cspu);
        List<Entity> itemCopyList = readProperty(itemCopy);
        List<Entity> itemMasterList = readProperty(itemMaster);
        List<Entity> skuCopyList = readProperty(skuCopy);
        List<Entity> skuMasterList = readProperty(skuMaster);

        System.out.println(
                spuList.size() + "\n" +
                        cspuList.size() + "\n" +
                        itemCopyList.size() + "\n" +
                        itemMasterList.size() + "\n" +
                        skuCopyList.size() + "\n" +
                        skuMasterList.size() + "\n"
        );

        List<String> sql = Lists.newArrayList();


        spuList.forEach(x -> sql.add(
                "update t_spu_base_language set " +
                        "version = version+1," +
                        "system_update_time = 1662692136000," +
                        "sale_property_extend = '" + x.getColumn2().replace("relationId", "custom") + "' " +
                        "where item_lan_id = " + x.getColumn1() + ";"));

        cspuList.forEach(x -> sql.add(
                "update t_cspu_language set " +
                        "version = version+1," +
                        "system_update_time = 1662692136000," +
                        "sale_property_extend = '" + x.getColumn2().replace("relationId", "custom") + "' " +
                        "where sku_lan_id = " + x.getColumn1() + ";"));

        itemCopyList.forEach(x -> sql.add(
                "update t_item_base_info_copy_language set " +
                        "version = version+1," +
                        "system_update_time = 1662692136000," +
                        "sale_property_extend = '" + x.getColumn2().replace("relationId", "custom") + "' " +
                        "where item_lan_id = " + x.getColumn1() + ";"));


        itemMasterList.forEach(x -> sql.add(
                "update t_item_base_master_language set " +
                        "version = version+1," +
                        "system_update_time = 1662692136000," +
                        "sale_property_extend = '" + x.getColumn2().replace("relationId", "custom") + "' " +
                        "where item_lan_id = " + x.getColumn1() + ";"));

        skuCopyList.forEach(x -> sql.add(
                "update t_sku_copy_language set " +
                        "version = version+1," +
                        "system_update_time = 1662692136000," +
                        "sale_property_extend = '" + x.getColumn2().replace("relationId", "custom") + "' " +
                        "where sku_lan_id = " + x.getColumn1() + ";"));

        skuMasterList.forEach(x -> sql.add(
                "update t_sku_master_language set " +
                        "version = version+1," +
                        "system_update_time = 1662692136000," +
                        "sale_property_extend = '" + x.getColumn2().replace("relationId", "custom") + "' " +
                        "where sku_lan_id = " + x.getColumn1() + ";"));

        System.out.println(JSON.toJSONString(sql));

        FileUtil.writeUtf8Lines(sql, new File("ding.sql"));

    }


    public static List<Entity> readProperty(String path) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            return ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class, 1);
        }
    }

}
