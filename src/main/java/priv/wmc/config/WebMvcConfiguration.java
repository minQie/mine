package priv.wmc.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.Data;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import priv.wmc.config.converter.EnumConverterFactory;
import priv.wmc.config.converter.InstantConverter;

import java.time.format.DateTimeFormatter;
import priv.wmc.config.schedule.SchedulingCondition;
import priv.wmc.constant.StaticConfig;

/**
 *
 *
 * @author 王敏聪
 * @date 2020-01-31 08:46:21
 */
@Data
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * Get请求方式的Instant和Enum的自定义反序列化规则
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(DateTimeFormatter.ofPattern(StaticConfig.DATE_PATTERN));
        registrar.setTimeFormatter(DateTimeFormatter.ofPattern(StaticConfig.TIME_PATTERN));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(StaticConfig.DATE_TIME_PATTERN));
        registrar.registerFormatters(registry);

        // Instant
        registry.addConverter(new InstantConverter(StaticConfig.DATE_TIME_PATTERN, StaticConfig.TIMEZONE));

        // Enum
        registry.addConverterFactory(new EnumConverterFactory());
    }

    /**
     * 跨域支持
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("*")
            .allowCredentials(true)
            .allowedHeaders("content-type, authorization")
            .allowedMethods(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                // 浏览器发起是否支持跨域请求的请求方式
                HttpMethod.OPTIONS.name(),
                HttpMethod.PATCH.name()
            );
    }

    /**
     * JSR303的校验器<br>
     * @see priv.wmc.common.utils.ValidateUtils
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
            .configure()
            .addProperty("hibernate.validator.fail_fast", "false")
            .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    /**
     * Spring定时任务的开关<br>
     * @see SchedulingCondition
     */
    @Bean
    @Conditional(SchedulingCondition.class)
    public ScheduledAnnotationBeanPostProcessor scheduledAnnotationProcessor() {
        return new ScheduledAnnotationBeanPostProcessor();
    }

}

