package boot.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitMqApp {

    /*  > docker run -d --rm --hostname my-rabbit --name some-rabbit -p 8081:15672 -p 5672:5672 rabbitmq:3-management

        > docker log some-rabbit

        > docker-machine env
            will show ip address how to access rabbitmq
            export DOCKER_HOST="tcp://192.168.99.100:2376"

            then go to http://192.168.99.100:8081
    */
    public static void main(String[] args) {
        SpringApplication.run(RabbitMqApp.class, args);
    }
}
