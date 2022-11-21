package com.zhong.ding.excel.ding;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhong.Utils.FileUtils;
import com.zhong.ding.excel.Entity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @date 2022/11/21 17:56
 */
public class BrandImg {

    public static void main(String[] args) {
        String path = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正品牌图片\\源文件\\货舱100个品牌.xlsx";
        List<Entity> list = FileUtils.readExcel(path);

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        System.out.println("list.size = " + list.size());

        ArrayList<String> sql = Lists.newArrayList();
        int k = 1;
        for (Entity entity : list) {
            String brandId = entity.getColumn1();
            String handle = entity.getColumn5();
            String newImg = entity.getColumn6();
            if (StringUtils.equals(handle, "否")) {
                System.out.println(handle + k++);
                continue;
            }

            sql.add(
                    "update t_brand set version=version+1, update_time=1669025304000, logo_url= 'https:" + newImg + "' " +
                            "where brand_id = " + brandId + " ;"
            );

        }

        FileUtil.writeUtf8Lines(sql, new File("brand-img.sql"));

    }

}
