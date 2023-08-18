package com.zhong.decryption.stateSecrets;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;

import java.security.KeyPair;
import java.util.Arrays;

/**
 * @author: juzi
 * @date: 2023/8/18
 * @desc:
 */
public class HutoolSm2V2 {

    //使用随机生成的密钥对加密或解密
    public static void main1(String[] args) {
        String text = "我是一段测试aaaa";

        SM2 sm2 = SmUtil.sm2();
        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
        System.out.println(decryptStr);
    }


    //使用自定义密钥对加密或解密
    public static void main2(String[] args) {
        String text = "我是一段测试aaaa";

        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();

        //base64
        String base64PrivateKey = Base64.encode(privateKey);
        String base64PublicKey = Base64.encode(publicKey);

        System.out.println("base64 privateKey--> " + base64PrivateKey);
        System.out.println("base64 publicKey --> " + base64PublicKey);

        //hex
        String hexPrivateKey = HexUtil.encodeHexStr(privateKey);
        String hexPublicKey = HexUtil.encodeHexStr(publicKey);
        System.out.println("hex privateKey--> " + hexPrivateKey);
        System.out.println("hex publicKey --> " + hexPublicKey);

//        SM2 sm2 = SmUtil.sm2("1279545C67F810778B6D62AA4F739D3029863F584CA932A44B2C950AE26052CA",
//                "04207605C6BFA0A58DBF8CEAC564AD95033151FABBB955045BF0C89A5C7FAF4F40850F8A6E6C395F86ABF3CB72F3CC5E3AA33708EDA70FC4611F46A82BD471EE41");

        SM2 sm2 = SmUtil.sm2("04207605C6BFA0A58DBF8CEAC564AD95033151FABBB955045BF0C89A5C7FAF4F40850F8A6E6C395F86ABF3CB72F3CC5E3AA33708EDA70FC4611F46A82BD471EE41",
                "1279545C67F810778B6D62AA4F739D3029863F584CA932A44B2C950AE26052CA");

        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);
        System.out.println("公钥加密后：" + encryptStr);

        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
        System.out.println("私钥解密后：" + decryptStr);
        System.out.println("私钥解密后：" + sm2.decryptStr(encryptStr, KeyType.PrivateKey));
        System.out.println("私钥解密后：" + StrUtil.utf8Str(sm2.decrypt(encryptStr, KeyType.PrivateKey)));
    }

    //加密 Base64
    public static void main11(String[] args) {
        //hex的公私钥
        String privateKey = "059AF2966C7127D9CB8B942D628E38BB0B28F4D2A469C0F52971BB47E28C0BDB";
        String publicKey = "0488E7BD796A255A6B093B10B1E91017EF83111A81FD93EFAAB9B01AF0F027AFB34031AA0C0EA2D77CD4D2C6D0307352330E21BC45241521FE8A54CAFCD1AB64FC";

        //加密/解密的内容（数据格式):文本，解密则为base64
        String content = "你是傻逼";
        //加密的结果：文本

        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        String decryptStr = sm2.encryptBase64(content, KeyType.PublicKey);
        System.out.println(decryptStr);
    }

    //解密
    public static void main55(String[] args) {
        //hex的公私钥
        String privateKey = "059AF2966C7127D9CB8B942D628E38BB0B28F4D2A469C0F52971BB47E28C0BDB";
        String publicKey = "0488E7BD796A255A6B093B10B1E91017EF83111A81FD93EFAAB9B01AF0F027AFB34031AA0C0EA2D77CD4D2C6D0307352330E21BC45241521FE8A54CAFCD1AB64FC";

        String content = "BOhOSq2P9ZJ1gj0MnFin0a3WIvNnqOl3oiIebetQnLDz2ISIvQ/EjoM2lf3pL4yrfyCTRt6lTHtg/yyJOD3uw1xte+Sy1CdYVzZe4ZOwumQ2ewFHz3kCAgp0sACl+DTxKwmGssrV6marPLzEUg==";

        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        String decryptStr = sm2.decryptStr(content, KeyType.PrivateKey);
        System.out.println(decryptStr);
    }

    //hex
    public static void main22(String[] args) {
        //hex的公私钥
        String privateKey = "51137B6A69EF9009DF54CC966CA61CCF65DB7A55AFB15540C3F5586DF6EE17E1";
        String publicKey = "04990932E081B7FF0FA501CB87EB49AC0456AF6830C1990CA80864D2607465C370DC35424FD02C622B79B72C59B6EB0808F29CC2071B69AFDB1F1BE30F00E1982D";

        //加密/解密的内容（数据格式):文本，解密则为base64
        String content = "游侠客牛逼";
        //加密的结果：文本

        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        String decryptStr = sm2.encryptHex(content, KeyType.PublicKey);
        System.out.println(decryptStr);
    }

    //解密 hex
    public static void main(String[] args) {
        //hex的公私钥
        String privateKey = "51137B6A69EF9009DF54CC966CA61CCF65DB7A55AFB15540C3F5586DF6EE17E1";
        String publicKey = "04990932E081B7FF0FA501CB87EB49AC0456AF6830C1990CA80864D2607465C370DC35424FD02C622B79B72C59B6EB0808F29CC2071B69AFDB1F1BE30F00E1982D";

        String content = "ce8bd79c3b56f2efaf2c7efa9013da9ed1df563fb90306f040512305de4bcea4e0f6b05bcb9cece54b496f5e39460639f3cca56f7580c4da71043fdb4d18043b5882b4a645e23fb37dce5a08e6384ef44b594d13ab4fcf443121a6232002ba04202960";

        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
//        String decryptStr = sm2.decryptStr(content, KeyType.PrivateKey);
//        System.out.println(decryptStr);

        System.out.println(StrUtil.utf8Str(sm2.decrypt(content, KeyType.PrivateKey)));
        System.out.println(StrUtil.utf8Str(sm2.decryptFromBcd(content, KeyType.PrivateKey)));
        System.out.println(StrUtil.utf8Str(sm2.decryptStrFromBcd(content, KeyType.PrivateKey)));

    }

}
