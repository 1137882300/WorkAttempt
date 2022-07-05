package com.zhong.requestVerify;

import com.google.common.collect.Lists;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @date 2022/7/5 11:35
 * <p>
 * DefaultGroupSequenceProvider来实现多字段联合校验
 */
public class BizGroupSequenceProvider implements DefaultGroupSequenceProvider<BeanExample> {

    @Override
    public List<Class<?>> getValidationGroups(BeanExample beanExample) {

        ArrayList<Class<?>> defaultGroupSequence = Lists.newArrayList(BeanExample.class); //这里增加默认处理器是必须的,不然Default分组都不会执行了，会抛错的

        if (Objects.isNull(beanExample)) {
            return defaultGroupSequence;
        }
        //这里可以自定义规则想要加哪些组进行校验
        if (beanExample.getNum() == 10) {
            defaultGroupSequence.add(BeanExample.VerifyName.class);
        } else {
            defaultGroupSequence.add(BeanExample.VerifyDesc.class);
        }
        if (Objects.nonNull(beanExample.getSize())) {
            defaultGroupSequence.add(BeanExample.VerifySize.class);
        }
        return defaultGroupSequence;
    }
}
