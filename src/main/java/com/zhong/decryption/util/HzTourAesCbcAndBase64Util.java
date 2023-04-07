package com.zhong.decryption.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Base64;

/**
* @author: 竹阳
* @date: 2022/9/30
* 前端交互加密解密
*/
@Slf4j
public class HzTourAesCbcAndBase64Util {

    /**
     * @author: 竹阳
     * @date: 2022/9/30
     * 解密
     */
    public static String decrypt(String content, String slatKey, String vectorKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
        IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] encrypted = cipher.doFinal(Hex.decodeHex(new String(Base64.getDecoder().decode(content))));
        return new String(encrypted);
    }

    public static String decryptWithPKCS7(String content, String sessionKey, String vectorKey) throws Exception {
        byte[] aesKey = Base64.getDecoder().decode(sessionKey);
        byte[] aesIV = Base64.getDecoder().decode(vectorKey);
        byte[] aesCipher = Base64.getDecoder().decode(content);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        Key sKeySpec = new SecretKeySpec(aesKey, "AES");
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(aesIV));
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec, params);
        return new String(cipher.doFinal(aesCipher), StandardCharsets.UTF_8);
    }

    /**
     * @author: 竹阳
     * @date: 2022/9/30
     * 加密
     */
    public static String encrypt(String content, String slatKey, String vectorKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
        IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encrypted = cipher.doFinal(content.getBytes());
        String encodeHexString = Hex.encodeHexString(encrypted);
        return Base64.getEncoder().encodeToString(encodeHexString.getBytes()).replace("=", "");
    }

    public static void main(String[] args) throws Exception {
        String s = new String(Base64.getDecoder().decode("MzVjM2VlODk2ZTQ4MWE3ZTFkOWQ4MGQzZWU1ZmM1MmQ0ODcwYjYzZTRhYzQxZjQzYTAxZWVmYTM5YWE3NDIwNDk1MWU4NDRkNzU1NDNhNDVjYTNiNjE2Y2YxMzgxNDdk"));
        System.out.println(s);
//        System.out.println(WLAesUtils.decrypt("35c3ee896e481a7e1d9d80d3ee5fc52d4870b63e4ac41f43a01eefa39aa74204951e844d75543a45ca3b616cf138147d", "gMhXfD9ePF8YhXQx", "hm3qvrxGaeJLrvx9"));
        System.out.println(decrypt("MzVjM2VlODk2ZTQ4MWE3ZTFkOWQ4MGQzZWU1ZmM1MmQ0ODcwYjYzZTRhYzQxZjQzYTAxZWVmYTM5YWE3NDIwNDk1MWU4NDRkNzU1NDNhNDVjYTNiNjE2Y2YxMzgxNDdk", "gMhXfD9ePF8YhXQx", "hm3qvrxGaeJLrvx9"));
        System.out.println(encrypt("[{\"cardNo\":\"fads\",\"name\":\"fas\",\"phone\":\"fas\"}]","gMhXfD9ePF8YhXQx", "hm3qvrxGaeJLrvx9"));
        System.out.println(encrypt("{\"cardNo\":\"110101199003079235\",\"name\":\"甜美\",\"phone\":\"17857336786\"}","gMhXfD9ePF8YhXQx", "hm3qvrxGaeJLrvx9"));
    }

}
