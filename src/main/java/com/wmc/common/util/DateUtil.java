package com.wmc.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author 王敏聪
 * @date 2019/11/15 11:46
 */
public class DateUtil {

    private final static String YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd";

    public static String getTodayYearMonthDayDateString() {
        return new SimpleDateFormat(YEAR_MONTH_DAY_FORMAT).format(new Date());
    }

}
