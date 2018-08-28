package boot.rabbitmq.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    private final String queueNameOne;
    private final String queueNameTwo;
    private final String host;
    private final String queueWf1StateA;
    private final String queueWf1StateB;
    private final String queueWf1StateC;

    public RabbitConfiguration(@Value("${queue.name.one}") String queueNameOne,
                               @Value("${queue.name.two}") String queueNameTwo,
                               @Value("${queue.wf1.state.A}") String queueWf1StateA,
                               @Value("${queue.wf1.state.B}") String queueWf1StateB,
                               @Value("${queue.wf1.state.C}") String queueWf1StateC,
                               @Value("${spring.rabbitmq.host}") String host) {
        this.queueNameOne = queueNameOne;
        this.queueNameTwo = queueNameTwo;
        this.queueWf1StateA = queueWf1StateA;
        this.queueWf1StateB = queueWf1StateB;
        this.queueWf1StateC = queueWf1StateC;
        this.host = host;
    }

    @Bean
    Queue queueWf1StateA() {
        return new Queue(queueWf1StateA, false);
    }

    @Bean
    Queue queueWf1StateB() {
        return new Queue(queueWf1StateB, false);
    }

    @Bean
    Queue queueWf1StateC() {
        return new Queue(queueWf1StateC, false);
    }

    @Bean
    Queue queue1() {
        return new Queue(queueNameOne, false);
    }

    @Bean
    Queue queue2() {
        return new Queue(queueNameTwo, false);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

}
