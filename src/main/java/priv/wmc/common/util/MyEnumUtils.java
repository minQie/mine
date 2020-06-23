package priv.wmc.common.util;

import java.util.Arrays;
import java.util.stream.Collectors;
import priv.wmc.common.enums.MyEnumInterface;

/**
 * @author 王敏聪
 * @date 2020-04-28 21:24:43
 */
public final class MyEnumUtils {

    /**
     * 指定类是否是枚举类且实现{@link priv.wmc.common.enums.MyEnumInterface}
     */
    public static boolean isMyEnum(Class<?> clazz) {
        return clazz.isEnum() && MyEnumInterface.class.isAssignableFrom(clazz);
    }

    /**
     * 获取FtEnum枚举类的Swagger文档描述
     */
    public static String getDescription(Class<?> clazz) {
        MyEnumInterface[] values = (MyEnumInterface[]) clazz.getEnumConstants();
        return "（" +
            Arrays.stream(values)
                .map(myEnum -> myEnum.getValue() + "：" + myEnum.getDescription())
                .collect(Collectors.joining("，"))
            + "）";
    }

}
