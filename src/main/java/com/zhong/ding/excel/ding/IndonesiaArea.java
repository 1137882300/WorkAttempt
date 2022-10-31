package com.zhong.ding.excel.ding;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import com.zhong.Utils.FileUtils;
import com.zhong.ding.excel.Entity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @date 2022/10/31 16:42
 */
public class IndonesiaArea {


    public static void main(String[] args) {

//        id,	en_name,	zh_name,	id_name,	1处理印尼语/其他处理中文
        String path = "C:\\Users\\EDZ\\Documents\\0订数据\\订正ＣＢＧ\\订正地区\\源数据\\最新-产品.xls";
        List<Entity> list = FileUtils.readExcel(path);
        System.out.println(list.size());
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        AtomicLong lanId = new AtomicLong(2001145001L);//递增
//        2001149261
        List<String> sql = Lists.newArrayList();
        list.forEach(x -> {
            String areaId = x.getColumn1();
            String zhName = x.getColumn3();
            String idName = x.getColumn4();
            String flag = x.getColumn5();
            sql.add(
                    "insert into `t_area_info_language` (`area_info_id`, `area_name`, `create_time`, `creator_id`,   `id`, `is_deleted`, `modified_id`, `preferred_language`, `update_time`, `version`) " +
                            "values ('" + areaId + "', \"" + zhName + "\", 1667206768000, -1, " + lanId.getAndIncrement() + ", 0, '-1', 'zh_CN', 1667206768000, '1');"
            );

            if (StringUtils.isNotBlank(flag) && flag.equals("1.0")) {
                System.out.println(flag);
                sql.add(
                        "insert into `t_area_info_language` (`area_info_id`, `area_name`, `create_time`, `creator_id`,   `id`, `is_deleted`, `modified_id`, `preferred_language`, `update_time`, `version`) " +
                                "values ('" + areaId + "', \"" + idName + "\", 1667206768000, -1, " + lanId.getAndIncrement() + ", 0, '-1', 'in_ID', 1667206768000, '1');"
                );
            }
        });

        FileUtil.writeUtf8Lines(sql, new File("IndonesiaArea.sql"));
    }

}
