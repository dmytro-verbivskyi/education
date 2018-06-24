package boot.statemachine.service.action;

import boot.statemachine.service.SomeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import static boot.statemachine.config.ImageProcessingStateMachine.Event;
import static boot.statemachine.config.ImageProcessingStateMachine.State;
import static boot.statemachine.service.AssetService.WORKFLOW_ID_HEADER;
import static java.lang.String.format;

@Component
public class FulFillAction implements Action<State, Event> {

    private static final Log LOG = LogFactory.getLog(FulFillAction.class);

    private SomeService someService;

    @Autowired
    public FulFillAction(SomeService someService) {
        this.someService = someService;
    }

    @Override
    public void execute(StateContext<State, Event> context) {
        LOG.info(format("in fulfill action wf: %s; someService: %s",
                context.getExtendedState().getVariables().getOrDefault(WORKFLOW_ID_HEADER, -1L), someService));
    }
}
