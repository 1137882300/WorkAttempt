package com.zhong.decryption.stateSecrets;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * @author: juzi
 * @date: 2023/5/17
 * @desc: SM4：无线局域网标准的分组数据算法。对称加密，密钥长度和分组长度均为128位。
 * 由于SM1、SM4加解密的分组大小为128bit，故对消息进行加解密时，若消息长度过长，需要进行分组，要消息长度不足，则要进行填充。
 */
public class Sm4 {


    public static void main(String[] args) {
        String text = "我的信息";
        SymmetricCrypto sm4 = SmUtil.sm4();

        String encryptHex = sm4.encryptHex(text);
        System.out.println("加密后：" + encryptHex);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println("解密后：" + decryptStr);

    }



}
