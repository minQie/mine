package priv.wmc.module.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 基于layui的WebSocket
 *
 * @author 王敏聪
 * @date 2020/1/12 22:03
 */
@Controller
public class WebSocketPage {

    @GetMapping("/page")
    public String page() {
        return "/index.html";
    }

}
