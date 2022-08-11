package com.zhong.ding.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/20 19:08
 */
@Data
public class Entity extends BaseRowModel {


    @ExcelProperty(value = "第1列", index = 0)
    private String column1;

    @ExcelProperty(value = "第2列", index = 1)
    private String column2;

    @ExcelProperty(value = "第3列", index = 2)
    private String column3;

    @ExcelProperty(value = "第4列", index = 3)
    private String column4;

    @ExcelProperty(value = "第5列", index = 4)
    private String column5;

    @ExcelProperty(value = "第6列", index = 5)
    private String column6;

    @ExcelProperty(value = "第7列", index = 6)
    private String column7;

    @ExcelProperty(value = "第8列", index = 7)
    private String column8;

    @ExcelProperty(value = "第9列", index = 8)
    private String column9;

    @ExcelIgnore
    private Long Id;
}