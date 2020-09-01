package priv.wmc.main.web.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 基于layui的WebSocket
 *
 * @author 王敏聪
 * @date 2020/1/12 22:03
 */
@Api(tags = "websocket ui模块")
@Controller
public class WebSocketPageController {

    @GetMapping("/page")
    public String page() {
        return "/index.html";
    }

}
