package boot.securitysample.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecuredController {

    @RequestMapping("/hello")
    public String index() {
        return "Greetings from SecuredController!";
    }
}
