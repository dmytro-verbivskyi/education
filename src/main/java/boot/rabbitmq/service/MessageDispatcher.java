package boot.rabbitmq.service;

import boot.rabbitmq.service.wf1.StringWithClassResult;
import boot.rabbitmq.service.wf1.Wf1WorkerA;
import boot.rabbitmq.service.wf1.Wf1WorkerB;
import boot.rabbitmq.service.wf1.Wf1WorkerC;
import com.google.common.collect.ImmutableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MessageDispatcher {

    private static final Logger LOG = LogManager.getLogger();

    private final AmqpTemplate rabbitTemplate;
    private final String queueWf1StateA;
    private final String queueWf1StateB;
    private final String queueWf1StateC;

    private final Map<Class, String> config;

    ExecutorService executor = Executors.newFixedThreadPool(10);

    @Autowired
    public MessageDispatcher(AmqpTemplate rabbitTemplate,
                             @Value("${queue.wf1.state.A}") String queueWf1StateA,
                             @Value("${queue.wf1.state.B}") String queueWf1StateB,
                             @Value("${queue.wf1.state.C}") String queueWf1StateC
                             ) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueWf1StateA = queueWf1StateA;
        this.queueWf1StateB = queueWf1StateB;
        this.queueWf1StateC = queueWf1StateC;

        this.config = ImmutableMap.of(
                Wf1WorkerA.class, this.queueWf1StateB,
                Wf1WorkerB.class, this.queueWf1StateC
        );
    }

    @RabbitListener(queues = "${queue.wf1.state.A}")
    public void wf1StateA(String message) {
        LOG.info(">>>> Received wf1.state.A [{}]", message);

        CompletableFuture<StringWithClassResult> future = CompletableFuture.supplyAsync(new Wf1WorkerA(message));

        future.thenApplyAsync(result -> {
            LOG.info(">>>> Result wf1.state.A [{}]", result.getMessage());
            LOG.info(">>>> going to send: {}", config.get(result.getaClass()));
            rabbitTemplate.convertAndSend(config.get(result.getaClass()), result.getMessage());
            return result;
        });
    }

    @RabbitListener(queues = "${queue.wf1.state.B}")
    public void wf1StateB(String message) {
        LOG.info(">>>> Received wf1.state.B [{}]", message);
        CompletableFuture<StringWithClassResult> future = CompletableFuture.supplyAsync(new Wf1WorkerB(message));

        future.thenApplyAsync(result -> {
            LOG.info(">>>> Result wf1.state.B [{}]", result.getMessage());
            LOG.info(">>>> going to send: {}", config.get(result.getaClass()));
            rabbitTemplate.convertAndSend(config.get(result.getaClass()), result.getMessage());
            return result;
        });
    }

    @RabbitListener(queues = "${queue.wf1.state.C}")
    public void wf1StateC(String message)  {
        LOG.info(">>>> Received wf1.state.C [{}]", message);
        CompletableFuture<StringWithClassResult> future = CompletableFuture.supplyAsync(new Wf1WorkerC(message));

        future.thenApplyAsync(result -> {
            LOG.info(">>>> Result wf1.state.C [{}]", result.getMessage() + "finished");
            //rabbitTemplate.convertAndSend(config.get(result.getaClass()), result.getMessage());
            return result;
        });
    }

}
