package priv.wmc.config.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;
import priv.wmc.common.enums.MyEnumInterface;
import priv.wmc.common.exception.ApiErrorCodes;
import priv.wmc.common.exception.ApiException;
import priv.wmc.common.util.RegexUtils;

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
 * @see EnumDeserializerCoreHandler#getAppropriateDeserializeField(Class, String)
 *
 * @author 王敏聪
 * @date 2020-01-16 09:21:14
 */
@Slf4j
@JsonComponent
public class EnumDeserializer extends JsonDeserializer<Enum> {

    /**
     * 1、这里无法控制传参为null时的反序列化行为（为null时，在回调当前反序列化器的该方法之前的其他反序列化器就已经结束了）
     * 2、代码能够执行到该方法，要反序列化的对象肯定实现了当前接口（@JsonSerialize方式）
     * 3、代码逻辑保证了语法的正确性，所有"unchecked cast"警告可以忽略
     */
    @Override
    public Enum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        // 1、获取要反序列化的类型（目前只支持这两种：Enum<? extends MyEnumInterface>、Collection<Enum<? extends MyEnumInterface>>）
        Type realType = EnumDeserializerCoreHandler.getRealEnumInvolvedType(jsonParser);
        String className = realType.getTypeName();

        // 获取枚举字节码对象 - 下面这个类型转换，不会对枚举的泛型进行校验
        @SuppressWarnings("unchecked")
        Class<Enum> myEnumClass = (Class<Enum>) realType;

        // 2、校验枚举类是否实现了MyEnumInterface接口
        if (!MyEnumInterface.class.isAssignableFrom(myEnumClass)) {
            log.warn("反序列化失败 - 枚举：{}，没有实现myEnum接口，无法被正确反序列化", myEnumClass.getName());
            throw new ApiException(ApiErrorCodes.ENUM_DESERIALIZE_FAIL);
        }

        @SuppressWarnings("unchecked")
        Class<Enum<? extends MyEnumInterface>> realEnumClass = (Class<Enum<? extends MyEnumInterface>>) realType;
        Enum<? extends MyEnumInterface>[] enumConstants = realEnumClass.getEnumConstants();

        // 3、校验枚举中是否定义了枚举常量
        if (enumConstants.length == 0) {
            log.error("反序列化失败 - [{}]中没有定义任何枚举常量", className);
            throw new ApiException(ApiErrorCodes.ENUM_DESERIALIZE_FAIL, "请通知相应的后台开发人员");
        }

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        // 4、校验参数的格式，JsonNode.asInt非数字的格式会被，默认转为0
        String valueString = node.asText();
        if (!RegexUtils.isInteger(valueString)) {
            log.debug("反序列化失败 - 参数非法，枚举[{}]可用值：[{}]，实际参数：[{}]", className, Arrays.stream(enumConstants).map(myEnum -> ((MyEnumInterface)myEnum).getValue()).collect(Collectors.toList()), valueString);
            return ((MyEnumInterface) enumConstants[0]).getDefault();
        }

        // 5、根据“getValue()”反序列化
        int value = node.asInt();
        for (Enum<? extends MyEnumInterface> myEnum : enumConstants) {
            if (((MyEnumInterface) myEnum).getValue() == value) {
                return myEnum;
            }
        }

        // 6、无匹配项结果处理
        log.debug("反序列化失败 - 参数非法，枚举[{}]可用值：[{}]，实际参数：[{}]", className, Arrays.stream(enumConstants).map(myEnum -> ((MyEnumInterface)myEnum).getValue()).collect(Collectors.toList()), value);
        return ((MyEnumInterface) enumConstants[0]).getDefault();
    }

}
