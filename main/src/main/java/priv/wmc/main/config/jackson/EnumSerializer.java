package priv.wmc.main.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import priv.wmc.main.base.enums.EnumDefine;
import priv.wmc.common.constant.StaticConstants;

import java.io.IOException;

/**
 * 自定义枚举序列化器（只针对{@link EnumDefine}接口，不限枚举类型）
 * <ul>
 * <li>枚举为空 -> null</li>
 * <li>枚举不为空 -> {"键名": {@link EnumDefine#getValue()}, "值名": {@link EnumDefine#getDescription()}}</li>
 * </ul>
 *
 * 问题1：是枚举就序列化
 * 问题2：泛型类型为“Enum<? extends EnumDefine>”，项目启动时会被莫名执行一次（EnumDefine就不会）
 *
 * @author 王敏聪
 * @date 2020/1/15 22:24
 *
 * @see StaticConstants
 */
@JsonComponent
public class EnumSerializer extends JsonSerializer<EnumDefine> {

    @Override
    public void serialize(EnumDefine enumDefine, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (enumDefine == null) {
            return;
        }
        gen.writeStartObject();
        gen.writeNumberField(StaticConstants.ENUM_SERIALIZE_KEY_NAME, enumDefine.getValue());
        gen.writeStringField(StaticConstants.ENUM_SERIALIZE_VALUE_NAME, enumDefine.getDescription());
        gen.writeEndObject();
        // 不能调用gen.close，否则会导致参数实体中接下来的其他字段序列化时，发生写json的键值次数错误
    }
}