package akka;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MyActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public void postStop() {
        log.info("Stopping actor {}", this);
    }

    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("printit", p -> {
                    log.info("EQ self  : {}, {}", getSelf(), getSelf().getClass().getSimpleName());
                    log.info("EQ sender: {}, {}", getSender(), getSender().getClass().getSimpleName());
                    Thread.sleep(200);
                    getSender().tell("Got Message: " + p, getSelf());
                })
                .matchAny(p -> {
                    log.info("ANY self  : {}", getSelf());
                    log.info("ANY sender: {}", getSender());
                    getSender().tell("Got Message: " + p, getSelf());
                })
                .build();
    }
}