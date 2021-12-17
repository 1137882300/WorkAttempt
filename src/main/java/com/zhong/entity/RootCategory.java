package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/17 14:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RootCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    Long rootId;

    Category categoryTree;

}
