package com.zhong.working.youxiake;

import cn.hutool.core.io.FileUtil;
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
 * @date: 2024/1/12
 * @desc:
 */
public class PostcardTest {


    public static void main(String[] args) {
        try (FileInputStream inputStream = new FileInputStream("F:\\工作记录\\电子名片信息表1.12.xlsx")) {
            List<Entity> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), Entity.class, 1);

            List<String> sqlList = Lists.newArrayList();

            for (Entity entity : list) {

                String real_name = entity.getColumn2();
                String foreign_name = entity.getColumn3();
                String phone = entity.getColumn4();
                if (StringUtils.isNotBlank(phone)) {
                    phone = sm4Encrypt(phone, null);
                }
                String telephone = entity.getColumn5();
                if (StringUtils.isNotBlank(telephone)) {
                    telephone = sm4Encrypt(telephone, null);
                }
                String email = "";
                String job_unit = entity.getColumn6();
                String job_position = entity.getColumn7();
                String address = entity.getColumn8();

                sqlList.add(
                        String.format("INSERT INTO \"postcard\"(\"real_name\", \"phone\", \"telephone\", \"email\", \"job_unit\", \"job_position\", \"status\", \"foreign_name\", \"address\")" +
                                        " VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '1', '%s', '%s');",
                                real_name, phone, telephone, email, job_unit, job_position, foreign_name, address)
                );
            }

            FileUtil.writeUtf8Lines(sqlList, new File("postcard.sql"));

        } catch (IOException ignored) {

        }


    }


    public static String sm4Encrypt(String content, String secretKey) {
//        635a6df163f598f450192fc1e5ac730e
        byte[] key = HexUtil.decodeHex("635a6df163f598f450192fc1e5ac730e");
        SymmetricCrypto sm4 = SmUtil.sm4(key);
        return sm4.encryptHex(content);
    }


}
