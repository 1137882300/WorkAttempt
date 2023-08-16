package com.zhong;

import org.junit.Test;

/**
 * @author: juzi
 * @date: 2023/8/10
 * @desc:
 */
public class HexTest {


    @Test
    public void t() {
        String hexString = "0xe8 0x8e 0xb7 0xe5 0x8f 0x96 0xe8 0xae 0xa2 0xe5 0x8d 0x95 0xe8 0xaf 0xa6 0xe7 0xbb 0x86 0xe4 0xbf 0xa1 0xe6 0x81 0xaf 0xe6 0x88 0x90 0xe5 0x8a 0x9f";
        String[] hexCodes = hexString.split(" ");

        StringBuilder result = new StringBuilder();
        for (String code : hexCodes) {
            // 移除0x前缀并解析十六进制值
            int value = Integer.parseInt(code.substring(2), 16);
            // 将值转换为字符并追加到结果字符串中
            result.append((char) value);
        }

        System.out.println("解码结果: " + result);
    }

}
