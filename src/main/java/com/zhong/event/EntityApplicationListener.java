package com.zhong.event;

import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationListener;

/**
 * @date 2022/9/9 15:30
 */
public class EntityApplicationListener implements ApplicationListener<EntityEvent> {


    @Override
    public void onApplicationEvent(EntityEvent entityEvent) {
        System.out.println(JSON.toJSONString(entityEvent));
    }
}
