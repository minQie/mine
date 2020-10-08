package priv.wmc.main.rabbit.model2;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import priv.wmc.main.App;

/**
 * 模型二 work 公平的多消费者
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
        for (int i = 0; i < 10; i++) {
            String message = "我是消息" + i;
            log.info(" [Producer] Sent '" + message + "'");
            rabbitTemplate.convertAndSend("work", message);
        }
    }

}
