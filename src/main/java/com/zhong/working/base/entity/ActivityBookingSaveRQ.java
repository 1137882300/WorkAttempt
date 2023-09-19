package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author: 竹阳
 * @date: 2022-09-23
 **/
@Data
public class ActivityBookingSaveRQ  {
    @ApiModelProperty("用于防重，由前端生成")
    @NotBlank(message = "标识不能为空")
    private String uuid;
    @ApiModelProperty(value = "活动id", required = true)
    private Integer id;
    @ApiModelProperty("联系人姓名")
    @NotBlank(message = "联系人姓名不能为空")
    private String name;
    @ApiModelProperty("联系人手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;
    @ApiModelProperty("成人数量")
    @Min(value = 0, message = "最小值0")
    @Max(value = 20, message = "最大值20")
    private Integer adult;
    @ApiModelProperty("儿童数量")
    @Min(value = 0, message = "最小值0")
    @Max(value = 20, message = "最大值20")
    private Integer child;
    @ApiModelProperty("出行人信息（加密json数组字符串）,['name':'','cardNo':'','phone':'','type':1]")
    @NotBlank(message = "出行人信息不能为空")
    private String memberList;

//    @Data
//    public static class ActivityBookingMemberRQ {
//        @ApiModelProperty("姓名")
//        private String name;
//        @ApiModelProperty("证件号")
//        private String cardNo;
//        @ApiModelProperty("手机号")
//        private String phone;
//        @ApiModelProperty("类型，1成人2儿童")
//        private Integer type;
//    }
}
