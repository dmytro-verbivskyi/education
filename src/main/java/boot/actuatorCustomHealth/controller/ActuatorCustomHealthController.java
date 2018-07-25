package boot.actuatorCustomHealth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActuatorCustomHealthController {

    @Value("${greeting.message}")
    private String greetingMessage;

    @RequestMapping("/hello")
    public String index() {
        return greetingMessage;
    }
}
