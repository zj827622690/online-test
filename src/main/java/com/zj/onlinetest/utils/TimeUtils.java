package com.zj.onlinetest.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @Auther: zj
 * @Date: 2019/4/17 10:49
 * @Description: 时间相关的操作(线程安全)
 */
public final class TimeUtils  {

    /**
     * 获取当前时间，精确到毫秒
     * @return
     */
    public static Long getNow() {
        Long millisecond = Instant.now().toEpochMilli();  // 获取当前时间，精确到毫秒
        return millisecond;
    }

    /**
     * 将时间戳转化成字符串，传入时间戳须为毫秒
     * @param millisecond
     * @return
     */
    public static String timeToString(Long millisecond) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return dateTimeFormatter.format( LocalDateTime.ofInstant(Instant.ofEpochMilli(millisecond), ZoneId.systemDefault()));
    }
    /**
     * 将字符串转化成时间戳,转换后时间戳为毫秒(慎用)
     * @param time
     * @return
     */
    public static Long convertTimeToLong(String time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse(time, dateTimeFormatter);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


}
