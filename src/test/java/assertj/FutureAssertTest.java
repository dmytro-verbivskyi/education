package assertj;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class FutureAssertTest {

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
