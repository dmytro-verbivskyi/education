package boot.rabbitmq.service.wf1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;

public class Wf1WorkerA implements Supplier<StringWithClassResult> {

    private static final Logger LOG = LogManager.getLogger();

    private final String message;

    public Wf1WorkerA(String message) {
        this.message = message;
    }

    @Override
    public StringWithClassResult get() {
        LOG.info(">>>> >>>> Work started A: [{}]", message);
        sleepUninterruptibly(1, TimeUnit.SECONDS);
        LOG.info(">>>> >>>> Work finished A: [{}]", message);

        return new StringWithClassResult()
                .setClass(this.getClass())
                .setMessage(message + " > go next > ");
    }
}
