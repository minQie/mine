package priv.wmc.main.rabbit.model2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Wang Mincong
 * @date 2020-10-01 15:09:29
 */
@Slf4j
@Component("messageConsumer2")
public class MessageConsumer {

    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void messageConsume(String message) {
        log.info(" [Consumer1] Received '" + message + "'");
    }

    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void messageConsume2(String message) {
        log.info(" [Consumer2] Received '" + message + "'");
    }

}
