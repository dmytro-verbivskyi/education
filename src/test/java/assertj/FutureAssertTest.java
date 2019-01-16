package assertj;

import org.junit.Test;

import java.util.concurrent.*;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static org.assertj.core.api.Assertions.assertThat;

public class FutureAssertTest {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Test
    public void simpleFuture() throws ExecutionException, InterruptedException {
        Future<String> future = executorService.submit(() -> "done");

        future.get(); // to make sure the future is done
        assertThat(future)
                .isDone()
                .isNotCancelled();
    }

    @Test
    public void simpleFutureCancelled() throws Exception {
        Future<String> future = executorService.submit(() -> {
            sleepUninterruptibly(10, TimeUnit.SECONDS);
            return "done";
        });

        assertThat(future)
                .isNotDone()
                .isNotCancelled();

        future.cancel(true);
        assertThat(future).isCancelled();
    }

    @Test
    public void completableFuture() {
        CompletableFuture<String> completedFuture = CompletableFuture.completedFuture("something");

        assertThat(completedFuture)
                .isCompleted()
                .isCompletedWithValue("something")
                .isCompletedWithValueMatching(result -> result.startsWith("some"))
                .isCompletedWithValueMatching(result -> result.startsWith("some"), "error message")
                .isDone();
    }

    @Test
    public void completableFutureCrashed() throws Exception {
        CompletableFuture<?> futureExplosion = new CompletableFuture<>();
        futureExplosion.completeExceptionally(new RuntimeException("boom !"));

        assertThat(futureExplosion)
                .isDone()
                .isNotCancelled()
                .hasFailed()
                .isCompletedExceptionally()
                .hasFailedWithThrowableThat()
                .isInstanceOf(RuntimeException.class)
                .hasMessage("boom !");
    }

    @Test
    public void completableFutureCanceled() throws Exception {
        CompletableFuture<?> cancelledFuture = new CompletableFuture<>();
        cancelledFuture.cancel(true);

        assertThat(cancelledFuture)
                .isDone()
                .isCancelled()
                .hasNotFailed()
                .isCompletedExceptionally();
    }
}
