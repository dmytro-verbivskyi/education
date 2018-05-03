package boot.akkaspring.schedule;

import akka.actor.ActorSystem;
import akka.routing.AdjustPoolSize;
import akka.routing.GetRoutees;
import akka.testkit.TestProbe;
import akka.testkit.javadsl.TestKit;
import boot.akkaspring.akka.actors.MigrationActor;
import boot.akkaspring.dao.Dao;
import boot.akkaspring.model.DataItem;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SchedulerTest {
    @org.junit.Rule
    public final JUnitRuleMockery mockery = new JUnitRuleMockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};

    Scheduler scheduler;

    static ActorSystem system;
    static TestProbe probe;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create("TestSystem");
        probe = TestProbe.apply(system);
    }

    @AfterClass
    public static void tearDown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        scheduler = new Scheduler();

        scheduler.dataProvider = mockery.mock(Dao.class);
        scheduler.migrationActor = probe.ref();
        scheduler.batchSize = 5;
    }

    @Test
    public void performRegularAction() throws Exception {
        DataItem dataItem = new DataItem();
        List<DataItem> dataItems = Arrays.asList(dataItem);

        mockery.checking(new Expectations() {
            {
                oneOf(scheduler.dataProvider).retrieveItems(scheduler.batchSize);
                will(returnValue(dataItems));
            }
        });

        scheduler.performRegularAction();

        MigrationActor.Send send = probe.expectMsgClass(MigrationActor.Send.class);
        assertThat("Wrong message in Send", send.dataItem, is(dataItem));
    }

    @Test
    public void changeActorsCount()
    {
        scheduler.changeActorsCount(2);

        AdjustPoolSize message = probe.expectMsgClass(AdjustPoolSize.class);
        assertThat("Wrong change value", message.change(), is(2));
        probe.expectMsgClass(GetRoutees.class);
    }
}