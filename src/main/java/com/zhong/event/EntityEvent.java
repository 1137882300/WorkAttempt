package com.zhong.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @date 2022/9/9 15:27
 */
public class EntityEvent extends ApplicationEvent {


    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    public EntityEvent(Object source, Long id, String name) {
        super(source);
        this.id = id;
        this.name = name;
    }
}
