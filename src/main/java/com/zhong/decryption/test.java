package com.zhong.decryption;

import com.zhong.cache.Main;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Base64;

/**
 * @author: 橘子
 * @date: 2023/3/8
 */
public class test {

    /**
     *  AES-128-CBC，数据采用PKCS#7填充
     * @param args
     */


    @SneakyThrows
    public static void main(String[] args) {
        String encryptedData = "+2IEjaY+hWHm1fxlwz0fWED3H6+3ngguEBLxTwaPBlqaj8F8DZRMgW1f37UyQajxx8Z+guAS0WIDUa9XDvKmF+z+K90FfZ0xWblLZ2WVUdaQlS1GCopX1sC7ckVVD5lLIuaBWSdPFXOQij6IAYJXIaJ+i+yp/YO9q0bf4UPkEpFW42yxcCUkS7hE+1lCapmB5BCFdNHQAHXM34LftfwBEAqP2kdUbcW5cfumPEeBZETXC3eWhEr7JTsHWxT6d7LmftzsfuYSXe/UZClfyA/QWUrJzxonZ975+E45At0ud4M9vYefnHDczEh8E4pKFMKl4u5GzzhQE9vSJxVb4l7Ss2mB7dVdiwGb9pSdz8Z8mOSQMIJfZEqh5dVWmZc4dzwZ51caCVlEvVIkKz+0MzVaWVVpmYAuQoLB+/x7VjSL+Mi3BoABm0hvg9l/YyuEsozBme0vYiGdP9w7i/C8+P0MWhN+Ruq6PHSM31Fvy72ITeLWN2+Li3h7zARoZ99dduIWzy9mFMmY7QIjchKg+ctQuDfmgYp+qI9ed/UQ8AZAPfRzHfg4Zsv/HxSD55ujUkQRpVF5VeY7xu75jDacirk+otUEyv8ppa5VeVWJPHvAfF+bzJ7t8mN8hzhAe2y9ctFkfFXeaizKHHnafkieP4yzeajt6Wtf5ilrbyVfqpldAp69bfz5jcmbhAwuqUFD6rVT98/diM5E2PM2svdBJyxQmZLkWQjUOS+a51Z+/5lkDn3c6OLFfG+WZWbMLcajmk0m8nO51PX+0NcDPsmx3PUdbu9UgQ/W9fhZD5p7hpnINn7HocD7oD1E0/Vt4PYT4WMgRXNJCQ8dsJdETe+VvWbxh8YNxkJ5wRS5KKyq8V3aEAvgHCqmHFk2OnW0/sdKz3xX5Kr2gGC6fHUqMDbQhIUQ6+LCTHkbYPaonaWd6z1k/JW/miy6pitlNAqn0PecC3dBgL0XW0GbpHrZY7HN3j3HTKvUXDNrZQn9qhny9aR/4ryBaf0sfqeZ5/FwKsAVhZB/iXX2IWpR4OK/bIF7efmKfP+gWPXhTx629AlT50l5DA5C02TItHj121UIYE94Uu7omPMO5/BLsD2YPDcjnwWqFsPY45y3b9lDXcjH6IHlyFiVPkaMpFde6+SNnf+XvmgAulntHuMs4ge6rbvS+8pXLo3PgiJraEICjlrXl/f49XLJXW/Fd6YnOT05qhlwuXYR4f9CYmV+xLXcJHRhBicv87UmyKy3SxHuniBKxr0j/q9kBJPB2zCiXSsnThKGQXTLb1OQFN+1YGo1MxGcjNU5tiMB9Rdt/wnXBL7OcLZrlxfDr6+RFXbL68byBRqZJyum4WL/Sv0ME1mRQMWvuS65hi0ZdgKpXJOSl76Op6STTJpZF3q4ToGIrzHq63oLQaUCp2mYjZzc6ihy5JAa5xsEMr28pKUmcRf9rFO1xrLb+E6cXxkEvVLuAQa1xbjuN6zPIDLO1qIUPMKQFyRpUvRDhZGBu6IbA9Drba9Wu5w2QzZv4zDnHNxAgp9Tx63y9b2NGGzxYGJdgPfVdlDOJcYBfgXlRRwWHvIt29/rynq2hd14BrVCh9fu+4ClOVTQCXyqwlztgNVD0DJbeqUBbMove81kaPmFc+BZ54W0mqQzdoo=";
        String iv = "hg5LTVD2X3Hn91w6RB6/XQ==";
        String sessionKey = "kM9fdtiMgPfayqSzeg6rDg==";

        String data = decryptAesPkcData(encryptedData, iv, sessionKey);
        System.out.println(data);
    }

    public static String decryptAesPkcData(String encryptedData, String iv, String sessionKey) throws Exception {
        //检查sessionKey的长度
        if (sessionKey.length() != 24) {

        }
        //检查iv的长度
        if (iv.length() != 24) {

        }
//        byte[] aesKey = Base64.decodeBase64(sessionKey);
//        byte[] aesIV = Base64.decodeBase64(iv);
//        byte[] aesCipher = Base64.decodeBase64(encryptedData);
//        Base64.getDecoder().decode
        byte[] aesKey = Base64.getDecoder().decode(sessionKey);
        byte[] aesIV = Base64.getDecoder().decode(iv);
        byte[] aesCipher = Base64.getDecoder().decode(encryptedData);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        Key sKeySpec = new SecretKeySpec(aesKey, "AES");
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(aesIV));
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec, params);
        return new String(cipher.doFinal(aesCipher), StandardCharsets.UTF_8);
    }

}
