package com.zhong.decryption.stateSecrets;

import cn.hutool.crypto.SmUtil;

/**
 * @author: juzi
 * @date: 2023/5/17
 * @desc: 消息摘要。可以用MD5作为对比理解。该算法已公开。校验结果为256位。
 */
public class Sm3 {

    public static void main(String[] args) {

        String text = "我的信息";
        String digestHex = SmUtil.sm3(text);
        System.out.println("加密后：" + digestHex);

    }

}
