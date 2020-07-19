package priv.wmc.common.enums;

import priv.wmc.common.exception.ApiErrorCodes;
import priv.wmc.common.exception.ApiException;
import priv.wmc.config.jackson.EnumDeserializer;
import priv.wmc.config.jackson.EnumSerializer;

/**
 * 枚举实现当前接口，会按照自定义的规则进行序列化和反序列化
 * <ul>
 * <li>序列化： {@link EnumSerializer}</li>
 * <li>反序列化：{@link EnumDeserializer}</li>
 * </ul>
 *
 * 注意：当前接口只能修饰枚举，否则反序列化流程无意义
 *
 * @author 王敏聪
 * @date 2020-01-16 00:15:33
 */
public interface EnumDefine {

    /**
     * 枚举必须返回一个整型值，作为序列化和反序列化的依据
     *
     * @return 标识
     */
    int getValue();

    /**
     * 枚举的描述字段
     *
     * @return 描述
     */
    String getDescription();

    /**
     * 反序列化枚举，而枚举常量中没有能够对应value反序列化的，则该方法的返回值作为返回结果
     *
     * @param <T> 实现当前接口的枚举
     * @return 实现当前接口的枚举
     */
    default <T extends Enum<T> & EnumDefine> Enum<T> getDefault() {
        throw new ApiException(ApiErrorCodes.ENUM_DESERIALIZE_FAIL);
    }

}
