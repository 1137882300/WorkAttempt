package com.zhong.ding.excel.ding;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import com.zhong.utils.FileUtils;
import com.zhong.ding.excel.Entity;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @date 2022/11/3 16:43
 */
public class CategoryImg {

    public static void main(String[] args) {

        String path = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正类目图片\\源文件\\源文件2.xlsx";
        List<Entity> list = FileUtils.readExcel(path);

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        ArrayList<String> sql = Lists.newArrayList();
        for (Entity entity : list) {
            String categoryId = entity.getColumn1();
            String img = entity.getColumn3();

            sql.add(
                    "update t_category_backend set version=version+1, update_time=1667465151000, icon_url= '" + img + "' where category_backend_id = " + categoryId + " ;"
            );

        }

        FileUtil.writeUtf8Lines(sql, new File("category.sql"));

    }


}
