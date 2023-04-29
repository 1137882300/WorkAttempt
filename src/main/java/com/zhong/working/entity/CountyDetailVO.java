package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-14
 **/
@Data
public class CountyDetailVO {
    @ApiModelProperty("天气")
    private WeatherVO weather;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("背景图片")
    private String image;
    @ApiModelProperty("描述")
    private String desc;
//    @ApiModelProperty("推荐内容列表")
//    private List<FlowIndexVO> recommendContentList;
    @ApiModelProperty("内容主题列表（弃用）")
    private List<CountyDetailThemeDTO> themeList;
    @ApiModelProperty("推荐景区列表")
    private List<CountyDetailScenicDTO> recommendScenicList;

    @Data
    public static class WeatherVO {
        @ApiModelProperty("相对湿度")
        private String humidity;
        @ApiModelProperty("风向描述")
        private String winddirection;
        @ApiModelProperty("风力级别，单位：级")
        private String windpower;
        @ApiModelProperty("实时气温，单位：摄氏度")
        private String temperature;
        @ApiModelProperty("天气现象（汉字描述）")
        private String weather;
        @ApiModelProperty("数据发布的时间")
        private String reporttime;
    }
    @Data
    public static class CountyDetailScenicDTO {
        private Integer id;
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("副标题")
        private String subTitle;
        @ApiModelProperty("图片")
        private String image;
        @ApiModelProperty("地址")
        private String address;
        @ApiModelProperty("星级 1：A 2：AA 3：AAA 4：AAAA 5：AAAAA")
        private Integer star;
    }

    @Data
    public static class CountyDetailThemeDTO {
        private Integer id;
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("副标题")
        private String subTitle;
//        @ApiModelProperty("攻略")
//        private List<CountyStrategyDTO> list;
    }

    @Data
    public static class CountyStrategyDTO {
        private Integer id;
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("图片")
        private String image;
        @ApiModelProperty("作者")
        private String author;
        @ApiModelProperty("头像")
        private String avatar;
//        @ApiModelProperty("坐标地点")
//        private PointVO point;
//        @ApiModelProperty("用户喜好")
//        private UserLikeVO userLike;
//        @ApiModelProperty("统计数量")
//        private StatInfoVO statInfo;
    }
}
