package boot.helloworld.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private static final Logger LOG = LogManager.getLogger();

    @RequestMapping("/hello")
    public String hello() {
        LOG.info("hello was called");
        return "Greetings from Spring Boot!";
    }
}
