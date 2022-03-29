package com.zhong.entity;

import org.apache.commons.collections4.EnumerationUtils;
import org.apache.commons.lang3.EnumUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/15 16:22
 */
public enum StateEnum {

    OPEN(200, "open"),
    CLOSE(500, "close"),
    ;
    private int code;
    private String message;

    StateEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    public static StateEnum getByCode(int code){
        for (StateEnum state : StateEnum.values()){
            if (state.code == code){
                return state;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static Set<String> getStateSet(){
        return EnumUtils.getEnumList(StateEnum.class).stream().
                map(StateEnum::getMessage).collect(Collectors.toSet());
    }


}
