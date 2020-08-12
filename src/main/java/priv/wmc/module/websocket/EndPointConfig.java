package priv.wmc.module.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 不可缺少配置
 *
 * @author 王敏聪
 * @date 2020/1/12 21:25
 */
@Component
public class EndPointConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
