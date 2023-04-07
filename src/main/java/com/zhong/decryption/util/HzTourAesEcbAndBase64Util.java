package com.zhong.decryption.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author: 竹阳
 * @date: 2022/9/24
 * AES & BASE64加密
 * 后端交互加密解密（免密登录，订单回流信息等）
 */
public class HzTourAesEcbAndBase64Util {
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

    /**
     * AES 加密操作
     * 加密信息是url上拼接方式传输需要用这个方法
     *
     * @param content 待加密内容
     * @param key     加密密钥
     * @return 返回Base64转码后的加密数据
     */
    public static String encryptUrl(String content, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器

        byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);

        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));// 初始化为加密模式的密码器

        byte[] result = cipher.doFinal(byteContent);// 加密

        return Base64.getUrlEncoder().encodeToString(result).replace("=", "");//通过Base64转码返回
    }

    /**
     * AES 解密操作
     * 加密信息是url上拼接方式传输需要用这个方法
     *
     * @param content
     * @param key
     * @return
     */
    public static String decryptUrl(String content, String key) throws Exception {

        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));

        //执行操作
        byte[] result = cipher.doFinal(Base64.getUrlDecoder().decode(content));


        return new String(result, StandardCharsets.UTF_8);
    }

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param key     加密密钥
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器

        byte[] byteContent = content.getBytes("utf-8");

        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));// 初始化为加密模式的密码器

        byte[] result = cipher.doFinal(byteContent);// 加密

        return Base64.getEncoder().encodeToString(result).replace("=", "");//通过Base64转码返回
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(String content, String key) throws Exception {

        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));

        //执行操作
        byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));


        return new String(result, "utf-8");
    }

    private static SecretKeySpec getSecretKey(final String key) throws Exception {
//        //返回生成指定算法密钥生成器的 KeyGenerator 对象
//        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
//
//        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//        random.setSeed(key.getBytes());
//        //AES 要求密钥长度为 128
//        kg.init(128, random);
//
//        //生成一个密钥
//        SecretKey secretKey = kg.generateKey();

        return new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);// 转换为AES专用密钥
    }

    public static void main(String[] args) throws Exception {
        String data = "wfawegeasrgearhg数据内容";
        String key = "gerthsergegwagag";
        String encrypt = encryptUrl(data, key);
        System.out.println(encrypt);
        String decrypt = decryptUrl(encrypt, key);
        System.out.println(decrypt);
    }
}
