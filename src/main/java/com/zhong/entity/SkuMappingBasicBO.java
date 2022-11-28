package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuMappingBasicBO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * mapping id
     */
    private Long channelId;
}
