package boot.rabbitmq.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;

@EnableRabbit
@Component
public class RabbitReceiver {

    private static final Logger LOG = LogManager.getLogger();

    Random random = new Random();

    /* =========================================================  /
    /   Простейший пример с одним слушателем                      /
    /  ========================================================= */
    @RabbitListener(queues = "${queue.name.one}")
    public void processQueue1(String message) {
        LOG.info(">>>> Received from 1 [{}]", message);
        sleepUninterruptibly(100 * random.nextInt(20), TimeUnit.MILLISECONDS);
    }

    /* =========================================================  /
    /   В данном примере одну очередь слушают уже два листенера   /
    /  ========================================================= */
    @RabbitListener(queues = "${queue.name.two}")
    public void processQueue2_1(String message) {
        LOG.info(">>>> Worker 2_1 [{}]", message);
        sleepUninterruptibly(100 * random.nextInt(20), TimeUnit.MILLISECONDS);
    }

    @RabbitListener(queues = "${queue.name.two}")
    public void processQueue2_2(String message) {
        LOG.info(">>>> Worker 2_2 [{}]", message);
        sleepUninterruptibly(100 * random.nextInt(20), TimeUnit.MILLISECONDS);
    }


}
