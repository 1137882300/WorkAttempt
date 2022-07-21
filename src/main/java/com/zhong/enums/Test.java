package com.zhong.enums;

import java.util.List;

/**
 * @date 2022/7/21 10:07
 */
public class Test {

    public static void main(String[] args) {
        int errorCode = TranslateErrorEnum2.SYSTEM_ERROR.getErrorCode();
        System.out.println(errorCode);
//
//        List<String> mapping = TranslateErrorEnum2.SYSTEM_ERROR.mapping();
//        System.out.println(mapping);
//
//        String errorMessage = TranslateErrorEnum2.SYSTEM_ERROR.getErrorMessage();
//        System.out.println(errorMessage);
    }

}
