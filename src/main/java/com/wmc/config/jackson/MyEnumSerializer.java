package com.wmc.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.wmc.common.enums.MyEnumInterface;
import com.wmc.config.StaticConfig;

import java.io.IOException;

/**
 * 自定义枚举序列化器（只针对{@link MyEnumInterface}接口，不限枚举类型）
 * <ul>
 * <li>枚举为空 -> null</li>
 * <li>枚举不为空 -> {"键名": {@link MyEnumInterface#getValue()}, "值名": {@link MyEnumInterface#getDescription()}}</li>
 * </ul>
 *
 * 问题1：是枚举就序列化
 * 问题2：泛型类型为“Enum<? extends FtEnum>”，项目启动时会被莫名执行一次（FtEnum就不会）
 *
 * @author 王敏聪
 * @date 2020/1/15 22:24
 *
 * @see StaticConfig
 */
public class MyEnumSerializer extends JsonSerializer<MyEnumInterface> {

    @Override
    public void serialize(MyEnumInterface myEnumInterface, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (myEnumInterface == null) {
            return;
        }
        gen.writeStartObject();
        gen.writeNumberField(StaticConfig.ENUM_SERIALIZE_KEY_NAME, myEnumInterface.getValue());
        gen.writeStringField(StaticConfig.ENUM_SERIALIZE_VALUE_NAME, myEnumInterface.getDescription());
        gen.writeEndObject();
        // 不能调用gen.close，否则会导致参数实体中接下来的其他字段序列化时，发生写json的键值次数错误
    }
}