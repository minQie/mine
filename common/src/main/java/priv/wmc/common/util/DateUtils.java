package priv.wmc.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import priv.wmc.common.constant.StaticConstants;

/**
 * 时间工具类
 *
 * @author 王敏聪
 * @date 2019/11/15 11:46
 */
public final class DateUtils {

    private DateUtils() {}

    public static String getCurrentDateTime() {
        return new SimpleDateFormat(StaticConstants.DATE_TIME_PATTERN).format(new Date());
    }

    public static String getCurrent(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String getDateTime(Date date) {
        return get(date, StaticConstants.DATE_TIME_PATTERN);
    }

    public static String get(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

}
