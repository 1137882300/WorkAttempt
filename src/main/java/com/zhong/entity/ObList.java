package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/11/26 17:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObList {

    private int id;

    List<Dog> dogList;

    MultiLanguageString fromCountries;
}
