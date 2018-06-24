package boot.statemachine.controller;

import boot.statemachine.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateMachineController {

    private final StateService stateService;

    @Autowired
    public StateMachineController(StateService stateService) {
        this.stateService = stateService;
    }

    @RequestMapping("/state")
    public String index() {
        return "Greetings from Spring State Machine!";
    }

    @RequestMapping("/toOne")
    public String toOne() {
        stateService.letsMoveIntoOne();
        return "Moved into ONE";
    }

    @RequestMapping("/toTwo")
    public String toTwo() {
        stateService.letsMoveIntoTwo();
        return "Moved into TWO";
    }
}
