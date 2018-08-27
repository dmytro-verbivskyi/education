package boot.rabbitmq.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class RabbitReceiver {

    private static final Logger LOG = LogManager.getLogger();

    @RabbitListener(queues = "${queue.name.one}")
    public void processQueue1(String message) {
        LOG.info(">>>> Received from 1 [{}]", message);
    }
}
