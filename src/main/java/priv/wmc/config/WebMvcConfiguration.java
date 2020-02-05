package priv.wmc.config;

import priv.wmc.config.converter.EnumConverterFactory;
import priv.wmc.config.converter.InstantConverter;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;

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
}

