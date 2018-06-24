package boot.statemachine.service;

import boot.statemachine.config.ImageProcessingStateMachine.Event;
import boot.statemachine.config.ImageProcessingStateMachine.State;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import static boot.statemachine.config.ImageProcessingStateMachine.Event.CANCEL;
import static boot.statemachine.config.ImageProcessingStateMachine.Event.FULFILL;

@Service
public class StateService {

    private static final Log LOG = LogFactory.getLog(StateService.class);

    private final StateMachineFactory<State, Event> factory;

    @Autowired
    public StateService(StateMachineFactory<State, Event> factory) {
        this.factory = factory;
    }

    public void letsMoveIntoOne() {
        LOG.info("letsMoveIntoOne");
        StateMachine<State, Event> machine = factory.getStateMachine("42");
        machine.start();
        LOG.info("current state: " + machine.getState().getId().name());
        machine.sendEvent(FULFILL);
        LOG.info("current state: " + machine.getState().getId().name());


    }

    public void letsMoveIntoTwo() {
        LOG.info("letsMoveIntoTwo");
        factory.getStateMachine("42").sendEvent(CANCEL);
    }
}
