package boot.dynamodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DynamoDbApp {

    public static void main(String[] args) {
        SpringApplication.run(DynamoDbApp.class, args);
    }
}
