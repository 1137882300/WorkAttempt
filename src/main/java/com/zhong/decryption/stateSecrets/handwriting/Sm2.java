package com.zhong.decryption.stateSecrets.handwriting;


import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.util.encoders.Hex;

import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author: juzi
 * @date: 2023/8/17
 * @desc:
 */
public class Sm2 {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // 生成SM2密钥对
        ECKeyPairGenerator keyPairGenerator = new ECKeyPairGenerator();
        keyPairGenerator.init(new ECKeyGenerationParameters(SM2Util.CURVE, new SecureRandom()));
        AsymmetricCipherKeyPair keyPair = keyPairGenerator.generateKeyPair();
        ECPrivateKeyParameters privateKeyParams = (ECPrivateKeyParameters) keyPair.getPrivate();
        ECPublicKeyParameters publicKeyParams = (ECPublicKeyParameters) keyPair.getPublic();

        // 将公钥和私钥转换为十六进制字符串
        String privateKeyHex = Hex.toHexString(PrivateKeyInfoFactory.createPrivateKeyInfo(privateKeyParams).getEncoded());
        String publicKeyHex = Hex.toHexString(SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(publicKeyParams).getEncoded());
        System.out.println("私钥：" + privateKeyHex);
        System.out.println("公钥：" + publicKeyHex);

        // 根据十六进制字符串恢复公钥和私钥对象
        PrivateKey privateKey = generatePrivateKeyFromHex(privateKeyHex);
        PublicKey publicKey = generatePublicKeyFromHex(publicKeyHex);

        // 待加密的明文
        String plaintext = "Hello, SM2!";

        // 使用公钥进行加密
        byte[] encryptedData = SM2Utils.encrypt(publicKey.getEncoded(), plaintext.getBytes());

        // 使用私钥进行解密
        byte[] decryptedData = SM2Utils.decrypt(privateKey.getEncoded(), encryptedData);

        System.out.println("明文：" + plaintext);
        System.out.println("加密后的密文：" + Hex.toHexString(encryptedData));
        System.out.println("解密后的明文：" + new String(decryptedData));
    }

    private static PrivateKey generatePrivateKeyFromHex(String hex) throws Exception {
        byte[] keyBytes = Hex.decode(hex);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC", new BouncyCastleProvider());
        return keyFactory.generatePrivate(keySpec);
    }

    private static PublicKey generatePublicKeyFromHex(String hex) throws Exception {
        byte[] keyBytes = Hex.decode(hex);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC", new BouncyCastleProvider());
        return keyFactory.generatePublic(keySpec);
    }


    public static class SM2Util {
        public static final X9ECParameters CURVE_PARAMS;
        public static final ECDomainParameters CURVE;
        public static final ECKeyGenerationParameters KEY_PARAMS;

        static {
            CURVE_PARAMS = GMNamedCurves.getByName("sm2p256v1");
            CURVE = new ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(),
                    CURVE_PARAMS.getH());
            KEY_PARAMS = new ECKeyGenerationParameters(CURVE, new SecureRandom());
        }
    }

    public static class SM2Utils {

        public static KeyPair generateKeyPair() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            ECNamedCurveParameterSpec sm2Spec = ECNamedCurveTable.getParameterSpec("sm2p256v1");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
            keyPairGenerator.initialize(sm2Spec, new SecureRandom());
            return keyPairGenerator.generateKeyPair();
        }

        public static byte[] encrypt(byte[] publicKey, byte[] data) throws InvalidCipherTextException, IOException {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            ECNamedCurveParameterSpec sm2Spec = ECNamedCurveTable.getParameterSpec("sm2p256v1");

            ECPublicKeyParameters ecPublicKeyParameters =
                    (ECPublicKeyParameters) PublicKeyFactory.createKey(publicKey);
            CipherParameters cipherParameters = new ParametersWithRandom(ecPublicKeyParameters,
                    new SecureRandom());
            SM2Engine engine = new SM2Engine();
            engine.init(true, cipherParameters);
            return engine.processBlock(data, 0, data.length);
        }

        public static byte[] decrypt(byte[] privateKey, byte[] encryptedData) throws  InvalidCipherTextException, IOException {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            ECNamedCurveParameterSpec sm2Spec = ECNamedCurveTable.getParameterSpec("sm2p256v1");

            ECPrivateKeyParameters ecPrivateKeyParameters =  (ECPrivateKeyParameters) PrivateKeyFactory.createKey(privateKey);
            SM2Engine engine = new SM2Engine();
            engine.init(false, ecPrivateKeyParameters);
            return engine.processBlock(encryptedData, 0, encryptedData.length);
        }



    }
}