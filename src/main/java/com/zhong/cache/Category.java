package com.zhong.cache;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/27 14:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category implements Serializable {

    private Long rootId;

    private CategoryEntity categoryTree;

    private Long version;


}
