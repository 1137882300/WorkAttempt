package com.zhong.ding.excel.ding;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import com.zhong.utils.FileUtils;
import com.zhong.ding.excel.Entity;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

/**
 * @date 2022/10/26 19:47
 */
public class SkuCodeAndShopUnique {


    public static void main(String[] args) {

//        String sku1 = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正skucode\\源文件\\sku-1.xls";
//        String sku2 = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正skucode\\源文件\\sku-2.xls";
        String sku3 = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正skucode\\源文件\\sku-3.xls";

//        List<Entity> sku1List = FileUtils.readExcel(sku1);
//        List<Entity> sku2List = FileUtils.readExcel(sku2);
        List<Entity> sku3List = FileUtils.readExcel(sku3);

        List<Entity> skuList = Lists.newArrayList();
//        skuList.addAll(sku1List);
//        skuList.addAll(sku2List);
        skuList.addAll(sku3List);

        List<String> skuCopySql = Lists.newArrayList();
        List<String> skuMasterSql = Lists.newArrayList();
        List<String> uniqueSql = Lists.newArrayList();

        skuList.forEach(x -> {
            String skuId = x.getColumn1();
            String skuCode = x.getColumn2();
            String orderType = x.getColumn3();//2:跨境保税代发-K, 4:一般贸易代发 -W, 1:跨境直邮代发-Z
            String shopId = x.getColumn4();

            String skuCodeNew = "";
            if (orderType.equals("2")) {
                skuCodeNew = skuCode + "-K";
            } else if (orderType.equals("4")) {
                skuCodeNew = skuCode + "-W";
            } else if (orderType.equals("1")) {
                skuCodeNew = skuCode + "-Z";
            }
            if (StringUtils.isBlank(skuCodeNew)) {
                System.out.println(skuId);
                return;
            }
            //1666789826000，1666838178000
            skuCopySql.add(
                    "update t_sku_copy set version=version+1,system_update_time=1666838178000,sku_code= '" + skuCodeNew + "' where sku_id=" + skuId + " ;"
            );
            skuMasterSql.add(
                    "update t_sku_master set version=version+1,system_update_time=1666838178000,sku_code= '" + skuCodeNew + "' where sku_id=" + skuId + " ;"
            );
            uniqueSql.add(
                    "update t_shop_unique set creator_id=-6, biz_value='" + skuCodeNew + "'  where biz_id= " + skuId + " and shop_id= " + shopId + " and type=1 ;"
            );
        });

        FileUtil.writeUtf8Lines(skuCopySql, new File("skuCopySql.sql"));
        FileUtil.writeUtf8Lines(skuMasterSql, new File("skuMasterSql.sql"));
        FileUtil.writeUtf8Lines(uniqueSql, new File("uniqueSql.sql"));
        System.out.println("end");
    }


}
