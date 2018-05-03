package boot.akkaspring.akka.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import boot.akkaspring.model.DataItem;
import boot.akkaspring.service.ExternalService;
import boot.akkaspring.service.contract.ServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MigrationActor extends AbstractActor {
    private final LoggingAdapter LOGGER = Logging.getLogger(getContext().getSystem(), this);

    @Autowired
    @Qualifier("externalServiceFakeImpl")
    ExternalService externalService;

    public MigrationActor() {

    }

    public MigrationActor(ExternalService externalService) {
        this.externalService = externalService;
    }

    public static Props props(ExternalService externalService) {
        return Props.create(MigrationActor.class, externalService);
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        LOGGER.info("Migration actor started");
    }

    @Override
    public void postStop() throws Exception {
        LOGGER.info("Migration actor stopped");
        super.postStop();
    }

    public static class Send {
        public final DataItem dataItem;

        public Send(DataItem dataItem) {
            this.dataItem = dataItem;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Send.class, this::onSend)
                .build();
    }

    private void onSend(Send send) {
        LOGGER.info("Start call service by onSend...");

        ServiceRequest request = new ServiceRequest();
        request.setDataItem(send.dataItem);
        externalService.timeConsumingOperation(request);

        LOGGER.info("Finish call to service by onSend");
    }
}
