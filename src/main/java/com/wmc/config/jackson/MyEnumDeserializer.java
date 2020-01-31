package com.wmc.config.jackson;

import cn.firstouch.web.api.ApiException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.wmc.common.enums.MyEnumInterface;
import ft.firstouch.common.enums.FtEnum;
import ft.firstouch.common.utils.RegexUtils;
import ft.firstouch.config.ApiErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 自定义枚举反序列化器
 *
 * <ul>
 * <li>含有@JsonProperty注解修饰的字段，其反序列化依据的字段名就是@JsonProperty.value()（Jackson默认）
 * <li>不需要拓展getDefault，@JsonEnumDefaultValue注解修饰的方法就具备这个行为（Jackson默认）
 * </ul>
 *
 * 问题：是枚举就序列化（即使你加了枚举的泛型也没用，Jackson不关注枚举泛型类型）
 * 所以Json层面传参产生了一个硬性要求：接口参数中如果有枚举，这个枚举必须实现自定义的枚举接口“FtEnum”
 *
 * @author 王敏聪
 * @date 2020-01-17 15:25:39
 */
@Slf4j
@JsonComponent
public class MyEnumDeserializer extends JsonDeserializer<Enum> {

    @Override
    @SuppressWarnings("unchecked")
    public Enum deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
        String currentName = jp.getCurrentName();
        Class currentClass = jp.getCurrentValue().getClass();
        Type propertyType = BeanUtils.findPropertyType(currentName, currentClass);
        // 获取枚举字节码对象 - 下面这个类型转换，不会对枚举的泛型进行校验
        Class<Enum> enumClass = (Class<Enum>) propertyType;
        if (!MyEnumInterface.class.isAssignableFrom(enumClass)) {
            log.warn("反序列化失败 - 枚举：{}，没有实现FtEnum接口，无法被正确反序列化", enumClass.getName());
            throw new ApiException(ApiErrorCodes.ENUM_DESERIALIZE_FAIL);
        }
        Class<Enum<? extends FtEnum>> ftEnumClass = (Class<Enum<? extends FtEnum>>) propertyType;

        // 需要参数是否为整数格式进行校验，JsonNode.asInt非数字的格式会被，默认转为0
        JsonNode node = jp.getCodec().readTree(jp);
        String valueString = node.asText();
        Enum<? extends FtEnum>[] enumConstants = ftEnumClass.getEnumConstants();
        if (!RegexUtils.isInteger(valueString)) {
            return ((FtEnum) enumConstants[0]).getDefault();
        }
        int value = node.asInt();
        for (Enum<? extends FtEnum> ftEnum : enumConstants) {
            if (((FtEnum) ftEnum).getValue() == value) {
                return ftEnum;
            }
        }

        // 没有匹配到相应的枚举项
        log.debug("反序列化失败 - 类型：{}，参数：{}", ftEnumClass.getName(), value);
        return ((FtEnum) enumConstants[0]).getDefault();
    }

}
