package priv.wmc.main.config.swagger;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.wmc.main.prop.AppProperties;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ModelSpecificationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2 核心配置
 *
 * @author 王敏聪
 * @date 2019年11月20日14:21:05
 */
@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SwaggerConfig {

    private final AppProperties appConfig;

    /**
     * swagger 为请求带上请求头的配置（目前不需要）
     *
     * @return
     */
    private List<RequestParameter> getTokenParam() {
        List<RequestParameter> parameterList = new ArrayList<>();
        RequestParameterBuilder builder = new RequestParameterBuilder();
        builder
            .description("登录获取的Token")
            .in(ParameterType.HEADER)
            .name("x-user-token")
            .required(true)
            .contentModel(new ModelSpecificationBuilder().scalarModel(ScalarType.STRING).build());
        parameterList.add(builder.build());
        return parameterList;
    }

    /**
     * 自己写的
     *
     * @return
     */
    @Bean
    public Docket apiWithGlobalToken() {
        // ui
        ApiInfo adminApiInfo = new ApiInfoBuilder()
            .title("Swagger 接口文档")
            .description("浪起来~")
            .version(appConfig.getApiVersion())
            .build();

        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("核心模块")
            .enable(appConfig.getSwaggerEnable())
            .apiInfo(adminApiInfo)
            .select()
            .apis(RequestHandlerSelectors.basePackage("priv.wmc"))
//          .paths(PathSelectors.regex("^(?:(?!(/api/user/v1/manager.*)).)*$"))
            .build();
    }

}
