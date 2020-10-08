package priv.wmc.main.rabbit.model5;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Wang Mincong
 * @date 2020-10-01 15:09:29
 */
@Slf4j
@Component("messageConsumer5")
public class MessageConsumer {

    @RabbitListener(bindings =
        @QueueBinding(
            exchange = @Exchange(value = "B", type = ExchangeTypes.TOPIC),
            value = @Queue,
            key = {"user.*"}
        )
    )
    public void messageConsume(String message) {
        log.info(" [Consumer1] Received '" + message + "'");
    }

    @RabbitListener(bindings =
        @QueueBinding(
            exchange = @Exchange(value = "B", type = ExchangeTypes.TOPIC),
            value = @Queue,
            key = {"user.#"}
        )
    )
    public void messageConsume2(String message) {
        log.info(" [Consumer2] Received '" + message + "'");
    }

}
