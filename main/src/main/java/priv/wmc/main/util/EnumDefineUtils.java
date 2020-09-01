package priv.wmc.main.util;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import priv.wmc.main.base.enums.EnumDefine;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.AllowableValues;

/**
 * @author 王敏聪
 * @date 2020-04-28 21:24:43
 */
public final class  EnumDefineUtils {

    private EnumDefineUtils() {}

    /**
     * 指定类是否是枚举类且实现{@link EnumDefine}
     */
    public static boolean isEnumDefine(Class<?> clazz) {
        return clazz.isEnum() && EnumDefine.class.isAssignableFrom(clazz);
    }

    /**
     * 指定类是否是枚举类且实现{@link EnumDefine}
     */
    public static boolean isEnumDefine(Object obj) {
        return obj instanceof Enum && obj instanceof EnumDefine;
    }

    public static boolean isEnumDefine(EnumDefine obj) {
        return obj instanceof Enum;
    }

    public static boolean isEnumDefine(Enum<?> e) {
        return e instanceof EnumDefine;
    }

    /**
     * 获取EnumDefine枚举类的Swagger文档描述
     */
    public static <T extends Enum<T> & EnumDefine> String getDescription(Class<T> clazz) {
        EnumDefine[] values = clazz.getEnumConstants();
        return "（" +
            Arrays.stream(values)
                .map(myEnum -> myEnum.getValue() + "：" + myEnum.getDescription())
                .collect(Collectors.joining("，"))
            + "）";
    }

    public static List<String> getEnumDescriptions(Class<?> type) {
        if (!type.isEnum()) {
            throw new IllegalArgumentException("老铁哟，不是枚举类型哟");
        }
        Function<Object, String> function = !EnumDefineUtils.isEnumDefine(type)
            ? Object::toString
            : e -> ((EnumDefine)e).getValue() + ":" + ((EnumDefine)e).getDescription();

        return Stream.of(type.getEnumConstants())
            .map(function)
            .collect(toList());
    }

    public static AllowableValues allowableValues(Class<?> type) {
        if (!type.isEnum()) {
            throw new IllegalArgumentException("老铁哟，不是枚举类型哟");
        }

        List<String> enumValues = getEnumDescriptions(type);

        return new AllowableListValues(enumValues, "LIST");
    }

}
