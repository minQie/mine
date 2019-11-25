package com.wmc.config.json;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.util.TypeUtils;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.Collections;

/**
 * 替换Spring默认的json解析框架，为FastJson - 处理数据空值问题（不能把null返回给前端，Map中的空键值，空集合、空数字、空字符串）
 *
 * @author 王敏聪
 * @date 2019/7/5 13:40
 */
@Configuration
public class FastJsonHttpMessageConverterConfig {

    static {
        // 取消FastJson会默认将json的键首字母小写的特性
        TypeUtils.compatibleWithJavaBean = true;
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero
        );

        FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();
        fastConvert.setFastJsonConfig(fastJsonConfig);
        fastConvert.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        return new HttpMessageConverters(fastConvert);
    }
}