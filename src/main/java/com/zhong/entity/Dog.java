package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/15 16:37
 */
@Data
@AllArgsConstructor
@Builder
public class Dog {

    private Integer id;

    private StateEnum state;

    private Map<String,String> dogMap;

    public Dog(Integer id,StateEnum state){

    }
    public Dog(){

    }
}
