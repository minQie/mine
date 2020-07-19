package priv.wmc.common.utils;

import java.util.Arrays;
import java.util.stream.Collectors;
import priv.wmc.common.enums.EnumDefine;

/**
 * @author 王敏聪
 * @date 2020-04-28 21:24:43
 */
public final class EnumUtils {

    /**
     * 指定类是否是枚举类且实现{@link EnumDefine}
     */
    public static boolean isMyEnum(Class<?> clazz) {
        return clazz.isEnum() && EnumDefine.class.isAssignableFrom(clazz);
    }

    /**
     * 获取EnumDefine枚举类的Swagger文档描述
     */
    public static String getDescription(Class<?> clazz) {
        EnumDefine[] values = (EnumDefine[]) clazz.getEnumConstants();
        return "（" +
            Arrays.stream(values)
                .map(myEnum -> myEnum.getValue() + "：" + myEnum.getDescription())
                .collect(Collectors.joining("，"))
            + "）";
    }

}
