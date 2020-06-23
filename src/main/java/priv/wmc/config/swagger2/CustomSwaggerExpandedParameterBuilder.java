/*
 *
 *  Copyright 2015-2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package priv.wmc.config.swagger2;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.collect.Lists.transform;
import static springfox.documentation.swagger.common.SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER;
import static springfox.documentation.swagger.readers.parameter.Examples.examples;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import priv.wmc.common.enums.MyEnumInterface;
import priv.wmc.common.util.MyEnumUtils;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.AllowableValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.EnumTypeDeterminer;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import springfox.documentation.swagger.readers.parameter.SwaggerExpandedParameterBuilder;
import springfox.documentation.swagger.schema.ApiModelProperties;

/**
 * 该类作用：自定义Swagger文档中Get请求的枚举类型参数（不是展示枚举名，而是描述value对应的意思）
 *
 * SwaggerExpandedParameterBuilder文档构建插件的最后一个，想覆盖之前插件的行为，并自定义的话，就应该复写这个插件
 * （实际这样的实现方式，还是有一些问题的，不够优雅，你会看到Swagger要渲染文档的插件有一个SwaggerExpandedParameterBuilder，以及CustomSwaggerExpandedParameterBuilder）
 */
@Order
@Component
public class CustomSwaggerExpandedParameterBuilder extends SwaggerExpandedParameterBuilder {
  private final DescriptionResolver descriptions;
  private final EnumTypeDeterminer enumTypeDeterminer;

  @Autowired
  public CustomSwaggerExpandedParameterBuilder(
      DescriptionResolver descriptions,
      EnumTypeDeterminer enumTypeDeterminer) {
    super(null, null);
    this.descriptions = descriptions;
    this.enumTypeDeterminer = enumTypeDeterminer;

    // custom
//    DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//    defaultListableBeanFactory.removeBeanDefinition("swaggerExpandedParameterBuilder");
  }

  @Override
  public void apply(ParameterExpansionContext context) {
    Optional<ApiModelProperty> apiModelPropertyOptional = context.findAnnotation(ApiModelProperty.class);
    if (apiModelPropertyOptional.isPresent()) {
      fromApiModelProperty(context, apiModelPropertyOptional.get());
    }
    Optional<ApiParam> apiParamOptional = context.findAnnotation(ApiParam.class);
    if (apiParamOptional.isPresent()) {
      fromApiParam(context, apiParamOptional.get());
    }
  }

  @Override
  public boolean supports(DocumentationType delimiter) {
    return SwaggerPluginSupport.pluginDoesApply(delimiter);
  }

  private void fromApiParam(ParameterExpansionContext context, ApiParam apiParam) {
    String allowableProperty = emptyToNull(apiParam.allowableValues());
    AllowableValues allowable = allowableValues(
        fromNullable(allowableProperty),
        context.getFieldType().getErasedType());

    // custom
    ParameterBuilder parameterBuilder = maybeSetParameterName(context, apiParam.name());

    String description = descriptions.resolve(apiParam.value());
    if (MyEnumUtils.isMyEnum(context.getFieldType().getErasedType())) {
      description += MyEnumUtils.getDescription(context.getFieldType().getErasedType());
      parameterBuilder.modelRef(new ModelRef("int"));
    }

    parameterBuilder
        .description(description)
        .defaultValue(apiParam.defaultValue())
        .required(apiParam.required())
        .allowMultiple(apiParam.allowMultiple())
        .allowableValues(allowable)
        .parameterAccess(apiParam.access())
        .hidden(apiParam.hidden())
        .scalarExample(apiParam.example())
        .complexExamples(examples(apiParam.examples()))
        .order(SWAGGER_PLUGIN_ORDER)
        .build();
  }

  private void fromApiModelProperty(ParameterExpansionContext context, ApiModelProperty apiModelProperty) {
    String allowableProperty = emptyToNull(apiModelProperty.allowableValues());
    AllowableValues allowable = allowableValues(
        fromNullable(allowableProperty),
        context.getFieldType().getErasedType());

    ParameterBuilder parameterBuilder = maybeSetParameterName(context, apiModelProperty.name());

    String description = descriptions.resolve(descriptions.resolve(apiModelProperty.value()));
    if (MyEnumUtils.isMyEnum(context.getFieldType().getErasedType())) {
      description += MyEnumUtils.getDescription(context.getFieldType().getErasedType());
      parameterBuilder.modelRef(new ModelRef("int"));
    }

    parameterBuilder
        .description(description)
        .required(apiModelProperty.required())
        .allowableValues(allowable)
        .parameterAccess(apiModelProperty.access())
        .hidden(apiModelProperty.hidden())
        .scalarExample(apiModelProperty.example())
        .order(SWAGGER_PLUGIN_ORDER)
        .build();
  }

  private ParameterBuilder maybeSetParameterName(ParameterExpansionContext context, String parameterName) {
    if (!Strings.isNullOrEmpty(parameterName)) {
      context.getParameterBuilder().name(parameterName);
    }
    return context.getParameterBuilder();
  }

  private AllowableValues allowableValues(final Optional<String> optionalAllowable, Class<?> fieldType) {

    AllowableValues allowable = null;
    if (enumTypeDeterminer.isEnum(fieldType)) {
      allowable = new AllowableListValues(getEnumValues(fieldType), "LIST");
    } else if (optionalAllowable.isPresent()) {
      allowable = ApiModelProperties.allowableValueFromString(optionalAllowable.get());
    }
    return allowable;
  }

  private List<String> getEnumValues(final Class<?> subject) {
    // custom
    Function<Object, String> function = !MyEnumUtils.isMyEnum(subject)
        ? Object::toString
        : obj -> String.valueOf(((MyEnumInterface)obj).getValue());

    return transform(Arrays.asList(subject.getEnumConstants()), function::apply);
  }
}
