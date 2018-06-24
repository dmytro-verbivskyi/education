package boot.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.config.StateMachineFactory;

import static boot.statemachine.config.ImageProcessingStateMachine.Event;
import static boot.statemachine.config.ImageProcessingStateMachine.State;

@SpringBootApplication
public class StateMachineApp {

    final StateMachineFactory<State, Event> factory;

    @Autowired
    public StateMachineApp(StateMachineFactory<State, Event> factory) {
        this.factory = factory;
    }

    public static void main(String[] args) {
        SpringApplication.run(StateMachineApp.class, args);
    }
}
