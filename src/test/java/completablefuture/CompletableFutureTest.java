package completablefuture;

import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void thenApply() throws Exception {
        LOG.info("preparing thenApply()");

        CompletableFuture<String> testOne = CompletableFuture.supplyAsync(() -> {
            LOG.info("starting step 1");
            Uninterruptibles.sleepUninterruptibly(140, TimeUnit.MILLISECONDS);

            if (true) {
                throw new RuntimeException("OMG");
            }
            LOG.info("finishing step 1");
            return "Hello";
        });

        LOG.info("ready to set TWO()");

        CompletableFuture<String> testTwo = CompletableFuture.supplyAsync(() -> {
            LOG.info("starting step 2");
            Uninterruptibles.sleepUninterruptibly(40, TimeUnit.MILLISECONDS);
            LOG.info("finishing step 2");
            return "World";
        });

        LOG.info("ready to set ALLOF()");
        CompletableFuture<Void> waitForAll = CompletableFuture.allOf(testOne, testTwo);
        waitForAll.thenApply(aVoid -> {
            LOG.info("starting step ALLOF");
            Uninterruptibles.sleepUninterruptibly(40, TimeUnit.MILLISECONDS);
            LOG.info("finishing step ALLOF");
            return "yo";
        });

        LOG.info("result thenApply(): {}", testOne.get());
        LOG.info("result thenApply(): {}", testTwo.get());
        LOG.info("result thenApply(): {}", waitForAll.get());

        LOG.info("stop thenApply()");
    }
}
