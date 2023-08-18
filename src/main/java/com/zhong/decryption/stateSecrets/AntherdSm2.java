package com.zhong.decryption.stateSecrets;

import com.antherd.smcrypto.sm2.Keypair;
import com.antherd.smcrypto.sm2.Sm2;

/**
 * @author: juzi
 * @date: 2023/8/17
 * @desc:
 */
public class AntherdSm2 {

    public static void main3(String[] args) {
        // cipherMode 1 - C1C3C2，0 - C1C2C3，默认为1

        String encryptData = Sm2.doEncrypt("msg", "045dd9c4fae4a95e4ffda40a6220acb59331c37e788c256f089224c372dc8fa1e8246422516713a3ef45444426810f261dc31eb86a25ff9e2d67baab57d9810c13"); // 加密结果
        String decryptData = Sm2.doDecrypt("BIgs4StgAUSRCne/+7wdmrTPlyFl8zLu5sMM4PDuINnWx0gN7/KxQ1VnXJ4LS0UiISQFiKpwoiYbQlbKuCCejc+9qMtpRxVMGSBXZywPIEkclwqRY0Lnw2J7ATppt/0uj5PWUI2BfTBgdA==",
                "00BA234775C5BE2C979E7C724054BC389EF28ED62709568A96360257BBD54030B9"); // 解密结果
        System.out.println(decryptData);

        Keypair keypair = Sm2.generateKeyPairHex();
        String privateKey = keypair.getPrivateKey();
        String publicKey = keypair.getPublicKey();
        System.out.println(privateKey);
        System.out.println(publicKey);

    }

    public static void main(String[] args) {
        String ss = "ce8bd79c3b56f2efaf2c7efa9013da9ed1df563fb90306f040512305de4bcea4e0f6b05bcb9cece54b496f5e39460639f3cca56f7580c4da71043fdb4d18043b5882b4a645e23fb37dce5a08e6384ef44b594d13ab4fcf443121a6232002ba04202960";
        String decrypt = Sm2.doDecrypt(ss, "51137B6A69EF9009DF54CC966CA61CCF65DB7A55AFB15540C3F5586DF6EE17E1");
        System.out.println(decrypt);

        String encrypt = Sm2.doEncrypt("游侠客牛逼", "04990932E081B7FF0FA501CB87EB49AC0456AF6830C1990CA80864D2607465C370DC35424FD02C622B79B72C59B6EB0808F29CC2071B69AFDB1F1BE30F00E1982D");
        System.out.println(encrypt);

    }


}
