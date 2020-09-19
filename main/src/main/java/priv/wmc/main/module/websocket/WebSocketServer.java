package priv.wmc.main.module.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * WebSocket Demo - 要引入 spring-boot-starter-websocket
 *
 * 配合resources/layui下的页面
 *
 * @author 王敏聪
 * @date 2020/1/12 11:52
 */
@Slf4j
@EqualsAndHashCode
@ServerEndpoint("/log")
@Component
public class WebSocketServer {

    /**
     * concurrent包的线程安全Set，用来存放WebSocket对象
     */
    private static final CopyOnWriteArraySet<Session> ON_LINE_SET = new CopyOnWriteArraySet<>();

    @OnOpen
    public void open(Session session) {
        log.info("a lala：" + Thread.currentThread().getName());
        ON_LINE_SET.add(session);

        sendMessage(session, "hello new client!");
        log.info("a new client connect, online client: {}", ON_LINE_SET.size());
    }

    @OnClose
    public void onClose(Session session) {
        ON_LINE_SET.remove(session);

        log.info("a new client disconnect, online client: {}", ON_LINE_SET.size());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        log.info(session.getNegotiatedSubprotocol() + "say: " + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        ON_LINE_SET.remove(session);

        log.error(session.getNegotiatedSubprotocol() + "error occur...");
        error.printStackTrace();
    }

    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("向客户端发送信息失败...");
            e.printStackTrace();
        }
    }

}
