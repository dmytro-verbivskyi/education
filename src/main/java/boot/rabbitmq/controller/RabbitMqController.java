package boot.rabbitmq.controller;

import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@RestController
public class RabbitMqController {

    private static final Logger LOG = LogManager.getLogger();

    private final RabbitTemplate rabbitTemplate;
    private final String queueOne;
    private final String queueTwo;
    private final String queueWf1StateA;

    @Autowired
    public RabbitMqController(RabbitTemplate rabbitTemplate,
                              @Value("${queue.name.one}") String queueOne,
                              @Value("${queue.name.two}") String queueTwo,
                              @Value("${queue.wf1.state.A}") String queueWf1StateA
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueOne = queueOne;
        this.queueTwo = queueTwo;
        this.queueWf1StateA = queueWf1StateA;
    }

    @RequestMapping("/send")
    public String send() {
        String msg = String.format("Message to queue #1: %s", LocalDateTime.now().format(ISO_DATE_TIME));
        LOG.info(">>>> Sending to 1 [{}]", msg);
        rabbitTemplate.convertAndSend(queueOne, msg);
        return msg;
    }

    @RequestMapping("/send2")
    public String send2() {
        String msg = String.format("Message to queue #2: %s", LocalDateTime.now().format(ISO_DATE_TIME));
        LOG.info(">>>> Sending to 2 [{}]", msg);
        rabbitTemplate.convertAndSend(queueTwo, msg);
        return msg;
    }

    @RequestMapping("/sendA")
    public String sendA() {
        for (int i = 0; i < 5; i++) {
            String msg = String.format("Message to queue #A: %d", i);
            LOG.info(">>>> Sending to wf1_A [{}]", msg);
            Uninterruptibles.sleepUninterruptibly(400, TimeUnit.MILLISECONDS);
            rabbitTemplate.convertAndSend(queueWf1StateA, msg);
        }
        return "done";
    }
}
