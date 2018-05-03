package boot.akkaspring.akka.actors;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.Routees;
import boot.akkaspring.schedule.ParamsHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MasterActor extends AbstractActor {

    private final LoggingAdapter LOGGER = Logging.getLogger(getContext().getSystem(), this);

    @Autowired
    ParamsHolder paramsHolder;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Routees.class, this::onRoutees)
                .build();
    }

    private void onRoutees(Routees routees) {
        int actorsCount = routees.getRoutees().size();
        LOGGER.info("Count of actors now is {}", actorsCount);

        paramsHolder.setActorsCount(actorsCount);
    }
}
