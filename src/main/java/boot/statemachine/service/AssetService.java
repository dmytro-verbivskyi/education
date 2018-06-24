package boot.statemachine.service;

import boot.statemachine.dao.AssetRepository;
import boot.statemachine.model.Asset;
import org.aspectj.bridge.AbortException;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static boot.statemachine.config.ImageProcessingStateMachine.Event;
import static boot.statemachine.config.ImageProcessingStateMachine.State;
import static boot.statemachine.config.ImageProcessingStateMachine.State.SUBMITTED;

@Service
public class AssetService {

    public static final String WORKFLOW_ID_HEADER = "workflow-id";

    private final AssetRepository assetRepository;
    private final StateMachineFactory<State, Event> stateMachineFactory;

    public AssetService(AssetRepository assetRepository, StateMachineFactory<State, Event> stateMachineFactory) {
        this.assetRepository = assetRepository;
        this.stateMachineFactory = stateMachineFactory;
    }

    public Asset createInitial(long workflowId) {
        Asset asset = new Asset();
        asset.setCreateDate(new Date());
        asset.setState(SUBMITTED);
        asset.setWorkflowId(workflowId);
        return assetRepository.save(asset);
    }

    public StateMachine<State, Event> pay(long workflowId, String payInfo) {
        StateMachine<State, Event> machine = build(workflowId);

        boolean done = machine.sendEvent(MessageBuilder.withPayload(Event.PAY)
                .setHeader(WORKFLOW_ID_HEADER, workflowId)
                .setHeader("paymentConfirmation", payInfo)
                .build());
        return machine;
    }

    public StateMachine<State, Event> fulfill(long workflowId, String address) {
        StateMachine<State, Event> machine = build(workflowId);

        machine.sendEvent(MessageBuilder.withPayload(Event.FULFILL)
                .setHeader(WORKFLOW_ID_HEADER, workflowId)
                .setHeader("addressOfTheClient", address)
                .build());
        return machine;
    }

    private StateMachine<State, Event> build(long workflowId) {
        Asset asset = assetRepository.findOneByWorkflowId(workflowId).orElseThrow(AbortException::new);
        String key = Long.toString(asset.getWorkflowId());

        StateMachine<State, Event> machine = this.stateMachineFactory.getStateMachine(key);
        machine.stop();
        machine.getStateMachineAccessor()
                .doWithAllRegions(function -> {
                    function.addStateMachineInterceptor(new StateMachineInterceptorAdapter<State, Event>() {

                        @Override
                        public void preStateChange(org.springframework.statemachine.state.State<State, Event> state, Message<Event> message, Transition<State, Event> transition, StateMachine<State, Event> stateMachine) {

                            Optional.ofNullable(message).ifPresent(msg -> {

                                Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault(WORKFLOW_ID_HEADER, -1L)))
                                        .ifPresent(workflowId -> {
                                            Asset a = assetRepository.findOneByWorkflowId(workflowId).get();
                                            a.setState(state.getId());
                                            assetRepository.save(a);
                                        });
                            });
                        }

                    });

                    State resetToState = asset.getState();
                    function.resetStateMachine(new DefaultStateMachineContext<>(
                            resetToState, null, null, null));
                });
        return machine;
    }
}
