package com.wmc.config.websocket;

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
     * 线程安全，记录在线连接数
     */
    private static AtomicInteger connectCount = new AtomicInteger(0);

    /**
     * concurrent包的线程安全Set，用来存放WebSocket对象
     */
    public static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    private Session session;

    @OnOpen
    public void open(Session session) {
        webSocketSet.add(this);
        connectCount.getAndIncrement();
        this.session = session;

        sendMessage("hello new client!");
        log.info("a new websocket connected...");
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        connectCount.getAndDecrement();

        log.info("an exist websocket closed...");
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
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("向客户端发送信息失败...");
            e.printStackTrace();
        }
    }

}
