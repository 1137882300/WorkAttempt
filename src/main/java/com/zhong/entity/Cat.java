package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/15 16:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cat implements Serializable {


    private static final long serialVersionUID = 1L;

    private int id;

    private MultiLanguageString unitName;

    private int state;

}
