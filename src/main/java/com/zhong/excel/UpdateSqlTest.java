package com.zhong.excel;

import com.google.common.collect.Maps;
import com.zhong.Utils.FileUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @date 2022/6/29 14:16
 */
public class UpdateSqlTest {

    public static void main(String[] args) throws IOException {

        String sourcePath = "C:\\Users\\EDZ\\Documents\\订数据\\订正的商品名称数据6-29.xlsx";
        String spuPath = "C:\\Users\\EDZ\\Documents\\订数据\\item-copy-spu.xls";
        String itemCopyPath = "C:\\Users\\EDZ\\Documents\\订数据\\item-copy.xls";
        String itemMasterPath = "C:\\Users\\EDZ\\Documents\\订数据\\item-master.xls";

        Map<String, String> spuCodeToTitleMap = readSource(sourcePath);                     //911 条
        Map<String, String> copyItemIdToSpuCodeMap = readItemCopyOrMaster(itemCopyPath);    //1118条
        Map<String, String> masterItemIdToSpuCodeMap = readItemCopyOrMaster(itemMasterPath);//207 条
        Map<String, String> spuIdToItemIdMap = readSpu(spuPath);                            //909 条

        System.out.println(
                "copyItemIdToSpuCodeMap= " + copyItemIdToSpuCodeMap.size() + " keySize= " + copyItemIdToSpuCodeMap.keySet().size() + "\n" +
                        "masterItemIdToSpuCodeMap= " + masterItemIdToSpuCodeMap.size() + " keySize= " + masterItemIdToSpuCodeMap.keySet().size() + "\n" +
                        "spuIdToItemIdMap= " + spuIdToItemIdMap.size() + " keySize= " + spuIdToItemIdMap.keySet().size() + "\n"
        );

        //item-copy
        StringBuilder sqlItemCopy = convertToSqlItem(copyItemIdToSpuCodeMap, spuCodeToTitleMap, false);

        //item-master
        StringBuilder sqlItemMaster = convertToSqlItem(masterItemIdToSpuCodeMap, spuCodeToTitleMap, true);

        //spu,spuCode-itemId-spuId
        StringBuilder sqlSpu = convertToSqlSpu(spuIdToItemIdMap, copyItemIdToSpuCodeMap, spuCodeToTitleMap);

        //写入文件
        FileUtils.writeFile(sqlItemCopy, "sqlItemCopy.sql");
        FileUtils.writeFile(sqlItemMaster, "sqlItemMaster.sql");
        FileUtils.writeFile(sqlSpu, "sqlSpu.sql");
    }


    private static Map<String, String> readSource(String sourcePath) throws IOException {
        Map<String, String> spuCodeToTitleMap = Maps.newHashMap();
        try (FileInputStream inputStream = new FileInputStream(sourcePath)) {
            List<Entity> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class, 1);
            list.forEach(x -> {
                spuCodeToTitleMap.put(x.getColumn1(), x.getColumn2());
            });
        }
        return spuCodeToTitleMap;
    }

    private static Map<String, String> readItemCopyOrMaster(String itemPath) throws IOException {
        Map<String, String> itemIdToSpuCodeMap = Maps.newHashMap();
        try (FileInputStream inputStream = new FileInputStream(itemPath)) {
            List<Entity> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class, 1);
            list.forEach(x -> {
                itemIdToSpuCodeMap.put(x.getColumn2(), x.getColumn1());//这里注意是 反着的
            });
        }
        return itemIdToSpuCodeMap;
    }

    private static Map<String, String> readSpu(String spuPath) throws IOException {
        Map<String, String> spuIdToItemIdMap = Maps.newHashMap();
        try (FileInputStream inputStream = new FileInputStream(spuPath)) {
            List<Entity> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class, 1);
            list.forEach(x -> {
                spuIdToItemIdMap.put(x.getColumn1(), x.getColumn2());
            });
        }
        return spuIdToItemIdMap;
    }


    private static StringBuilder convertToSqlItem(Map<String, String> itemMap, Map<String, String> spuCodeToTitleMap, boolean copyOrMaster) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : itemMap.entrySet()) {
            String itemId = entry.getKey();
            String spuCode = entry.getValue();
            if (spuCodeToTitleMap.containsKey(spuCode)) {
                String title = spuCodeToTitleMap.get(spuCode);
                for (int i = 0; i < 2; i++) {
                    if (copyOrMaster) {
                        sb.append(" update t_item_base_master_language set title = ");
                    } else {
                        sb.append(" update t_item_base_info_copy_language set title = ");
                    }
                    sb.append("\"").append(title).append("\"").append(",");
                    sb.append(" version = version+1,system_update_time = 1656486458000");
                    sb.append(" where item_id = ").append(itemId);
                    if (i == 1) {
                        sb.append(" and preferred_language = 'zh_CN' ;").append("\n");
                    } else {
                        sb.append(" and preferred_language = 'en_US' ;").append("\n");
                    }
                }
            }
        }
        return sb;
    }


    private static StringBuilder convertToSqlSpu(Map<String, String> spuIdToItemIdMap, Map<String, String> copyItemIdToSpuCodeMap, Map<String, String> spuCodeToTitleMap) {
        int k = 0;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : spuIdToItemIdMap.entrySet()) {
            String spuId = entry.getKey();
            String itemId = entry.getValue();
            if (copyItemIdToSpuCodeMap.containsKey(itemId)) {
                String spuCode = copyItemIdToSpuCodeMap.get(itemId);
                if (spuCodeToTitleMap.containsKey(spuCode)) {
                    String title = spuCodeToTitleMap.get(spuCode);
                    for (int i = 0; i < 2; i++) {
                        sb.append(" update t_spu_base_language set title = ");
                        sb.append("\"").append(title).append("\"").append(",");
                        sb.append(" version = version+1,system_update_time = 1656486458000");
                        sb.append(" where item_id = ").append(spuId);
                        if (i == 1) {
                            sb.append(" and preferred_language = 'zh_CN' ;").append("\n");
                        } else {
                            sb.append(" and preferred_language = 'en_US' ;").append("\n");
                        }
                        k++;
                    }

                }
            }
        }
        System.out.println("k == " + k);
        return sb;
    }
}
