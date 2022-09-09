package com.zhong.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.Resource;

/**
 * @date 2022/9/9 15:32
 */
public class Use {

    private ApplicationEventPublisher applicationEventPublisher;

    private Use() {
        applicationEventPublisher = new ApplicationEventPublisher() {
            @Override
            public void publishEvent(ApplicationEvent applicationEvent) {

            }
        };
    }

    public void useEvent() {
        applicationEventPublisher.publishEvent(new EntityEvent(this, 11L, "jex"));


    }

    public static void main(String[] args) {
        Use u = new Use();
        u.useEvent();
    }

}
