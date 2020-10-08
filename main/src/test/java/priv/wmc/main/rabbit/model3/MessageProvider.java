package priv.wmc.main.rabbit.model3;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import priv.wmc.main.App;

/**
 * 模型三 添加交换机概念的多消费者
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
        // 再提一下，无论是队列还是交换机都是由消费者端来确认创建的（因为消费者对队列交换机的相关定义，在容器启动扫描类注解时就已经确定了，而生产者则需实际运行才能确定）
        for (int i = 0; i < 10; i++) {
            String message = "我是消息" + i;
            log.info(" [Producer] Sent '" + message + "'");
            rabbitTemplate.convertAndSend("logs", "", message);
        }
    }

}
