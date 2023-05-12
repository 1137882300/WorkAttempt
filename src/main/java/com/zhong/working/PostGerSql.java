package com.zhong.working;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import com.zhong.Utils.FileUtils;
import com.zhong.ding.excel.Entity;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author: juzi
 * @date: 2023/5/11
 * @desc:
 */
public class PostGerSql {


    public static void main1(String[] args) {
        String str = "<p style=\"text-align: center;\" data-mce-style=\"text-align: center;\"><img src=\"https://hztour-img.youxiake.com/cms/post/202304/27/113723/25Gs1682566701711.jpg\" data-mce-src=\"https://hztour-img.youxiake.com/cms/post/202304/27/113723/25Gs1682566701711.jpg\"></p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\"><br></p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\">期待已久的五一假期</p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\">终于越来越近了</p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\">带上这份萧山五一游玩全攻略</p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\">选择目的地出发吧！<br></p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\">这个假期，畅玩景区乐园</p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\">&nbsp;<br><span style=\"font-size: 16px;\" data-mce-style=\"font-size: 16px;\"><strong><em>湘湖跨湖桥景区</em></strong></span></p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\"><br></p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\"><img src=\"https://hztour-img.youxiake.com/cms/post/202304/27/113723/25Gs1682566739867.jpg\" data-mce-src=\"https://hztour-img.youxiake.com/cms/post/202304/27/113723/25Gs1682566739867.jpg\"></p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\"><br></p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\">露营、踏青、喝茶、打卡</p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\">一起来看看</p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\">湘湖“五·一”绘梦艺术系列</p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\">都有哪些精彩活动吧~</p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\"><br></p><p style=\"text-align: center;\" ";
        String regex = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";  // 匹配数字的正则表达式
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {  // 循环查找匹配项
            String match = matcher.group();  // 获取匹配到的子字符串
            System.out.println(match);  // 输出匹配到的数字子字符串
        }
    }

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

        //匹配请求的URL
        String regex = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(regex);

        List<String> list = Lists.newArrayList();
        entityList.forEach(x -> {
            Matcher matcher = pattern.matcher(x.getColumn2());
            list.add(String.format("\n" + sql1, x.getColumn1(), x.getColumn1()) + " ;");
            while (matcher.find()) {
                String group = matcher.group();
                list.add("\n" + String.format(sql2, x.getColumn1(), x.getColumn1(), group) + " ;");
            }
        });
        System.out.println(list.size());
        List<String> collect = list.stream().distinct().collect(Collectors.toList());
        System.out.println(collect.size());

        //写文件
        FileUtil.writeUtf8Lines(collect, new File("结果2.sql"));
    }
}
