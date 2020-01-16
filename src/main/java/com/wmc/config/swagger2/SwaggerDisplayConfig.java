package com.wmc.config.swagger2;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.wmc.common.enums.MyEnumInterface;
import com.wmc.common.verify.Verify;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ModelPropertyBuilder;
import springfox.documentation.schema.Annotations;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.schema.ApiModelProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 将swagger2的自定生成api文档功能，拓展适配当前项目的一些自定义配置以及jsr303非空
 * <p>
 * 1、FtEnum类型：正确解析
 * 2、jsr303非空支持
 *
 * @author 王敏聪
 * @date 2020-1-3 14:50:58
 */
@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)
@Configuration
@ConditionalOnProperty(value = "app.swagger-enable", havingValue = "true")
public class SwaggerDisplayConfig implements ModelPropertyBuilderPlugin {

    @SneakyThrows
    @Override
    public void apply(ModelPropertyContext context) {
        Optional<ApiModelProperty> annotationOptional = Optional.empty();
        if (context.getAnnotatedElement().isPresent()) {
            // noinspection Guava
            com.google.common.base.Optional<ApiModelProperty> apiModePropertyAnnotation = ApiModelProperties.findApiModePropertyAnnotation(context.getAnnotatedElement().get());
            if (apiModePropertyAnnotation.isPresent()) {
                annotationOptional = Optional.of(annotationOptional.orElseGet(apiModePropertyAnnotation::get));
            }
        }
        if (context.getBeanPropertyDefinition().isPresent()) {
            // noinspection Guava
            com.google.common.base.Optional<ApiModelProperty> propertyAnnotation = Annotations.findPropertyAnnotation(context.getBeanPropertyDefinition().get(), ApiModelProperty.class);
            if (propertyAnnotation.isPresent()) {
                annotationOptional = Optional.of(annotationOptional.orElseGet(propertyAnnotation::get));
            }
        }
        if (!annotationOptional.isPresent()) {
            return;
        }

        AnnotatedField field = context.getBeanPropertyDefinition().get().getField();
        Class<?> fieldRawType = field.getRawType();
        // 自定义枚举支持
        if (MyEnumInterface.class.isAssignableFrom(fieldRawType)) {
            MyEnumInterface[] values = (MyEnumInterface[]) fieldRawType.getEnumConstants();
            String description = Arrays.stream(values)
                    .map(myEnumInterface -> myEnumInterface.getValue() + ":" + myEnumInterface.getDescription())
                    .collect(Collectors.joining(", "));

            Field mField = ModelPropertyBuilder.class.getDeclaredField("description");
            mField.setAccessible(true);
            description =  mField.get(context.getBuilder()) + "（" + description + "）";

            ResolvedType resolvedType = context.getResolver().resolve(int.class);
            context.getBuilder().description(description).type(resolvedType);
        }

        // jsr303 以及 自定义注解 的非空支持
        boolean native303NotNull = field.hasAnnotation(NotNull.class) || field.hasAnnotation(NotBlank.class);
        boolean customVerifyNotNull = field.hasAnnotation(Verify.class) && !field.getAnnotation(Verify.class).allowBlank();
        if (native303NotNull || customVerifyNotNull) {
            context.getBuilder().required(true);
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
