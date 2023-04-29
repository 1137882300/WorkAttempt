package com.zhong.decryption.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * AES工具
 */
@Slf4j
public class UserAESCryptoUtil {

    public static String FIXKEY = "19w8bsgecnjemkataqk0ujfk5i4ocvoq";

    private UserAESCryptoUtil() {
    }

    /**
     * 自定义key
     *
     * @return 密钥
     * @throws NoSuchAlgorithmException
     */
    public static byte[] createAESKey() throws NoSuchAlgorithmException {
        return FIXKEY.getBytes();
    }

    /**
     * AES加密
     *
     * @param secretKey 密钥byte数组
     * @param text      明文字符串
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public static byte[] AESEncode(byte[] secretKey, String text)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        SecretKeySpec sKeySpec = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, sKeySpec);

        byte[] result = cipher.doFinal(text.getBytes("UTF-8"));
        return result;
    }


    /**
     * AES解密
     *
     * @param secretKey 密钥byte数组
     * @param text      密文byte数组
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public static String AESDecode(byte[] secretKey, byte[] text)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        SecretKeySpec sKeySpec = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, sKeySpec);

        byte[] result = cipher.doFinal(text);
        return new String(result, "UTF-8");
    }


    /**
     * 二进制字节流转十六进制字符串
     *
     * @param buf 二进制字节流
     * @return 十六进制字符串
     */
    public static String byte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();

        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);

            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }


    /**
     * 十六进制字符串转二进制字节流
     *
     * @param hexStr 十六进制字符串
     * @return 二进制字节流
     */
    public static byte[] hexStr2Byte(String hexStr) {
        // 简单的非空判断
        if (hexStr == null || hexStr.length() < 1) {
            return null;
        }

        byte[] result = new byte[hexStr.length() / 2];

        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);

            result[i] = (byte) (high * 16 + low);
        }

        return result;
    }


    /**
     * 解密
     *
     * @author ashui
     * @date 2022/10/9
     */
    public static String decrypt(String hexEncStr) {
        try {
            byte[] sk = createAESKey(); // 生成密钥
            byte[] byteEncByte = hexStr2Byte(hexEncStr); // 转二进制字节流
            String decStr = AESDecode(sk, byteEncByte); // 解密
            return decStr;
        } catch (Exception ex) {
            log.error("decrypt=" + hexEncStr, ex);
            return null;
        }
    }


    /**
     * 加密
     *
     * @author ashui
     * @date 2022/10/9
     */
    public static String encrypt(String str) {
        try {
            byte[] sk = createAESKey(); // 生成密钥
            byte[] encByte = AESEncode(sk, str); // 加密

            String hexEncStr = byte2HexStr(encByte); // 转十六进制字符串
            return hexEncStr;
        } catch (Exception ex) {
            log.error("encrypt=" + str, ex);
            return null;
        }
    }


    public static void main(String[] args) {

         String encStr = "{'heads_url':'https://jsc.qiandaohu.cc/oss/zyqd/wxhead/default.png','nick_name':'测试昵称','mobile':'188xxxx3136','id':56}";

        try {

            final String encrypt = encrypt(encStr);
            System.out.println("转十六进制后的字符串: " + encrypt);
            final String decrypt = decrypt(encrypt);

            System.out.println("解密后的数据: " + decrypt);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
