package com.zhong.excel;

import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2022/1/21 10:50
 */
@Data
public class WriteEntity extends BaseRowModel {

    private String name;

    private String code;

    private Long money;


}
