package priv.wmc.main.rabbit.model1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Wang Mincong
 * @date 2020-10-01 15:09:29
 */
@Slf4j
@Component
@RabbitListener(queuesToDeclare = @Queue("hello"))
public class MessageConsumer {

    @RabbitHandler
    public void messageConsume(String message) {
        log.info(" [Consumer] Received '" + message + "'");
    }

}
