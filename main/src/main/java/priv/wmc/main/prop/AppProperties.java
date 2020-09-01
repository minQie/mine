package priv.wmc.main.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 项目核心配置类
 *
 * @author 王敏聪
 * @date 2019/11/17 20:31
 */
@Getter
@Setter
@ConfigurationProperties("app")
public class AppProperties {

    /**
     * 是否开启swagger
     */
    private Boolean swaggerEnable;

    /**
     * api version
     */
    private String apiVersion;

}
