package com.wmc.config.converter;

import com.wmc.common.enums.MyEnumInterface;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author 王敏聪
 * @date 2020-01-21 23:05:38
 */
public class EnumConverterFactory implements ConverterFactory<String, MyEnumInterface> {

    @Override
    public <T extends MyEnumInterface> Converter<String, T> getConverter(Class<T> targetType) {
        return new ValueEnumConverter<T>(targetType);
    }

    class ValueEnumConverter<T extends MyEnumInterface> implements Converter<String, T> {
        private final Class<T> type;

        public ValueEnumConverter(Class<T> type) {
            if (type == null) {
                throw new IllegalArgumentException("Type argument cannot be null");
            }
            this.type = type;
        }

        @Override
        public T convert(String source) {
            if (source == null) {
                throw new IllegalArgumentException("Value cannot be null");
            }
            int value;
            try {
                value = Integer.parseInt(source);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Value error");
            }
            T[] enumConstants = type.getEnumConstants();
            for (T t : enumConstants) {
                if (t.getValue() == value) {
                    return t;
                }
            }
            throw new IllegalArgumentException("Value not matches");
        }
    }

}

