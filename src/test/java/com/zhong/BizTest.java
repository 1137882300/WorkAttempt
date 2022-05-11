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

}
