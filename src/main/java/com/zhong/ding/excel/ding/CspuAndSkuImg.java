package com.zhong.ding.excel.ding;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhong.ding.excel.Entity;
import com.zhong.ding.excel.ExcelUtil;
import com.zhong.entity.Container;
import com.zhong.entity.OldNew;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @date 2022/10/25 20:45
 */
public class CspuAndSkuImg {

    @SneakyThrows
    public static void main2(String[] args) {

        String source = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正cspu和sku图片\\源文件\\主图优化.xlsx";
        String cspuImg = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正cspu和sku图片\\查询数据\\cspu-img.xls";
        String skuImg = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正cspu和sku图片\\查询数据\\cspu-sku_copy-img.xls";

        List<Entity> sourceList = readExcel(source);    //7427
        List<Entity> cspuImgList = readExcel(cspuImg);  //7427
        List<Entity> skuImgList = readExcel(skuImg);    //9098

        System.out.println(
                sourceList.size() + "\n" +
                        cspuImgList.size() + "\n" +
                        skuImgList.size() + "\n"
        );

        List<String> cspuImgSql = Lists.newArrayList();
        List<String> skuImgSql = Lists.newArrayList();
        List<String> skuImgMasterSql = Lists.newArrayList();

        List<Long> cspuIdList = Lists.newArrayList();
        List<Long> skuIdList = Lists.newArrayList();

        //k:cspuId, value:sku
//        Map<String, List<String>> cspuSkuMap = skuImgList.stream().collect(Collectors.groupingBy(Entity::getColumn1, Collectors.mapping(Entity::getColumn2, Collectors.toList())));

        Map<String, List<Entity>> cspuSkuImgMap = skuImgList.stream().collect(Collectors.groupingBy(Entity::getColumn1));

        List<Container> containerList = Lists.newArrayList();
        sourceList.forEach(x -> {
            Container container = new Container();
            container.setId(x.getColumn2());
            OldNew oldNew = new OldNew();
            oldNew.setOldStr(x.getColumn3());
            oldNew.setNewStr(x.getColumn4());
            container.setOldNew(oldNew);
            containerList.add(container);
        });

        //k:cspuId, value: img
        Map<String, OldNew> sourceMap = containerList.stream().collect(Collectors.toMap(Container::getId, Container::getOldNew));

        cspuImgList.forEach(x -> {
            String cspuId = x.getColumn1();
            String img = x.getColumn2();
            if (sourceMap.containsKey(cspuId)) {
                OldNew oldNew = sourceMap.get(cspuId);
                if (StringUtils.equals(img, oldNew.getOldStr())) {
                    if (!StringUtils.equals(oldNew.getOldStr(), oldNew.getNewStr())) {
                        cspuImgSql.add(
                                "update t_cspu set version=version+1, system_update_time=1666703573000, image_url='" + oldNew.getNewStr() + "' where  cspu_id= " + cspuId + ";"
                        );
                        cspuIdList.add(Long.valueOf(cspuId));
                    }
                    if (cspuSkuImgMap.containsKey(cspuId)) {
                        List<Entity> list = cspuSkuImgMap.get(cspuId);
                        list.forEach(o -> {
                            String skuId = o.getColumn2();
                            String imag = o.getColumn3();
                            if (!StringUtils.equals(oldNew.getNewStr(), imag)) {
                                skuImgSql.add(
                                        "update t_sku_copy set version=version+1, system_update_time=1666703573000, image_url='" + oldNew.getNewStr() + "' where sku_id= " + skuId + ";"
                                );
                                skuImgMasterSql.add(
                                        "update t_sku_master set version=version+1, system_update_time=1666703573000, image_url='" + oldNew.getNewStr() + "' where sku_id= " + skuId + ";"
                                );
                                skuIdList.add(Long.valueOf(skuId));
                            }
                        });
                    }

//                    if (cspuSkuMap.containsKey(cspuId)) {
//                        List<String> skuList = cspuSkuMap.get(cspuId);
//                        skuList.forEach(skuId -> {
//                            skuImgSql.add(
//                                    "update t_sku_copy set version=version+1, system_update_time=1666703573000, image_url='" + oldNew.getNewStr() + "' where sku_id= " + skuId + ";"
//                            );
//                            skuImgMasterSql.add(
//                                    "update t_sku_master set version=version+1, system_update_time=1666703573000, image_url='" + oldNew.getNewStr() + "' where sku_id= " + skuId + ";"
//                            );
//                        });
//                    }
                }
            }
        });

        FileUtil.writeUtf8Lines(cspuImgSql, new File("cspuImgSql.sql"));
        FileUtil.writeUtf8Lines(skuImgSql, new File("skuImgSql.sql"));
        FileUtil.writeUtf8Lines(skuImgMasterSql, new File("skuImgMasterSql.sql"));

        String cspuStr = JSON.toJSONString(cspuIdList);
        String skuStr = JSON.toJSONString(skuIdList);
        System.out.println(cspuStr);
        System.out.println(skuStr);
        System.out.println("end");
    }

    /**
     * 处理img is null的
     *
     * @param args
     */
    @SneakyThrows
    public static void main(String[] args) {

        String source = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正cspu和sku图片\\源文件\\主图优化.xlsx";
        String cspuImg = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正cspu和sku图片\\查询数据\\cspu-imgIsNull.xls";
        String skuImg = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正cspu和sku图片\\查询数据\\cspu-sku-imgIsNull.xls";

        List<Entity> sourceList = readExcel(source);    //7427
        List<Entity> cspuImgList = readExcel(cspuImg);  //459
        List<Entity> skuImgList = readExcel(skuImg);    //459

        System.out.println(
                sourceList.size() + "\n" +
                        cspuImgList.size() + "\n" +
                        skuImgList.size() + "\n"
        );

        List<String> cspuImgSql = Lists.newArrayList();
        List<String> skuImgSql = Lists.newArrayList();
        List<String> skuImgMasterSql = Lists.newArrayList();

        //k:cspuId, v:sku
        Map<String, List<String>> cspuSkuMap = skuImgList.stream().collect(Collectors.groupingBy(Entity::getColumn1, Collectors.mapping(Entity::getColumn2, Collectors.toList())));


        List<Container> containerList = Lists.newArrayList();
        sourceList.forEach(x -> {
            Container container = new Container();
            container.setId(x.getColumn2());
            OldNew oldNew = new OldNew();
            oldNew.setOldStr(x.getColumn3());
            oldNew.setNewStr(x.getColumn4());
            container.setOldNew(oldNew);
            containerList.add(container);
        });

        //k:cspuId, value: img
        Map<String, OldNew> sourceMap = containerList.stream().collect(Collectors.toMap(Container::getId, Container::getOldNew));

        cspuImgList.forEach(x -> {
            String cspuId = x.getColumn1();
            if (sourceMap.containsKey(cspuId)) {
                OldNew oldNew = sourceMap.get(cspuId);
                String newStr = oldNew.getNewStr();
                cspuImgSql.add(
                        "update t_cspu set version=version+1, system_update_time=1666703573000, image_url='" + newStr + "' where  cspu_id= " + cspuId + ";"
                );
                if (cspuSkuMap.containsKey(cspuId)) {
                    List<String> skuList = cspuSkuMap.get(cspuId);
                    skuList.forEach(skuId -> {
                        skuImgSql.add(
                                "update t_sku_copy set version=version+1, system_update_time=1666703573000, image_url='" + oldNew.getNewStr() + "' where sku_id= " + skuId + ";"
                        );
                        skuImgMasterSql.add(
                                "update t_sku_master set version=version+1, system_update_time=1666703573000, image_url='" + oldNew.getNewStr() + "' where sku_id= " + skuId + ";"
                        );
                    });
                } else {
                    System.out.println(cspuId);
                    //cspuId: 20000071933, 20000071942, 20000073961, 20000096003, 20000098513, 20000098532, 20000098538, 20000098581, 20000098624, 20000098627, 20000101467, 20000107507, 20000123379
                }
            }
        });

        FileUtil.writeUtf8Lines(cspuImgSql, new File("cspuImgSqlIsNull.sql"));
        FileUtil.writeUtf8Lines(skuImgSql, new File("skuImgSqlIsNull.sql"));
        FileUtil.writeUtf8Lines(skuImgMasterSql, new File("skuImgMasterSqlIsNull.sql"));
        System.out.println("end");
    }

    public static List<Entity> readExcel(String path) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            return ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class, 1);
        }
    }

}
