package com.zhong.enums;

import org.apache.commons.lang3.LocaleUtils;

import java.util.List;
import java.util.Locale;

/**
 * @date 2022/7/21 10:07
 */
public class Test {

    public static void main(String[] args) {
        String string = Locale.CHINA.toString();
        String language = Locale.CHINA.getLanguage();
        System.out.println(language);
        System.out.println(string);
        Locale locale = LocaleUtils.toLocale(Locale.CHINA.getLanguage());
        System.out.println(locale);

//        int errorCode = TranslateErrorEnum2.SYSTEM_ERROR.getErrorCode();
//        System.out.println(errorCode);
//
//        List<String> mapping = TranslateErrorEnum2.SYSTEM_ERROR.mapping();
//        System.out.println(mapping);
//
//        String errorMessage = TranslateErrorEnum2.SYSTEM_ERROR.getErrorMessage();
//        System.out.println(errorMessage);
    }

}
