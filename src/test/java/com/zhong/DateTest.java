package com.zhong;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @author: juzi
 * @date: 2023/3/23
 */
public class DateTest {


    /**
     * @author juzi
     * @date 2023/6/21 上午 11:48
     * @description 获取当前时间：单位：秒
     */
    @Test
    public void InstantNow(){
        long epochSecond = Instant.now().getEpochSecond();
        long nano = Instant.now().getNano();
        System.out.println(epochSecond);
        System.out.println(nano);

        long epochMilli = Instant.now().toEpochMilli();
        System.out.println(epochMilli/1000);
    }

    /**
     * @author juzi
     * @date 2023/4/4 9:33
     * @description date转string, string转date
     */
    @Test
    public void convert() {

        Date dateLimit = DateUtil.date().offset(DateField.MINUTE, 30);
        //date转string
        String format = DateUtil.format(dateLimit, DatePattern.NORM_DATETIME_FORMAT);
        System.out.println(format);

        //string 转 date
        DateTime parse = DateUtil.parse(format, DatePattern.NORM_DATETIME_FORMAT);
        System.out.println(parse);

        boolean after = parse.after(new Date());
        System.out.println(after);
    }

    /**
     * @author juzi
     * @date 2023/3/23 16:34
     * @description 日期的第一天，最后一天
     */
    @Test
    public void firstAndEnd() {
        //每天 ×××
        Date startDay = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date endDay = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        //每周
        Date startWeek = Date.from(LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date endWeek = Date.from(LocalDate.now().with(DayOfWeek.SUNDAY).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        //每月
        Date startMonth = Date.from(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date endMonth = Date.from(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        //每年
        Date startYear = Date.from(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date endYear = Date.from(LocalDate.now().with(TemporalAdjusters.lastDayOfYear()).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());


        System.out.println(startMonth);
        System.out.println(endMonth);

        String format = "yyyy-MM-dd HH:mm:ss";
        String startDate = format(format, startYear);
        String endDate = format(format, endYear);
        System.out.println(startDate);
        System.out.println(endDate);

    }

    public String format(String format, Date date) {
        if (StringUtils.isBlank(format)) {
            System.out.println();
        }
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    @Test
    public void dateDiff() {
        Date date = new Date();
        LocalDate localDate = LocalDate.now();
        System.out.println(date);
        System.out.println(localDate);

        Instant s = LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Instant e = LocalDate.now().with(DayOfWeek.SUNDAY).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        LocalDateTime localDateTimes = LocalDateTime.ofInstant(s, ZoneId.systemDefault());
        LocalDateTime localDateTimee = LocalDateTime.ofInstant(e, ZoneId.systemDefault());
        System.out.println(localDateTimes);
        System.out.println(localDateTimee);

    }
}
