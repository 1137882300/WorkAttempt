package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 竹阳
 * @date: 2022-09-12
 **/
@Data
public class UserIndexVO {
    @ApiModelProperty("用户信息")
    private UserInfoVO userInfo;
    @ApiModelProperty("足迹数量")
    private Integer footprintNum;
    @ApiModelProperty("碳积分")
    private Long totalIntegral;

    @Data
    public static class UserInfoVO {

        @ApiModelProperty("用户ID")
        private Integer userId;
        @ApiModelProperty("昵称")
        private String nickname;
        @ApiModelProperty("头像")
        private String avatar;
        @ApiModelProperty("手机号")
        private String phone;
        @ApiModelProperty("是否达人 1：是 0：否")
        private Integer kolStatus;
    }

}
