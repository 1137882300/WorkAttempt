package com.zhong.decryption.stateSecrets;

import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;

/**
 * @author: juzi
 * @date: 2023/5/17
 * @desc: SM4：无线局域网标准的分组数据算法。对称加密，密钥长度和分组长度均为128位。
 * 由于SM1、SM4加解密的分组大小为128bit，故对消息进行加解密时，若消息长度过长，需要进行分组，要消息长度不足，则要进行填充。
 */
public class Sm4 {


    public static void main(String[] args) {
        String text = "Hello, SM4!";


        //必须是：0123456789abcdef
        //密钥字符串应该是偶数长度，因为每个十六进制字符表示4个bit。
        //密钥字符串中只能包含0-9和a-f（不区分大小写）的字符，其余字符都是非法的。
        //密钥字符串不应包含任何前缀，如"0x"或"0X"。
        byte[] key = Hex.decode("635a6df163f598f450192fc1e5ac730e");
        SymmetricCrypto sm4 = SmUtil.sm4(key);

        String encryptHex = sm4.encryptHex(text);
        System.out.println("加密后：" + encryptHex);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println("解密后：" + decryptStr);

    }



}
