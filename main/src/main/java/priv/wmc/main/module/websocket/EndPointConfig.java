package priv.wmc.main.module.websocket;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 不可缺少配置
 *
 * @author 王敏聪
 * @date 2020/1/12 21:25
 */
@Configuration
public class EndPointConfig {

    @Bean
    @ConditionalOnBean(javax.websocket.server.ServerContainer.class)
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
