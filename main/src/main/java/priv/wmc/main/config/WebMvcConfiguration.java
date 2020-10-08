package priv.wmc.main.config;

import java.time.format.DateTimeFormatter;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.Data;
import org.hibernate.validator.HibernateValidator;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import priv.wmc.main.config.converter.EnumConverterFactory;
import priv.wmc.main.config.converter.InstantConverter;
import priv.wmc.main.config.schedule.SchedulingCondition;
import priv.wmc.common.constant.StaticConstants;
import priv.wmc.main.test.aop.XxxInterceptor;
import priv.wmc.main.test.interceptor.XxxMvcInterceptor;
import priv.wmc.main.web.filter.XssFilter;

/**
 *
 *
 * @author 王敏聪
 * @date 2020-01-31 08:46:21
 */
@Data
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Profile("dev")
    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor2() {
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();

        // 设置切点
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("@annotation(priv.wmc.main.test.aop.Aop) && execution(* priv.wmc.main.service..*(..))");
        advisor.setPointcut(pointcut);

        // 设置通知
        XxxInterceptor xxxInterceptor = new XxxInterceptor();
        advisor.setAdvice(xxxInterceptor);

        return advisor;
    }

    /**
     * 注册过滤器
     */
    @Profile("dev")
    @Bean
    public FilterRegistrationBean<XssFilter> someFilterRegistration() {
        FilterRegistrationBean<XssFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new XssFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        registrationBean.setEnabled(true);
        return registrationBean;
    }

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new XxxMvcInterceptor()).addPathPatterns("/**").excludePathPatterns("/login").order(1);
    }

    /**
     * Get请求方式的Instant和Enum的自定义反序列化规则
     */
    @Override
    public void addFormatters(@SuppressWarnings("NullableProblems") FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(DateTimeFormatter.ofPattern(StaticConstants.DATE_PATTERN));
        registrar.setTimeFormatter(DateTimeFormatter.ofPattern(StaticConstants.TIME_PATTERN));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(StaticConstants.DATE_TIME_PATTERN));
        registrar.registerFormatters(registry);

        // Instant
        registry.addConverter(new InstantConverter(StaticConstants.DATE_TIME_PATTERN, StaticConstants.TIMEZONE));

        // Enum
        registry.addConverterFactory(new EnumConverterFactory());
    }

    /**
     * 跨域支持
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/api/**")
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
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
            .configure()
            // failFast：校验时，是全部校验完统计错误信息；还是校验到错误信息，就直接返回
//            .addProperty("hibernate.validator.fail_fast", "false")
            .failFast(false)
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

