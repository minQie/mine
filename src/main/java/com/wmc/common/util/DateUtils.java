package com.wmc.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author 王敏聪
 * @date 2019/11/15 11:46
 */
public class DateUtils {

    public static final String YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd";
    @SuppressWarnings("WeakerAccess")
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getDateString(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String getDateString(Date date) {
        return getDateString(date, DEFAULT_FORMAT);
    }

    @SuppressWarnings("WeakerAccess")
    public static String getDateString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

}
