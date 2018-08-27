package boot.rabbitmq.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@RestController
public class RabbitMqController {

    private static final Logger LOG = LogManager.getLogger();

    private final RabbitTemplate rabbitTemplate;
    private final String queueOne;

    @Autowired
    public RabbitMqController(RabbitTemplate rabbitTemplate,
                              @Value("${queue.name.one}") String queueOne) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueOne = queueOne;
    }

    @RequestMapping("/send")
    public String send() {
        String msg = String.format("Message to queue: %s", LocalDateTime.now().format(ISO_DATE_TIME));
        LOG.info(">>>> Sending to 1 [{}]", msg);
        rabbitTemplate.convertAndSend(queueOne, msg);
        return msg;
    }
}
