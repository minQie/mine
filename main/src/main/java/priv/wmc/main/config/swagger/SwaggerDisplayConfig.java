package priv.wmc.main.config.swagger;

import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import java.lang.reflect.Field;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import priv.wmc.main.base.enums.EnumDefine;
import priv.wmc.main.util.EnumDefineUtils;
import priv.wmc.main.module.verify.Verify;
import springfox.documentation.builders.ModelSpecificationBuilder;
import springfox.documentation.builders.PropertySpecificationBuilder;
import springfox.documentation.schema.ModelSpecification;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

/**
 * <p>将swagger2的自定生成api文档功能，拓展适配当前项目的一些自定义配置以及jsr303非空
 *
 * <ol>
 * <li>EnumDefine类型：正确解析</li>
 * <li>jsr303非空支持</li>
 * </ol>
 *
 * @author 王敏聪
 * @date 2020-1-3 14:50:58
 */
@Slf4j
@Configuration
@AllArgsConstructor(onConstructor_ = @Autowired)
@ConditionalOnProperty(value = "app.swagger-enable", havingValue = "true")
public class SwaggerDisplayConfig <T extends Enum<T> & EnumDefine> implements ModelPropertyBuilderPlugin {

    @SneakyThrows
    @Override
    public void apply(ModelPropertyContext context) {
        // beanPropertyDefinition 得有值
        Optional<BeanPropertyDefinition> beanPropertyDefinition = context.getBeanPropertyDefinition();
        if (!beanPropertyDefinition.isPresent()) {
            return;
        }

        // 下面这一段代码虽然是原逻辑，但是放开了，发现任何字段都无法执行到设计的地方
        // 从被带有注解修饰的字段中 或者 从 get set 方法上 一定要找到@ApiModelProperty注解
//        ApiModelProperty apiModelProperty = null;
//        Optional<AnnotatedElement> annotatedElement = context.getAnnotatedElement();
//        if (!annotatedElement.isPresent()) {
//            return;
//        }
//        Optional<ApiModelProperty> apiModePropertyAnnotation = ApiModelProperties.findApiModePropertyAnnotation(annotatedElement.get());
//        if (apiModePropertyAnnotation.isPresent()) {
//            apiModelProperty = apiModePropertyAnnotation.get();
//        }
//        apiModePropertyAnnotation = Annotations.findPropertyAnnotation(beanPropertyDefinition.get(), ApiModelProperty.class);
//        if (apiModePropertyAnnotation.isPresent()) {
//            apiModelProperty = apiModePropertyAnnotation.get();
//        }
//        if (Objects.isNull(apiModelProperty)) {
//            return;
//        }

        AnnotatedField field = beanPropertyDefinition.get().getField();
        Class<?> fieldRawType = field.getRawType();
        // 自定义枚举支持
        if (EnumDefineUtils.isEnumDefine(fieldRawType)) {
            // noinspection unchecked
            Class<T> enumDefineField = (Class<T>) fieldRawType;

            Field mField = PropertySpecificationBuilder.class.getDeclaredField("description");
            // 没有下面这句，通过field获取相应对象对应字段的值，会报 IllegalAccessException
            mField.setAccessible(true);
            String description =  mField.get(context.getSpecificationBuilder()) + EnumDefineUtils.getDescription(enumDefineField);

            ModelSpecification modelSpecification = new ModelSpecificationBuilder()
                .scalarModel(ScalarType.INTEGER)
                .build();
            context
                .getSpecificationBuilder()
                .description(description)
                .type(modelSpecification);
        }

        // jsr303 以及 自定义注解 的非空支持
        boolean native303NotNull = field.hasAnnotation(NotNull.class) || field.hasAnnotation(NotBlank.class);
        boolean customVerifyNotNull = field.hasAnnotation(Verify.class) && !field.getAnnotation(Verify.class).allowBlank();
        if (native303NotNull || customVerifyNotNull) {
            context.getSpecificationBuilder().required(true);
        }
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
