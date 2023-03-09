package com.zhong;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @date 2022/9/20 18:03
 */
public class RegularTest {

    //过滤特殊字符
    private String stringFilter(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    @Test
    public void test() {

        String s = stringFilter("!a!s#d$c^");
        System.out.println(s);
    }


}
