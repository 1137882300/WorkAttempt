package com.zhong.decryption.stateSecrets;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import org.apache.commons.lang3.tuple.Pair;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.PlainDSAEncoding;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.util.encoders.Hex;

import javax.xml.bind.DatatypeConverter;

/**
 * @author: juzi
 * @date: 2023/5/16
 * @desc: sm2加密算法
 * 私钥D值（编码后的私钥）
 * 公钥Q值（编码后的公钥）
 * 非对称加密，基于ECC。该算法已公开。由于该算法基于ECC，故其签名速度与秘钥生成速度都快于RSA。
 * ECC 256位（SM2采用的就是ECC 256位的一种）安全强度比RSA 2048位高，但运算速度快于RSA。
 */
public class Sm2Test {

    private static final String PROVIDER_NAME = "BC"; // BouncyCastle Provider
    private static final String ENCODING = "UTF-8"; // 字符编码
    private static final String ALGORITHM_NAME = "SM2"; // 算法名称


    // hutool 自定义公钥私钥
    public static void main5(String[] args) {
        // 定义自定义的公钥和私钥
        String publicKeyStr = "04db9629dd33ba568e9507add5df6587a0998361a03d3321948b448c653c2c1b7056434884ab6f3d1c529501f166a336e86f045cea10dffe58aa82ea13d7253763";
        String privateKeyStr = "1ebf8b341c695ee456fd1a41b82645724bc25d79935437d30e7e4b0a554baa5e";

        // 将公钥和私钥进行Base64解码
        byte[] publicKeyBytes = Base64.decode(publicKeyStr);
        byte[] privateKeyBytes = Base64.decode(privateKeyStr);

        // 定义待加密的数据
        String plaintext = "Hello, SM2!";

        // 执行加密操作
        SM2 sm2 = SmUtil.sm2(publicKeyBytes, privateKeyBytes);
        byte[] encryptedBytes = sm2.encrypt(plaintext.getBytes());

        // 将加密结果转化为Base64编码的字符串
        String encryptedBase64 = Base64.encode(encryptedBytes);

        System.out.println("加密后数据：" + encryptedBase64);

        // 执行解密操作
        byte[] decryptedBytes = sm2.decrypt(Base64.decode(encryptedBase64));
        String decryptedText = new String(decryptedBytes);

        System.out.println("解密后数据：" + decryptedText);
    }

    public static void main4(String[] args) {

        String ss = "我的信息";
        byte[] text = ss.getBytes(StandardCharsets.UTF_8);

        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();

        //HexUtil.encodeHexStr
        //Base64.encode
        String privateKeyEncode = Base64.encode(privateKey);
        String publicKeyEncode = Base64.encode(publicKey);
        System.out.println("私钥: " + privateKeyEncode);
        System.out.println("公钥: " + publicKeyEncode);

        SM2 sm2 = SmUtil.sm2(privateKeyEncode, publicKeyEncode);
        // 公钥加密
        String encryptStr2 = sm2.encryptBase64(text, KeyType.PublicKey);
        System.out.println("公钥加密后：" + encryptStr2);
        //私钥解密
        String decryptStr2 = StrUtil.utf8Str(sm2.decrypt(encryptStr2, KeyType.PrivateKey));
        System.out.println("私钥解密后：" + decryptStr2);

        sm2.setMode(SM2Engine.Mode.C1C3C2);
        sm2.setEncoding(new PlainDSAEncoding());
        byte[] sign = sm2.sign(text, null);
        String signEncode = Base64.encode(sign);
        System.out.println("签名：" + signEncode);
        //验签
        byte[] signDecode = Base64.decode(signEncode);
        boolean verify = sm2.verify(text, signDecode);
        System.out.println("验签：" + verify);
    }


    public static void main1(String[] args) {
        String ss = "我的信息";
        byte[] bytes = ss.getBytes(StandardCharsets.UTF_8);

//        KeyPair keyParis = SecureUtil.generateKeyPair("SM2");
//        PrivateKey privateKey = keyParis.getPrivate();
//        PublicKey publicKey = keyParis.getPublic();

        Pair<String, String> keyParis = getKeyParis();
        System.out.println("私钥：" + keyParis.getKey());
        System.out.println("公钥：" + keyParis.getValue());

        byte[] encryption = encryption(keyParis.getKey(), bytes);

        String sign = generateSignByPrivateKey(keyParis.getKey(), encryption);
        System.out.println("生成签名：" + sign);

        Boolean signOk = verifySignByPublicKey(keyParis.getValue(), sign, encryption);
        System.out.println("签名是否ok：" + signOk);

        byte[] decryption = decryption(keyParis.getValue(), encryption);
        String result = new String(decryption);
        System.out.println(result);


    }


    //todo  hutool
    public static Pair<String, String> getKeyParis() {
        //随机生成秘钥
        SM2 sm2 = SmUtil.sm2();
        //获取秘钥对象
        PrivateKey privateKeyObject = sm2.getPrivateKey();
        PublicKey publicKeyObject = sm2.getPublicKey();
        //生成私钥
//        String privateKeyBase64 = Base64.encode(privateKeyObject.getEncoded());
        String privateKey = HexUtil.encodeHexStr(BCUtil.encodeECPrivateKey(privateKeyObject));
        //生成公钥
//        String publicKeyBase64 = Base64.encode(publicKeyObject.getEncoded());
//        String publicKey = HexUtil.encodeHexStr(BCUtil.encodeECPublicKey(publicKeyObject));
        String publicKey = HexUtil.encodeHexStr(((BCECPublicKey) publicKeyObject).getQ().getEncoded(false));
        return Pair.of(privateKey, publicKey);
    }


    //加密
    public static byte[] encryption(String publicKey, byte[] bytes) {
        // 初始化SM2对象
        SM2 sm2 = new SM2(null, publicKey);
        return sm2.encrypt(bytes, KeyType.PublicKey);
    }

    //解密
    public static byte[] decryption(String privateKey, byte[] bytes) {
        // 初始化SM2对象
        SM2 sm2 = new SM2(privateKey, null);
        return sm2.decrypt(bytes, KeyType.PrivateKey);
    }


    /**
     * 通过私钥进行文件签名
     *
     * @param privateKey 私钥
     * @param dataBytes  需要签名的文件以流的形式
     */
    public static String generateSignByPrivateKey(String privateKey, byte[] dataBytes) {
        // 私钥HEX处理
        byte[] decode = Base64.decode(privateKey);
        SM2 sm3 = new SM2(decode, null);
        byte[] bytes = BCUtil.encodeECPrivateKey(sm3.getPrivateKey());
        String privateKeyHex = HexUtil.encodeHexStr(bytes);
        //需要加密的明文,得到明文对应的字节数组
        ECPrivateKeyParameters privateKeyParameters = BCUtil.toSm2Params(privateKeyHex);
        //创建sm2 对象
        SM2 sm2 = new SM2(privateKeyParameters, null);
        //这里需要手动设置，sm2 对象的默认值与我们期望的不一致 , 使用明文编码
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        byte[] sign = sm2.sign(dataBytes, null);
        // hex to base64
        return Base64.encode(sign);
    }


    /**
     * 通过公钥进行文件验签
     *
     * @param publicKey 公钥
     * @param sign      签名（原先为hex处理后的16位，现在改为base处理后的64位）
     * @param dataBytes 需要验签的文件数据以流的形式
     * @return
     */
    public static Boolean verifySignByPublicKey(String publicKey, String sign, byte[] dataBytes) {
        // 修改公钥HEX处理
        byte[] decode = Base64.decode(publicKey);
        SM2 sm3 = new SM2(null, decode);
        byte[] bytes = ((BCECPublicKey) sm3.getPublicKey()).getQ().getEncoded(false);
        String publicKeyHex = HexUtil.encodeHexStr(bytes);
        // 公钥HEX处理
        //需要加密的明文,得到明文对应的字节数组
        //这里需要根据公钥的长度进行加工
        if (publicKeyHex.length() == 130) {
            //这里需要去掉开始第一个字节 第一个字节表示标记
            publicKeyHex = publicKeyHex.substring(2);
        }
        String xHex = publicKeyHex.substring(0, 64);
        String yHex = publicKeyHex.substring(64, 128);
        ECPublicKeyParameters ecPublicKeyParameters = BCUtil.toSm2Params(xHex, yHex);
        //创建sm2 对象
        SM2 sm2 = new SM2(null, ecPublicKeyParameters);
        //这里需要手动设置，sm2 对象的默认值与我们期望的不一致 , 使用明文编码
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        return sm2.verify(dataBytes, Base64.decode(sign));
    }

    public static void main2(String[] args) {
        createSm2KeyTest();
    }

    public static void createSm2KeyTest() {
        //需要加密的明文
        String text = "我是一段测试aaaa";
        //创建sm2 对象
        SM2 sm2 = SmUtil.sm2();
        //这里会自动生成对应的随机秘钥对 , 注意！ 这里一定要强转，才能得到对应有效的秘钥信息
        byte[] privateKey = BCUtil.encodeECPrivateKey(sm2.getPrivateKey());
        //这里公钥不压缩  公钥的第一个字节用于表示是否压缩  可以不要
        byte[] publicKey = ((BCECPublicKey) sm2.getPublicKey()).getQ().getEncoded(false);
        //这里得到的 压缩后的公钥   ((BCECPublicKey) sm2.getPublicKey()).getQ().getEncoded(true);
        // byte[] publicKeyEc = BCUtil.encodeECPublicKey(sm2.getPublicKey());
        //打印当前的公私秘钥
        System.out.println("私钥: " + HexUtil.encodeHexStr(privateKey));
        System.out.println("公钥: " + HexUtil.encodeHexStr(publicKey));
        //得到明文对应的字节数组
        byte[] dateBytes = text.getBytes();
        System.out.println("数据: " + HexUtil.encodeHexStr(dateBytes));
        //这里需要手动设置，sm2 对象的默认值与我们期望的不一致
        sm2.setMode(SM2Engine.Mode.C1C2C3);
        sm2.setEncoding(new PlainDSAEncoding());
        //计算签名
        byte[] sign = sm2.sign(dateBytes, null);
        System.out.println("签名: " + HexUtil.encodeHexStr(sign));
        // 校验  验签
        boolean verify = sm2.verify(dateBytes, sign);
        System.out.println(verify);
    }


    public static void main3(String[] args) {
        sm2Test();
    }

    public static void sm2Test() {

        String text = "wang jing";

        //使用随机生成的密钥对加密或解密
        System.out.println("使用随机生成的密钥对加密或解密====开始");
        SM2 sm2 = SmUtil.sm2();
        // 公钥加密
        String encryptStr = sm2.encryptBase64(text, KeyType.PublicKey);
        System.out.println("公钥加密：" + encryptStr);
        //私钥解密
        String decryptStr = StrUtil.utf8Str(sm2.decrypt(encryptStr, KeyType.PrivateKey));
        System.out.println("私钥解密：" + decryptStr);
        System.out.println("使用随机生成的密钥对加密或解密====结束");


        //使用自定义密钥对加密或解密
        System.out.println("使用自定义密钥对加密或解密====开始");

        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();

        SM2 sm22 = SmUtil.sm2(privateKey, publicKey);
        // 公钥加密
        String encryptStr2 = sm22.encryptBase64(text, KeyType.PublicKey);
        System.out.println("公钥加密：" + encryptStr2);
        //私钥解密
        String decryptStr2 = StrUtil.utf8Str(sm22.decrypt(encryptStr2, KeyType.PrivateKey));
        System.out.println("私钥解密：" + decryptStr2);
        System.out.println("使用自定义密钥对加密或解密====结束");

    }

    public static void main7(String[] args) {
        //使用自定义密钥对加密或解密
        String text = "wang jing";

        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();
        System.out.println("privateKey--> " + HexUtil.encodeHexStr(privateKey));
        System.out.println("publicKey--> " + HexUtil.encodeHexStr(publicKey));

        SM2 sm22 = SmUtil.sm2(HexUtil.encodeHexStr(privateKey), HexUtil.encodeHexStr(publicKey));
        // 公钥加密
        String encryptStr2 = sm22.encryptBase64(text, KeyType.PublicKey);
        System.out.println("公钥加密后：" + encryptStr2);
        //私钥解密
        String decryptStr2 = StrUtil.utf8Str(sm22.decrypt(encryptStr2, KeyType.PrivateKey));
        System.out.println("私钥解密后：" + decryptStr2);
    }

    public static void main(String[] args) {

        String privatekey = "308193020100301306072a8648ce3d020106082a811ccf5501822d04793077020101042025688e402d66a84f98ef8bdd95ff17849ee57b1ef98bc48e90d4244814844c58a00a06082a811ccf5501822da144034200041847a77d8bb931123175a91f12a1924fbf266f0481e3940c1a608edfb715927a716742d75e4d46deadc87c9ff68aafd7e96de8f96c6cc745965404ff372183ba";
        String publicKey = "3059301306072a8648ce3d020106082a811ccf5501822d034200041847a77d8bb931123175a91f12a1924fbf266f0481e3940c1a608edfb715927a716742d75e4d46deadc87c9ff68aafd7e96de8f96c6cc745965404ff372183ba";
        SM2 sm22 = SmUtil.sm2(privatekey, publicKey);
        String miwen = "BBgWUe8bWmGsRC7ptK4z4Ib/pzKE43XtwmhuZEVcdzJQJwBps8kh8msT+1WhcooS/TV7WNeGEmQRcQomEJ8MDo/1JfjrnxJ8XLepBSpLVDtmMQAt450hqDYa1z6hqVtX7qXTLtke7QnGTg==";
        String decryptStr2 = StrUtil.utf8Str(sm22.decrypt(miwen, KeyType.PrivateKey));
        System.out.println("私钥解密后：" + decryptStr2);
    }
}























