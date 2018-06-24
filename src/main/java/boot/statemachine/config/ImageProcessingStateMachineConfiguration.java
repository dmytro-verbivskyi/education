package boot.statemachine.config;

import boot.statemachine.service.action.FulFillAction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.EnumSet;

import static boot.statemachine.config.ImageProcessingStateMachine.Event;
import static boot.statemachine.config.ImageProcessingStateMachine.Event.*;
import static boot.statemachine.config.ImageProcessingStateMachine.State;
import static boot.statemachine.config.ImageProcessingStateMachine.State.*;
import static java.lang.String.format;

@Configuration
@EnableStateMachineFactory
public class ImageProcessingStateMachineConfiguration extends StateMachineConfigurerAdapter<State, Event> {

    private static final Log LOG = LogFactory.getLog(ImageProcessingStateMachineConfiguration.class);

    @Autowired
    FulFillAction fulFillAction;

    @Override
    public void configure(StateMachineStateConfigurer<State, Event> states) throws Exception {
        states.withStates()
                .initial(SUBMITTED)
                .states(EnumSet.allOf(State.class))
                .end(FULFILLED)
                .end(CANCELLED)
        ;
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<State, Event> transitions) throws Exception {
        transitions
                .withExternal()
                .source(SUBMITTED).target(PAID).event(PAY).and()
                .withExternal()
                .source(PAID).target(FULFILLED).event(FULFILL).action(fulFillAction).and()

                /* can be CANCELED from all other states, none-END states */
                .withExternal()
                .source(PAID).target(CANCELLED).event(CANCEL).and()
                .withExternal()
                .source(SUBMITTED).target(CANCELLED).event(CANCEL)
        ;
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<State, Event> config) throws Exception {
        config.withConfiguration()
                .autoStartup(false)
                .listener(new StateMachineListenerAdapter<State, Event>() {
                    @Override
                    public void stateChanged(org.springframework.statemachine.state.State<State, Event> from, org.springframework.statemachine.state.State<State, Event> to) {
                        LOG.info(format("stateChanged(from: %s, to: %s", from, to));
                    }
                })
        ;
    }
}
