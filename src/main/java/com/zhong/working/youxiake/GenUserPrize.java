package com.zhong.working.youxiake;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.google.common.collect.Lists;
import com.zhong.ding.excel.Entity;
import com.zhong.ding.excel.ExcelUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author: juzi
 * @date: 2024/3/18
 * @desc:
 */
public class GenUserPrize {

    public static void main1(String[] args) {
        try (FileInputStream inputStream = new FileInputStream("F:\\工作记录\\20240318中奖结果.xls")) {
            List<Entity> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class, 1);

            List<String> sqlList = Lists.newArrayList();

            for (Entity entity : list) {

                String prize = entity.getColumn1();
                String name = entity.getColumn2().replace("　", "").trim();
                String phone = entity.getColumn3();

                String prizeStr = "";
                if ("一等奖".equals(prize)) {
                    prizeStr = "1";
                } else if ("二等奖".equals(prize)) {
                    prizeStr = "2";
                } else if ("三等奖".equals(prize)) {
                    prizeStr = "3";
                } else if ("四等奖".equals(prize)) {
                    prizeStr = "4";
                }


                String phoneEn = "";
                if (StringUtils.isNotBlank(phone)) {
                    phoneEn = sm4Encrypt(phone.replace("　", "").trim(), null);
                } else {
                    phoneEn = "";
                }

                sqlList.add(
                        String.format("INSERT INTO \"public\".\"activity_enroll\"( \"user_id\",  \"real_name\", \"phone\",\"type\",  \"content\")" +
                                "VALUES ( 0,  '%s', '%s',  '4', '%s');", name, phoneEn, prizeStr)
                );
            }

            FileUtil.writeUtf8Lines(sqlList, new File("prize.sql"));

        } catch (IOException ignored) {

        }


    }

    public static void main(String[] args) {
//        String s = sm4Encrypt("15397073571", null);
//        System.out.println(s);
        //5f22e41ada32ce10068a82a635614f0d

        String s1 = sm4Decrypt("b5e483bb4afc09af9cd3f2700fb62c35", null);
        System.out.println(s1);

    }

    public static String sm4Encrypt(String content, String secretKey) {
//        635a6df163f598f450192fc1e5ac730e
        byte[] key = HexUtil.decodeHex("635a6df163f598f450192fc1e5ac730e");
        SymmetricCrypto sm4 = SmUtil.sm4(key);
        return sm4.encryptHex(content);
    }

    public static String sm4Decrypt(String content, String secretKey) {
        byte[] key = HexUtil.decodeHex("635a6df163f598f450192fc1e5ac730e");
        SymmetricCrypto sm4 = SmUtil.sm4(key);
        return sm4.decryptStr(content, CharsetUtil.CHARSET_UTF_8);
    }

}
