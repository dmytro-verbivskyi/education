package boot.akkaspring.schedule;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.AdjustPoolSize;
import akka.routing.BalancingPool;
import akka.routing.GetRoutees;
import boot.akkaspring.akka.actors.MigrationActor;
import boot.akkaspring.akka.extension.SpringExtension;
import boot.akkaspring.dao.Dao;
import boot.akkaspring.model.DataItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@EnableScheduling
@Component
public class Scheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);
    private final long DELAY = 10_000;

    @Value("${scheduler.batchSize:5}")
    int batchSize;

    @Autowired
    ApplicationContext context;

    @Autowired
    Dao dataProvider;

    @Autowired
    ParamsHolder paramsHolder;

    ActorRef migrationActor;
    ActorRef masterActor;

    @Scheduled(fixedDelay = DELAY)
    public void performRegularAction() {
        LOGGER.info("---------------------------------------------------");
        LOGGER.info("Try to retrieve {} records from DB...", batchSize);
        List<DataItem> dataItems = dataProvider.retrieveItems(batchSize);
        LOGGER.info("{} records retrieved", dataItems.size());

        int itemNumber = 0;
        for (DataItem dataItem : dataItems) {
            LOGGER.info("Send to actor item №{}...", itemNumber++);
            migrationActor.tell(new MigrationActor.Send(dataItem), ActorRef.noSender());
        }
    }

    @PostConstruct
    public void postConstructMethod() {
        ActorSystem system = context.getBean(ActorSystem.class);
        SpringExtension springExtension = context.getBean(SpringExtension.class);

        // Use the Spring Extension to create props for a named actor bean
        migrationActor = system.actorOf(
                springExtension.props("migrationActor")
                        .withRouter(new BalancingPool(paramsHolder.getActorsCount()))
        );
        masterActor = system.actorOf(springExtension.props("masterActor"));
    }

    public void changeActorsCount(int change) {
        migrationActor.tell(new AdjustPoolSize(change), ActorRef.noSender());
        migrationActor.tell(GetRoutees.getInstance(), masterActor);
    }
}
