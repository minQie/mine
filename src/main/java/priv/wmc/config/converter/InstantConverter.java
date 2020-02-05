package priv.wmc.config.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author 王敏聪
 * @date 2020-01-21 23:07:37
 */
public class InstantConverter implements Converter<String, Instant> {

    final private DateTimeFormatter formatter;

    final private String pattern;

    public InstantConverter(String pattern, String timeZone) {
        this.pattern = pattern;
        this.formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.of(timeZone));
    }

    @Override
    public Instant convert(String source) {
        try {
            return formatter.parse(source, Instant::from);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("格式错误位于第" + (e.getErrorIndex()+1) +"字符，请使用正确格式：" + pattern, e);
        }
    }

}
