package com.zhong.ding.excel.ding;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import com.zhong.Utils.FileUtils;
import com.zhong.ding.excel.Entity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @date 2022/10/26 20:27
 */
public class SpuImgAndItemImg {

    public static void main(String[] args) {


        String path = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正spu和item图片\\源文件\\主图优化全量版本.xlsx";

        List<Entity> entityList = FileUtils.readExcel(path);
        if (CollectionUtils.isEmpty(entityList)) {
            System.out.println("is null");
            return;
        }
        System.out.println(
                "entityList.size= " + entityList.size()
        );
        //去重
        List<Entity> duplicate = entityList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(Entity::getColumn3))
        ), ArrayList::new));

        System.out.println(
                "去重后 entityList.size= " + duplicate.size()
        );

        List<String> spuImgSql = Lists.newArrayList();
        List<String> itemCopyImgSql = Lists.newArrayList();
        List<String> itemMasterImgSql = Lists.newArrayList();

        duplicate.forEach(x -> {
            String oldImg = x.getColumn3();
            String newImg = x.getColumn4();
            if (StringUtils.equals(oldImg, newImg)) {
                return;
            }
            spuImgSql.add(
                    "update t_spu_image set version=version+1,update_time=1666788138000,image_url='" + newImg + "'  where sku_id=0 and image_url = '" + oldImg + "' and is_deleted=0;"
            );
            itemCopyImgSql.add(
                    "update t_item_image_copy set version=version+1,update_time=1666788138000,image_url= '" + newImg + "' where sku_id=0 and image_url = '" + oldImg + "' and is_deleted=0;"
            );
            itemMasterImgSql.add(
                    "update t_item_image_master set version=version+1,update_time=1666788138000,image_url= '" + newImg + "' where sku_id=0 and image_url = '" + oldImg + "' and is_deleted=0;"
            );
        });

        FileUtil.writeUtf8Lines(spuImgSql, new File("spuImgSql.sql"));
        FileUtil.writeUtf8Lines(itemCopyImgSql, new File("itemCopyImgSql.sql"));
        FileUtil.writeUtf8Lines(itemMasterImgSql, new File("itemMasterImgSql.sql"));
        System.out.println("end");

    }

}
