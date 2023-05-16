package com.zhong.decryption.stateSecrets;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.apache.commons.lang3.tuple.Pair;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.util.encoders.Hex;

/**
 * @author: juzi
 * @date: 2023/5/16
 * @desc: sm2加密算法
 * 私钥D值（编码后的私钥）
 * 公钥Q值（编码后的公钥）
 */
public class Sm2 {

    private static final String PROVIDER_NAME = "BC"; // BouncyCastle Provider
    private static final String ENCODING = "UTF-8"; // 字符编码
    private static final String ALGORITHM_NAME = "SM2"; // 算法名称

    public static void main(String[] args) {
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
        SM2 sm2 = new SM2();
        //获取秘钥对象
        PrivateKey privateKeyObject = sm2.getPrivateKey();
        PublicKey publicKeyObject = sm2.getPublicKey();
        //生成私钥
        String privateKeyBase64 = Base64.encode(privateKeyObject.getEncoded());
        //生成公钥
        String publicKeyBase64 = Base64.encode(publicKeyObject.getEncoded());
        return Pair.of(privateKeyBase64, publicKeyBase64);
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

}























