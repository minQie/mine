package priv.wmc.common.constant;

/**
 * 全局静态常量配置
 *
 * @author 王敏聪
 * @date 2020-01-16 00:11:09
 */
public final class StaticConstants {

    private StaticConstants() {}

    /** 自定义枚举序列化规则的 键名 */
    public static final String ENUM_SERIALIZE_KEY_NAME = "key";

    /** 自定义枚举序列化规则的 值名 */
    public static final String ENUM_SERIALIZE_VALUE_NAME = "value";

    /** 日期规则 */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /** 时间规则 */
    public static final String TIME_PATTERN = "HH:mm:ss";

    /** 日期时间规则 */
    public static final String DATE_TIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    /** 默认时区 */
    public static final String TIMEZONE = "Asia/Shanghai";

}
