package boot.statemachine.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static boot.statemachine.config.ImageProcessingStateMachine.Event;
import static boot.statemachine.config.ImageProcessingStateMachine.Event.FULFILL;
import static boot.statemachine.config.ImageProcessingStateMachine.Event.PAY;
import static boot.statemachine.config.ImageProcessingStateMachine.State;
import static boot.statemachine.service.AssetService.WORKFLOW_ID_HEADER;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
//        , classes = {DataSourceAutoConfiguration.class}
)
@ActiveProfiles({"test"})
@EnableAutoConfiguration
public class StateServiceTest {

    @Autowired
    StateMachineFactory<State, Event> factory;

    @Test
    public void name() {
        StateMachine<State, Event> machine = factory.getStateMachine();
        machine.getExtendedState().getVariables().putIfAbsent(WORKFLOW_ID_HEADER, 32323232L);
        machine.start();

        machine.sendEvent(PAY);
        System.out.println(machine.getState().getId().name());

        machine.sendEvent(FULFILL);
        System.out.println(machine.getState().getId().name());
    }
}