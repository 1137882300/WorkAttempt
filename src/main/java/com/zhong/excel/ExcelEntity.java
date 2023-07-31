package com.zhong.excel;

import lombok.Data;

/**
 * @author: juzi
 * @date: 2023/7/31
 * @desc:
 */
@Data
public class ExcelEntity {

    @ExcelColumn("id")
    private Double column1;

    @ExcelColumn("content")
    private String column2;

}
