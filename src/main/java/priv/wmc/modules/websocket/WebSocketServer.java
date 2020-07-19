package priv.wmc.modules.websocket;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

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
    private static CopyOnWriteArraySet<Session> onlineSet = new CopyOnWriteArraySet<>();

    private Session current;

    @OnOpen
    public void open(Session session) {
        onlineSet.add(session);
        this.current = session;

        sendMessage("hello new client!");
        log.info("a new client connect, online client: {}", onlineSet.size());
    }

    @OnClose
    public void onClose() {
        onlineSet.remove(current);
        this.current = null;

        log.info("a new client disconnect, online client: {}", onlineSet.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info(session.getNegotiatedSubprotocol() + "say: " + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error(session.getNegotiatedSubprotocol() + "error occur...");
        error.printStackTrace();
    }

    private void sendMessage(String message) {
        try {
            current.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("向客户端发送信息失败...");
            e.printStackTrace();
        }
    }

}
