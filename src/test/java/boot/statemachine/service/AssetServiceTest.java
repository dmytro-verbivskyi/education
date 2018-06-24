package boot.statemachine.service;

import boot.statemachine.model.Asset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static boot.statemachine.config.ImageProcessingStateMachine.Event;
import static boot.statemachine.config.ImageProcessingStateMachine.State;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
@EnableAutoConfiguration
public class AssetServiceTest {

    @Autowired
    AssetService assetService;

    @Test
    public void simpleTest() {
        Asset a = assetService.createInitial(42);

        StateMachine<State, Event> payMachine = assetService.pay(a.getWorkflowId(), "pay First time");
        System.out.println(String.format("after pay(): %s", payMachine.getState().getId().name()));

        StateMachine<State, Event> fulfillMachine = assetService.fulfill(a.getWorkflowId(), "Kyiv city");
        System.out.println(String.format("after fulfill(): %s", fulfillMachine.getState().getId().name()));
    }
}