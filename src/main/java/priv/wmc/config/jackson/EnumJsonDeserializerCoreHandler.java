package priv.wmc.config.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import priv.wmc.common.exception.ApiErrorCodes;
import priv.wmc.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * 枚举反序列化核心处理器
 *
 * @author 王敏聪
 * @date 2020-01-16 09:24:39
 */
@Deprecated
@Slf4j
public class EnumJsonDeserializerCoreHandler {

    /**
     * 从jsonParser的反序列化上下文中获取要反序列化的字段类型
     */
    public static Type getRealEnumInvolvedType(JsonParser jsonParser) throws IOException {
        // 调试发现，枚举类型没有被集合或者其他容器修饰时，字段名称可以直接通过“getCurrentName”方法获取到，否则为空，故由该规律得出下面的逻辑

        // 找到的字段的类型是：Enum<? extends MyEnumInterface>
        if (jsonParser.getCurrentName() != null) {
            Class<?> formClass = jsonParser.getCurrentValue().getClass();
            String fieldName = jsonParser.getCurrentName();

            Field field = getAppropriateDeserializeField(formClass, fieldName);

            return field.getType();

        // 找到的字段类型是：Collection<Enum<? extends MyEnumInterface>>
        } else {
            JsonStreamContext parsingContext = jsonParser.getParsingContext().getParent();
            Class<?> formClass = parsingContext.getCurrentValue().getClass();
            String fieldName = parsingContext.getCurrentName();

            Field field = getAppropriateDeserializeField(formClass, fieldName);

            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            return parameterizedType.getActualTypeArguments()[0];
        }
    }

    /**
     * 在字节码对象中根据字段名称获取要被反序列化的字段，没有找到则抛出异常
     *
     * @param clazz 字节码对象
     * @param fieldName 字段名
     * @return 字段
     * @exception ApiException 没有找到
     */
    private static Field getAppropriateDeserializeField(Class clazz, String fieldName) throws ApiException {
        // 1、根据 字段名 查找字段
        Field collectionField = ReflectionUtils.findField(clazz, fieldName);
        if (collectionField != null) {
            return collectionField;
        }
        // 2、根据 @JsonProperty.value 值查找字段
        List<Field> fieldsListWithJsonProperty = FieldUtils.getFieldsListWithAnnotation(clazz, JsonProperty.class);
        Optional<Field> fieldOptional = fieldsListWithJsonProperty
                .stream()
                .filter(field1 -> field1.getAnnotation(JsonProperty.class).value().equals(fieldName))
                .findAny();

        // 找到
        if (fieldOptional.isPresent()) {
            return fieldOptional.get();
        }
        // 没有找到
        log.error("反序列化失败 - 无法定位到字段（类型：Collection<Enum<? extends MyEnumInterface>>，字段名：{}）", fieldName);
        throw new ApiException(ApiErrorCodes.ENUM_DESERIALIZE_FAIL, "请通知相应的后台开发人员");
    }

}
