package com.zhong;

import com.zhong.entity.MM;
import com.zhong.entity.MultiLanguageString;
import org.junit.Test;

/**
 * @date 2022/5/10 18:32
 */
public class BizTest {

    @Test
    public void muti() {
        MM m = new MM();

        MultiLanguageString name = m.getName();
        System.out.println(name.getAllLanguageString());//NullPointerException
        System.out.println(name.getLanguageStringMap());//NullPointerException
    }

    public static String concatUrl(String a, String b) {
        if (a.endsWith("/")) {
            a = a.substring(0, a.length() - 1);
        }
        if (b.startsWith("/")) {
            b = b.substring(1);
        }
        return a + "/" + b;
    }

    @Test
    public void ss(){
        String s = concatUrl("aaa/ccc/eee/", "eee/ttt/uuu/");
        System.out.println(s);//:    aaa/ccc/eee/eee/ttt/uuu/
    }

}
