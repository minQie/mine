package priv.wmc.main.rabbit.model1;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import priv.wmc.main.App;

/**
 * 像当前这样写法，实际运行时会在启动时报错，因为首先这是在测试环境，springboot 默认不启动 web 服务器（Tomcat），即不加载相关类
 * 而 WebSocket 的相关实现是基于 Tomcat 的，实际就是在创建 ServerEndpointExporter 的时候报错
 *
 * 解决一：在测试用例中把 web 服务器启起来，即，为 SpringBootTest 注解设置一个属性 webEnvironment = WebEnvironment.RANDOM_PORT
 * 解决二（推荐）：和 web 服务紧密相关的类目应该设置成处于 web 环境下才加载，即，@ConditionalOnBean(javax.websocket.server.ServerContainer.class)
 *
 * @author Wang Mincong
 * @date 2020-10-01 15:09:29
 */
@Slf4j
@SpringBootTest(classes = App.class, properties = "spring.profiles.active=dev")
@RunWith(SpringRunner.class)
public class MessageProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void messageProvide() {
        // 这里需要解释一下，发送消息不需要指定队列发送的原因
        // 首先消费者产生 hello 队列，放在默认的交换机上，RabbitMQ 中默认的交换机有一个特点，该交换机会将内部与 routingKey 同名的队列路由上
        // 生产者指定 routingKey 为 hello 的消息就会发送给没有指定交换机，且指定监听 hello 队列的消费者了
        String message = "我是消息";
        log.info(" [Producer] Sent '" + message + "'");
        rabbitTemplate.convertAndSend("hello", message);
    }

}
