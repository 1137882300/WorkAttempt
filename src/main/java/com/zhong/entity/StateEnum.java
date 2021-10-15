package com.zhong.entity;

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

    public StateEnum getByCode(int code){
        for (StateEnum state : StateEnum.values()){
            if (state.code == code){
                return state;
            }
        }
        return null;
    }



}
