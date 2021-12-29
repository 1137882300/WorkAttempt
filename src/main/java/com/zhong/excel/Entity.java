package com.zhong.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/20 19:08
 */
@Data
public class Entity extends BaseRowModel {


    @ExcelProperty(value = "一级分类", index = 0)
    private String oneCn;

    @ExcelProperty(value = "The primary category", index = 1)
    private String oneEn;

    @ExcelProperty(value = "印尼语", index = 2)
    private String oneId;

    @ExcelProperty(value = "二级分类名称", index = 3)
    private String twoCn;

    @ExcelProperty(value = "The secondary category", index = 4)
    private String twoEn;

    @ExcelProperty(value = "印尼语2", index = 5)
    private String twoId;

    @ExcelProperty(value = "三级分类名称", index = 6)
    private String threeCn;

    @ExcelProperty(value = "The thirdly category", index = 7)
    private String threeEn;

    @ExcelProperty(value = "印尼语3", index = 8)
    private String threeId;
}
