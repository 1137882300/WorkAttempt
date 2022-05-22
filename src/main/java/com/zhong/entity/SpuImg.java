package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.LocaleUtils;

/**
 * @date 2022/4/14 20:03
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SpuImg {

    private Long spuId;

    private Integer isMaster;

    private String imageUrl;

    private Integer imageOrder;

    private Integer type;


}
