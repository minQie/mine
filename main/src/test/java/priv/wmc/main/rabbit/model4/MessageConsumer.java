package priv.wmc.main.rabbit.model4;

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
@Component("messageConsumer4")
public class MessageConsumer {

    @RabbitListener(bindings =
        @QueueBinding(
            // 交换机的类型默认就是这个
            exchange = @Exchange(value = "logs2", type = ExchangeTypes.DIRECT),
            value = @Queue,
            key = {"INFO", "WARN", "ERROR"}
        )
    )
    public void messageConsume(String message) {
        log.info(" [Consumer1] Received '" + message + "'");
    }

    @RabbitListener(bindings =
        @QueueBinding(
            exchange = @Exchange(value = "logs2", type = ExchangeTypes.DIRECT),
            value = @Queue,
            key = {"DEBUG"}
        )
    )
    public void messageConsume2(String message) {
        log.info(" [Consumer2] Received '" + message + "'");
    }

}
