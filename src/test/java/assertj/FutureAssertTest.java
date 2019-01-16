package assertj;

import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

public class FutureAssertTest {

    @Ignore
    @Test
    public void name() throws ExecutionException, InterruptedException {
        Future<String> future = Executors.newCachedThreadPool().submit(() -> {
            if (true) {
                throw new RuntimeException("Hello");
            }
            return "World";
        });

        assertThat(future).isDone();
    }
}
