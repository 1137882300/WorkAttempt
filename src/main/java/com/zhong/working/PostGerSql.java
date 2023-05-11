package com.zhong.working;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import com.zhong.Utils.FileUtils;
import com.zhong.ding.excel.Entity;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

/**
 * @author: juzi
 * @date: 2023/5/11
 * @desc:
 */
public class PostGerSql {


    public static void main(String[] args) {


//        String sql1 = "INSERT INTO `yxkbbs`.`question_answers` (`id`,`question_id`, `authorid`, `message`, `summary`, `created_at`, `updated_at`) VALUES (%s, 100000000, 0, '', '', 628358400, 628358400)";
//        String sql2 = "INSERT INTO `yxkbbs`.`question_attachs` (`question_id`, `url`, `created_at`, `updated_at`) VALUES (%s, '%s', 628358400, 628358400)";

        String sql1 = "INSERT INTO `youxiake_cms`.`cms_risk_audit_task` (`id`,`addtime`, `business_flag`, `business_id`,`business_status`) VALUES (%s,'1990-01-01 00:00:00', 'discover_add', %s,0)";
        String sql2 = "INSERT INTO `youxiake_cms`.`cms_risk_audit_task_detail` (`task_id`, `business_flag`, `business_id`, `content`, `content_type`, `business_status`) VALUES (%s, 'discover_add', %s, '%s', 2,0)";


        String path = "F:\\工作记录\\content2.xlsx ";
//        File file = new File(path);
//        List<String> list = FileUtil.readUtf8Lines(file);

        String s = StringUtils.substringBetween(path, "c", " ");
        System.out.println(s);

        List<Entity> entityList = FileUtils.readExcel(path);
        System.out.println(entityList.size());
        entityList.removeIf(x -> StringUtils.isBlank(x.getColumn2()));
        System.out.println(entityList.size());


        List<String> list = Lists.newArrayList();
        entityList.forEach(x -> {
            String between = StringUtils.substringBetween(x.getColumn2(), "<img src=\"", " ");
            if (StringUtils.isBlank(between)) {
                return;
            }
            String substring = between.substring(0, between.length() - 1);
            list.add(
                    String.format(sql1, x.getColumn1(), x.getColumn1()) + " ;\n" + String.format(sql2, x.getColumn1(), x.getColumn1(), substring) + " ;"
            );
        });

        //写文件
        FileUtil.writeUtf8Lines(list, new File("结果.sql"));

    }
}
