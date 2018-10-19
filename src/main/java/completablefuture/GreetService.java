package completablefuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

public class GreetService {

    private static final Logger LOG = LogManager.getLogger();

    private Executor executor = Executors.newFixedThreadPool(6);

    public CompletableFuture<GreetHolder> getGreeting(String lang) {
        LOG.info("Task is preparing...");
        return CompletableFuture.supplyAsync(() -> {
            try {
                LOG.info("Task execution started.");
                Thread.sleep(2000);
                LOG.info("Task execution stopped.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new GreetHolder(getGreet(lang));
        }, executor);
    }

    private String getGreet(String lang) {
        if (lang.equals("EN")) {
            return "Hello";
        } else if (lang.equals("ES")) {
            return "Hola";
        } else if (lang.equals("UA")) {
            return "Привіт";
        } else {
            throw new IllegalArgumentException("Invalid lang param");
        }
    }
}
