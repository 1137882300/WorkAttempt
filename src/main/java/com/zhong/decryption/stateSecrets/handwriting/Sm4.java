package com.zhong.decryption.stateSecrets.handwriting;


import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author: juzi
 * @date: 2023/8/17
 * @desc:
 */
public class Sm4 {

    public static void main(String[] args) {
        // 定义密钥
        byte[] key = Hex.decode("0123456789abcdef0123456789abcdef");

        // 定义待加密的数据
        String plaintext = "你好Aa@@234";
        byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);

        // 执行加密操作
        byte[] encryptedBytes = sm4Encrypt(key, plaintextBytes);
        String encryptedHex = Hex.toHexString(encryptedBytes);

        System.out.println("加密后数据：" + encryptedHex);

        // 执行解密操作
        byte[] decryptedBytes = sm4Decrypt(key, Hex.decode(encryptedHex));
        String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);

        System.out.println("解密后数据：" + decryptedText);
    }

    public static byte[] sm4Encrypt(byte[] key, byte[] plaintext) {
        SM4Engine sm4Engine = new SM4Engine();
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(sm4Engine);

        // 初始化加密参数
        cipher.init(true, new KeyParameter(key));

        // 执行加密操作
        byte[] encryptedBytes = new byte[cipher.getOutputSize(plaintext.length)];
        int processLen = cipher.processBytes(plaintext, 0, plaintext.length, encryptedBytes, 0);
        try {
            cipher.doFinal(encryptedBytes, processLen);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encryptedBytes;
    }

    public static byte[] sm4Decrypt(byte[] key, byte[] ciphertext) {
        SM4Engine sm4Engine = new SM4Engine();
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(sm4Engine);

        // 初始化解密参数
        cipher.init(false, new KeyParameter(key));

        // 执行解密操作
        byte[] decryptedBytes = new byte[cipher.getOutputSize(ciphertext.length)];
        int processLen = cipher.processBytes(ciphertext, 0, ciphertext.length, decryptedBytes, 0);
        try {
            cipher.doFinal(decryptedBytes, processLen);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decryptedBytes;
    }

//    --------------------------------------------------------------
    public static class SM4EncryptionExample {

        public static void main(String[] args) {
            // 定义密钥
            byte[] key = Hex.decode("0123456789abcdef0123456789abcdef");

            // 定义待加密的数据
            String plaintext = "Hello, SM4!";
            byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);

            // 执行加密操作
            byte[] encryptedBytes = sm4Encrypt(key, plaintextBytes);
            String encryptedHex = Hex.toHexString(encryptedBytes);

            System.out.println("加密后数据：" + encryptedHex);

            // 执行解密操作
            byte[] decryptedBytes = sm4Decrypt(key, Hex.decode(encryptedHex));

            // 获取解密数据的实际长度
            int actualLength = getDecryptedDataLength(decryptedBytes);

            // 截取解密数据，去除填充
            byte[] resultBytes = Arrays.copyOf(decryptedBytes, actualLength);
            String decryptedText = new String(resultBytes, StandardCharsets.UTF_8);

            System.out.println("解密后数据：" + decryptedText);
        }

        public static byte[] sm4Encrypt(byte[] key, byte[] plaintext) {
            SM4Engine sm4Engine = new SM4Engine();
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(sm4Engine);

            // 初始化加密参数
            cipher.init(true, new KeyParameter(key));

            // 执行加密操作
            byte[] encryptedBytes = new byte[cipher.getOutputSize(plaintext.length)];
            int processLen = cipher.processBytes(plaintext, 0, plaintext.length, encryptedBytes, 0);
            try {
                cipher.doFinal(encryptedBytes, processLen);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return encryptedBytes;
        }

        public static byte[] sm4Decrypt(byte[] key, byte[] ciphertext) {
            SM4Engine sm4Engine = new SM4Engine();
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(sm4Engine);

            // 初始化解密参数
            cipher.init(false, new KeyParameter(key));

            // 执行解密操作
            byte[] decryptedBytes = new byte[cipher.getOutputSize(ciphertext.length)];
            int processLen = cipher.processBytes(ciphertext, 0, ciphertext.length, decryptedBytes, 0);
            try {
                cipher.doFinal(decryptedBytes, processLen);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return decryptedBytes;
        }

        public static int getDecryptedDataLength(byte[] decryptedBytes) {
            // 获取填充字节的数量
            int padCount = decryptedBytes[decryptedBytes.length - 1];

            // 计算无填充数据的长度
            return decryptedBytes.length - padCount;
        }
    }
}