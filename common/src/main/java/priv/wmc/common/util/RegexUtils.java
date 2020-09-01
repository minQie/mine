package priv.wmc.common.util;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author 王敏聪
 * @date 2020-01-17 18:06:17
 */
public final class RegexUtils {

    private RegexUtils() {}

    public static final String INTEGER_REGEX = "^-?[0-9]|-?[1-9][0-9]*$";
    public static final Pattern INTEGER_PATTERN = Pattern.compile(INTEGER_REGEX);

    /**
     * 是否是正整数
     */
    public static boolean isInteger(String integerString) {
        return INTEGER_PATTERN.matcher(integerString).matches();
    }
}
