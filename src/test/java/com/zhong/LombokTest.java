package com.zhong;

import com.zhong.entity.AreaInfoPO;
import org.junit.Test;

/**
 * @date 2022/7/26 11:15
 */
public class LombokTest {

    /**
     * build测试
     */
    @Test
    public void buildTest() {
        AreaInfoPO.AreaInfoPOBuilder areaInfoPOBuilder = AreaInfoPO.builder();
        areaInfoPOBuilder
                .areaLevel(1)
                .countryId(22L)
                .parentId(23L)
                .features("ceshi");

        AreaInfoPO build = areaInfoPOBuilder.build();
        System.out.println(build.getFeatures());
        System.out.println(build);
    }

}
