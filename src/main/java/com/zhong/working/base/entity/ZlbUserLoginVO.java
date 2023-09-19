package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: juzi
 * @date: 2023/4/21
 * @desc: 浙里办用户信息
 */
@Data
public class ZlbUserLoginVO {

    private String token;
    private String tokenType;

    @ApiModelProperty("浙里办的用户ID")
    private String externalUserId;

    @ApiModelProperty("是否新用户:[true:新人]")
    private Boolean newcomer;

    @ApiModelProperty("用户信息")
    private UserInfoDTO userInfo;

    @Data
    public static class UserInfoDTO {
        @ApiModelProperty("用户id")
        private Integer userId;
        @ApiModelProperty("用户昵称")
        private String nickname;
        @ApiModelProperty("手机号")
        private String phone;
    }
}
