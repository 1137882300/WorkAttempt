package com.zhong.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * The type Date util.
 *
 * @author hjh
 * @version 2022 /1/24-22:19
 */
public class DateUtil {
    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取东8区毫秒时间戳
     *
     * @param localDateTime 日期
     * @return long long
     */
    public static Long toEpochMilliZone8(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public static Long toEpochMilliZone8(String localDateTime) {
        if (StringUtils.isBlank(localDateTime)) {
            return null;
        }
        LocalDateTime parse = LocalDateTime.parse(localDateTime, dateTimeFormatter);
        return toEpochMilliZone8(parse);
    }
}
