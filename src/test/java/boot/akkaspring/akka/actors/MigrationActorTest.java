package boot.akkaspring.akka.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.TestActorRef;
import akka.testkit.javadsl.TestKit;
import boot.akkaspring.model.DataItem;
import boot.akkaspring.service.ExternalService;
import boot.akkaspring.service.contract.ServiceRequest;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;

public class MigrationActorTest {
    @org.junit.Rule
    public final JUnitRuleMockery mockery = new JUnitRuleMockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
        setThreadingPolicy(new Synchroniser());
    }};

    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create("TestSystem");
    }

    @AfterClass
    public static void tearDown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testMigrationActorSend() {
        ExternalService externalService = mockery.mock(ExternalService.class);
        DataItem dataItem = new DataItem();

        mockery.checking(new Expectations() {
            {
                oneOf(externalService).timeConsumingOperation(with(allOf(
                        any(ServiceRequest.class),
                        hasProperty("dataItem", is(dataItem))
                )));
            }
        });

        TestActorRef<MigrationActor> ref = TestActorRef.create(system, MigrationActor.props(externalService), "migrationActor");

        ref.tell(new MigrationActor.Send(dataItem), ActorRef.noSender());
    }
}