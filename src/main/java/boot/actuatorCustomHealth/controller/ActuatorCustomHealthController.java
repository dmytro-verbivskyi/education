package boot.actuatorCustomHealth.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActuatorCustomHealthController {

    private static final Logger LOG = LogManager.getLogger();

    @Value("${greeting.message}")
    private String greetingMessage;

    @RequestMapping("/hello")
    public String hello() {
        LOG.info("hello method was called");
        return greetingMessage;
    }
}
