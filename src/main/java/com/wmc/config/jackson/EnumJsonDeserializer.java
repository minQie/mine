package com.wmc.config.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.wmc.common.enums.MyEnumInterface;
import com.wmc.common.exception.ApiErrorCodes;
import com.wmc.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 自定义枚举反序列化器
 * 无论传参类型是Number，还是String，都会根据“ValueEnum.getValue()”方法的返回值进行反序列化
 *
 * 1、可以通过复写FtEnum的“getDefault”方法，来指定反序列化依据的“value”值无法正确反序列化时，返回一个默认的枚举值（默认抛出异常）<p>
 * 2、支持@JsonProperty注解<p>
 * <p>
 * 缺陷：<p>
 * 实际JsonDeserializer的泛型写Enum、MyEnumInterface都行，因为当前反序列化器在框架解析起作用，不是根据类型（所以解析流程中需要对真实类型进行校验）<p>
 * 强调，没有真正意义上完美支持自定义反序列化枚举方式，实际中，参数实体中支持的枚举相关类型：<code>Enum<? extends MyEnumInterface></code>和<code>Collection<Enum<? extends MyEnumInterface>></code>
 *
 * @see MyEnumInterface#getDefault()
 * @see EnumJsonDeserializerCoreHandler#getAppropriateDeserializeField(Class, String)
 *
 * @author 王敏聪
 * @date 2020-01-16 09:21:14
 */
@Deprecated
@Slf4j
public class EnumJsonDeserializer extends JsonDeserializer<Enum<? extends MyEnumInterface>> {

    /**
     * 1、这里无法控制传参为null时的反序列化行为（为null时，在回调当前反序列化器的该方法之前的其他反序列化器就已经结束了）
     * 2、代码能够执行到该方法，要反序列化的对象肯定实现了当前接口（@JsonSerialize方式）
     * 3、代码逻辑保证了语法的正确性，所有"unchecked cast"警告可以忽略
     */
    @Override
    public Enum<? extends MyEnumInterface> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        // 1、序列化枚举的参数，无论是String，int统一转成int处理
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int value = node.asInt();

        // 2、获取要反序列化的类型（目前只支持这两种：Enum<? extends MyEnumInterface>、Collection<Enum<? extends MyEnumInterface>>）
        Type realType = EnumJsonDeserializerCoreHandler.getRealEnumInvolvedType(jsonParser);
        String className = realType.getTypeName();

        @SuppressWarnings("unchecked")
        Class<MyEnumInterface> myEnumClass = (Class<MyEnumInterface>) realType;

        // 3、校验真实类型是否是枚举
        if (!myEnumClass.isEnum()) {
            log.error("反序列化失败 - MyEnumInterface修饰的[{}]不是一个枚举类", className);
            throw new ApiException(ApiErrorCodes.ENUM_DESERIALIZE_FAIL, "请通知相应的后台开发人员");
        }

        @SuppressWarnings("unchecked")
        Class<Enum<? extends MyEnumInterface>> realEnumClass = (Class<Enum<? extends MyEnumInterface>>) realType;
        Enum<? extends MyEnumInterface>[] enumConstants = realEnumClass.getEnumConstants();

        // 4、校验枚举中是否了枚举常量
        if (enumConstants.length == 0) {
            log.error("反序列化失败 - [{}]中没有定义任何枚举常量", className);
            throw new ApiException(ApiErrorCodes.ENUM_DESERIALIZE_FAIL, "请通知相应的后台开发人员");
        }

        // 5、根据“getValue()”反序列化
        for (Enum<? extends MyEnumInterface> myEnum : enumConstants) {
            if (((MyEnumInterface) myEnum).getValue() == value) {
                return myEnum;
            }
        }

        // 无匹配项结果处理
        log.debug("反序列化失败 - 参数非法，枚举[{}]可用值：[{}]，实际参数：[{}]", className, Arrays.stream(enumConstants).map(myEnum -> ((MyEnumInterface)myEnum).getValue()).collect(Collectors.toList()), value);
        return ((MyEnumInterface) enumConstants[0]).getDefault();
    }

}
