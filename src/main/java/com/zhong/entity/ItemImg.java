package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @date 2022/5/6 18:04
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ItemImg {

    private Long itemId;

    private Long shopId;

    private Integer isMaster;

    private String imageUrl;

    private Integer imageOrder;

    private Integer type;

}
