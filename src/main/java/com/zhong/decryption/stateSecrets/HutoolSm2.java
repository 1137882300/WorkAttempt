package com.zhong.decryption.stateSecrets;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;

import java.security.KeyPair;

/**
 * @author: juzi
 * @date: 2023/8/17
 * @desc:
 */
public class HutoolSm2 {


    public static void main4(String[] args) {
        //使用自定义密钥对加密或解密
        String text = "wang jing";

        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();

        //hex
        String hexPrivateKey = HexUtil.encodeHexStr(privateKey);
        String hexPublicKey = HexUtil.encodeHexStr(publicKey);
        System.out.println("hex privateKey--> " + hexPrivateKey);
        System.out.println("hex publicKey --> " + hexPublicKey);

        //base64
        String base64PrivateKey = Base64.encode(privateKey);
        String base64PublicKey = Base64.encode(publicKey);
        System.out.println("base64 privateKey--> " + base64PrivateKey);
        System.out.println("base64 publicKey --> " + base64PublicKey);

        //可以切换方式
        SM2 sm22 = SmUtil.sm2(base64PrivateKey, base64PublicKey);

        // 公钥加密
        String encryptStr2 = sm22.encryptBase64(text, KeyType.PublicKey);
        System.out.println("公钥加密后：" + encryptStr2);

        //私钥解密
        String decryptStr2 = StrUtil.utf8Str(sm22.decrypt(encryptStr2, KeyType.PrivateKey));
        System.out.println("私钥解密后：" + decryptStr2);
    }

    public static void main3(String[] args) {
        String privatekey = "308193020100301306072a8648ce3d020106082a811ccf5501822d04793077020101042025688e402d66a84f98ef8bdd95ff17849ee57b1ef98bc48e90d4244814844c58a00a06082a811ccf5501822da144034200041847a77d8bb931123175a91f12a1924fbf266f0481e3940c1a608edfb715927a716742d75e4d46deadc87c9ff68aafd7e96de8f96c6cc745965404ff372183ba";
        String publicKey = "3059301306072a8648ce3d020106082a811ccf5501822d034200041847a77d8bb931123175a91f12a1924fbf266f0481e3940c1a608edfb715927a716742d75e4d46deadc87c9ff68aafd7e96de8f96c6cc745965404ff372183ba";
        SM2 sm22 = SmUtil.sm2(privatekey, publicKey);
        String miwen = "BBgWUe8bWmGsRC7ptK4z4Ib/pzKE43XtwmhuZEVcdzJQJwBps8kh8msT+1WhcooS/TV7WNeGEmQRcQomEJ8MDo/1JfjrnxJ8XLepBSpLVDtmMQAt450hqDYa1z6hqVtX7qXTLtke7QnGTg==";
        String decryptStr2 = StrUtil.utf8Str(sm22.decrypt(miwen, KeyType.PrivateKey));
        System.out.println("私钥解密后：" + decryptStr2);
    }

    public static void main1(String[] args) {
        String privatekey = "308193020100301306072a8648ce3d020106082a811ccf5501822d04793077020101042025688e402d66a84f98ef8bdd95ff17849ee57b1ef98bc48e90d4244814844c58a00a06082a811ccf5501822da144034200041847a77d8bb931123175a91f12a1924fbf266f0481e3940c1a608edfb715927a716742d75e4d46deadc87c9ff68aafd7e96de8f96c6cc745965404ff372183ba";
        String publicKey = "3059301306072a8648ce3d020106082a811ccf5501822d034200041847a77d8bb931123175a91f12a1924fbf266f0481e3940c1a608edfb715927a716742d75e4d46deadc87c9ff68aafd7e96de8f96c6cc745965404ff372183ba";
        SM2 sm22 = SmUtil.sm2(privatekey, publicKey);
        String miwen = "BBgWUe8bWmGsRC7ptK4z4Ib/pzKE43XtwmhuZEVcdzJQJwBps8kh8msT+1WhcooS/TV7WNeGEmQRcQomEJ8MDo/1JfjrnxJ8XLepBSpLVDtmMQAt450hqDYa1z6hqVtX7qXTLtke7QnGTg==";
        String decryptStr2 = StrUtil.utf8Str(sm22.decrypt(miwen, KeyType.PrivateKey));
        System.out.println("私钥解密后：" + decryptStr2);
    }


}
