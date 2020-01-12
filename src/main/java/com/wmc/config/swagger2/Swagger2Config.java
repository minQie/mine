package com.wmc.config.swagger2;

import com.wmc.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2 核心配置
 *
 * @author 王敏聪
 * @date 2019年11月20日14:21:05
 */
@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class Swagger2Config {

    private final AppConfig appConfig;

    /**
     * swagger 为请求带上请求头的配置（目前不需要）
     *
     * @return
     */
    private List<Parameter> getTokenParam() {
        List<Parameter> parameterList = new ArrayList<>();
        ParameterBuilder builder = new ParameterBuilder();
        builder.description("登录获取的Token").modelRef(new ModelRef("String")).parameterType("header").name("x-user-token").required(true).build();
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
        ApiInfo adminApiInfo = new ApiInfoBuilder().title("Swagger2 接口文档").description("浪起来~").version(appConfig.getApiVersion()).build();

        return new Docket(DocumentationType.SWAGGER_2).groupName("核心模块").enable(appConfig.getSwaggerEnable()).apiInfo(adminApiInfo).select().apis(RequestHandlerSelectors.basePackage("com.wmc"))
                //                .paths(PathSelectors.regex("^(?:(?!(/api/user/v1/manager.*)).)*$"))
                .build();
    }

}
