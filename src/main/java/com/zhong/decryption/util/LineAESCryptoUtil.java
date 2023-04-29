package com.zhong.decryption.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * AES工具
 *
 * @author Aomr
 * @version V1.0
 * @since 2019-04-27 15:36:04
 */
@Slf4j
public class LineAESCryptoUtil {

    public static int SECRET_KEY_LENGTH = 128;

    public static String KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh210DF0zTUeqbopSdzEEGr3/DyexxBBSLANGqu2kAmPV1fJuEplGoZrR77zn4xKsjDrzolYLaQFfwQg801zA33c+yRmMY8VE4BI9jMqW7fm3DbVOZ7HdRTXUDJjwW++PVhDyu4Yio3540bXlQQZZe/L9g5/F7k3ttOH8SCikvJCNZt15VaSk1ZXpXH2076kBoa5CzEyuFLBuZ5KJS45XqbEiTKkfHLBRpBilT9v2WnfuacrLSNl6WraXGTCWWyuWNpmwu3wgk7ZiWU66lEXNcS4Kl0wvbKrWWedM9p90ymfGHHd/PRgV/KKtxYAc5Cl8u2y04rHS7sjS7SMPMUymhwIDAQAB";

    public static String FIXKEY = "e0de64e05dad2d98";

    private LineAESCryptoUtil() {
    }

    /**
     * 自定义key
     *
     * @param key 密钥字符串
     * @return 密钥
     * @throws NoSuchAlgorithmException
     */
    public static byte[] createAESKey(String key) throws NoSuchAlgorithmException {
//        KeyGenerator kg = KeyGenerator.getInstance("AES");
//        kg.init(128, new SecureRandom(key.getBytes()));
//
//        SecretKey sk = kg.generateKey();
//        byte[] skBuffer = sk.getEncoded();

        return FIXKEY.getBytes();
    }

    /**
     * 无参生成
     *
     * @return 密钥
     * @throws NoSuchAlgorithmException
     */
    public static byte[] autoCreateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128);

        SecretKey sk = kg.generateKey();
        byte[] skBuffer = sk.getEncoded();

        return skBuffer;
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
            byte[] sk = createAESKey(KEY); // 生成密钥
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
            byte[] sk = createAESKey(KEY); // 生成密钥
            byte[] encByte = AESEncode(sk, str); // 加密

            String hexEncStr = byte2HexStr(encByte); // 转十六进制字符串
            return hexEncStr;
        } catch (Exception ex) {
            log.error("encrypt=" + str, ex);
            return null;
        }
    }


    public static void main(String[] args) {

         String encStr = "{\"code\":\"0\",\"data\":{\"allowCancel\":0,\"brandName\":\"英迪格\",\"cancelNum\":\"34555543\",\"cancelPolicy\":\"\",\"cancelTime\":\"2022-12-05 15:00:00\",\"checkinDate\":\"2022-12-06\",\"checkoutDate\":\"2022-12-07\",\"confirmNum\":\"87666678\",\"hotelName\":\"上海静安英迪格酒店\",\"image\":\"https://cdn.youxiatrip.com/hotel/46edc431-b525-4179-86b4-b7775d400c21.jpg\",\"interestName\":\"200元餐饮额度（ALIPAY MEMBER HOTELS）-上海静安英迪格酒店\",\"interestText\":\"【FOR 尊享价格】\\n每日至多2份免费早餐（根据预定或实际入住人数提供）\\n每间房享有200元人民币餐饮消费抵扣额度\\n免费升一级房型（视入住当天房态而定）\\n提早入住/延迟退房，视房态而定\\n抵达入住时赠送酒店特色欢迎饮品和礼遇\\n免费WiFi使用\\n备注：具体以酒店说明为准，仅限尊享价格\",\"orderSn\":\"1527432012036571776\",\"orderStatus\":7,\"orderStatusExplain\":\"已取消\",\"owner\":\"lifeilong\",\"phone\":\"+8615757175993\",\"ppFirstName\":\"feilong\",\"ppLastName\":\"li\",\"ppPhone\":\"+8615757175993\",\"ppSex\":\"男\",\"promotionNames\":\"\",\"roomDetailName\":\"尊享价格\",\"roomDetailNameEn\":\"ALIPAY MEMBER HOTELS AND\",\"roomDetailText\":\"\",\"roomDetailTextEn\":\"\",\"roomName\":\"STANDARD ROOM BED TYPE CANNOT BE GUARANTEED\",\"text\":\"入住：2022/12/06 【居住2人】\",\"totalPriceCny\":1268.61},\"msg\":\"SUCCESS\",\"success\":true,\"time\":\"2022-10-09T10:56:06.422\"}";
       // String encStr = "Hello World";

        try {


            final String encrypt = encrypt(encStr);
            System.out.println("转十六进制后的字符串: " + encrypt);
            final String decrypt = decrypt(encrypt);

            System.out.println("解密后的数据: " + decrypt);


//            byte[] sk = FIXKEY.getBytes(); // 生成密钥
//            byte[] encByte = AESEncode(sk, encStr); // 加密
//
//            String hexEncStr = byte2HexStr(encByte); // 转十六进制字符串
//            System.out.println("转十六进制后的字符串: " + hexEncStr);
//
//            byte[] byteEncByte = hexStr2Byte(hexEncStr); // 转二进制字节流
//
//            String decStr = AESDecode(sk, byteEncByte); // 解密
//
//            System.out.println("解密后的数据: " + decStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
