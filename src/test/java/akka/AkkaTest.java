package akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.Props;
import akka.testkit.TestActor;
import akka.testkit.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class AkkaTest {

    private static ActorSystem system = null;

    @BeforeClass
    public static void setUp() {
        system = ActorSystem.create("test-system");
    }

    @AfterClass
    public static void tearDown() {
//        TestKit.shutdownActorSystem(system, Duration.apply(1000, TimeUnit.MILLISECONDS), true);
        system = null;
    }

    @Test
    public void givenAnActor_sendHimAMessageUsingTell() throws InterruptedException {
        final TestKit probe = new TestKit(system);
        ActorRef myActorRef = probe.childActorOf(Props.create(MyActor.class));
        myActorRef.tell("printit", probe.testActor());
        myActorRef.tell("printit2", probe.testActor());

        LinkedBlockingDeque<TestActor.Message> queue = probe.akka$testkit$TestKitBase$$queue();

        Thread.sleep(1000);
        assertThat(queue).isNotEmpty();
        probe.expectMsg("Got Message: printit");
    }

    @Test
    public void givenAnActor_sendHimAMessageUsingAsk() throws ExecutionException, InterruptedException {
        final TestKit probe = new TestKit(system);
        ActorRef wordCounterActorRef = probe.childActorOf(Props.create(WordCounterActor.class));

        CompletableFuture<Object> future =
                ask(wordCounterActorRef, new WordCounterActor.CountWords("this is a text"), 1000).toCompletableFuture();

        assertThat((Integer) future.get()).as("The actor should count 4 words").isEqualTo(4);
    }

    @Test
    public void givenAnActor_whenTheMessageIsNull_respondWithException() {
        final TestKit probe = new TestKit(system);
        ActorRef wordCounterActorRef = probe.childActorOf(Props.create(WordCounterActor.class));

        CompletableFuture<Object> future =
                ask(wordCounterActorRef, new WordCounterActor.CountWords(null), 1000).toCompletableFuture();

        assertThatExceptionOfType(ExecutionException.class)
                .isThrownBy(() -> {
                            future.get(1000, TimeUnit.MILLISECONDS);
                        }
                )
                .withMessageContaining("The text to process can't be null!")
                .withRootCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void givenAnAkkaSystem_countTheWordsInAText() {
        ActorSystem system = ActorSystem.create("test-system");

        ActorRef readingActorRef = system.actorOf(ReadingActor.props(TEXT), "readingActor");
        readingActorRef.tell(new ReadingActor.ReadLines(), ActorRef.noSender());
    }

    @Test
    public void TODO_testDeadLettersBox() throws Exception {
//        ActorRef myActorRef = system.actorOf(Props.create(MyActor.class), "my-actor");
//        myActorRef.tell("This should go to deadLetter", ActorRef.noSender());

        final TestKit probe = new TestKit(system);
        ActorRef myActorRef = probe.childActorOf(Props.create(MyActor.class));
        myActorRef.tell("This should go back to deadLetter", ActorRef.noSender());

        system.eventStream().subscribe(probe.testActor(), DeadLetter.class);
        ActorRef deadLetters = system.deadLetters();
        int a = 34;
    }

    @Test
    public void TODO_verifyWorkOfPoisonPill() throws Exception {
//        ActorSystem system = ActorSystem.create("test-system");
//        ActorRef myActorRef = system.actorOf(Props.create(MyActor.class), "my-actor");
//        myActorRef.tell("printit", null);
//        system.stop(myActorRef);
//        myActorRef.tell(PoisonPill.getInstance(), ActorRef.noSender());
//        myActorRef.tell(Kill.getInstance(), ActorRef.noSender());
    }

    @Test
    public void TODO_verifyTerminateSystem() throws Exception {
//        ActorSystem system = ActorSystem.create("test-system");
//        Future<Terminated> terminateResponse = system.terminate();
    }

    private static String TEXT = "Lorem Ipsum is simply dummy text\n" +
            "of the printing and typesetting industry.\n" +
            "Lorem Ipsum has been the industry's standard dummy text\n" +
            "ever since the 1500s, when an unknown printer took a galley\n" +
            "of type and scrambled it to make a type specimen book.\n" +
            " It has survived not only five centuries, but also the leap\n" +
            "into electronic typesetting, remaining essentially unchanged.\n" +
            " It was popularised in the 1960s with the release of Letraset\n" +
            " sheets containing Lorem Ipsum passages, and more recently with\n" +
            " desktop publishing software like Aldus PageMaker including\n" +
            "versions of Lorem Ipsum.";

}