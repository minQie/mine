package priv.wmc.main.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import priv.wmc.common.util.DateUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

/**
 * Instant序列化
 *
 * @author 王敏聪
 * @date 2020-01-15 23:28:04
 * @see DateUtils
 */
@JsonComponent
public class InstantSerializer extends JsonSerializer<Instant> {

    @Override
    public void serialize(Instant instant, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Date date = Date.from(instant);
        String dateString = DateUtils.getDateTime(date);

        gen.writeString(dateString);
    }

}
