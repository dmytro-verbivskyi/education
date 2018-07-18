package boot.dynamodb.model;

import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.SerializationUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static boot.dynamodb.model.ApprovalStatus.APPROVED;
import static boot.dynamodb.model.ApprovalStatus.REJECTED;
import static boot.dynamodb.model.ReviewStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;

public class ReviewTest {

    Review one = new Review()
            .setId("id")
            .setReviewId("reviewId")
            .setCreateDate(LocalDateTime.MIN.toString())
            .setStatus(PENDING)
            .setReviewOwner(new Owner()
                    .setId("owner Id")
                    .setName("owner Name")
                    .setEmail("owner Email"))
            .setAssets(Lists.newArrayList(
                    new Asset().setAssetId(42L).setStatus(APPROVED),
                    new Asset().setAssetId(567L).setStatus(ApprovalStatus.PENDING)
            ));

    @Test
    public void coverEntity() throws Exception {
        Review two = SerializationUtils.clone(one);
        Review three = SerializationUtils.clone(one);
        three.getAssets().get(0).setStatus(REJECTED);

        assertThat(one)
                .isEqualTo(one)
                .isEqualTo(two)
                .isNotEqualTo(three)
                .isNotEqualTo(null)
                .isNotEqualTo("string")
                .hasSameHashCodeAs(two)
                .hasToString("Review{id=id,"
                        + " reviewId=reviewId,"
                        + " createDate=-999999999-01-01T00:00,"
                        + " reviewOwner=Owner{id=owner Id, name=owner Name, email=owner Email},"
                        + " status=PENDING,"
                        + " assets=[Asset{assetId=42, status=APPROVED}, Asset{assetId=567, status=PENDING}]"
                        + "}");
    }

    @Test
    public void hashCodeBenchmark() throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();

        for (int i = 0; i < 1_000_000; i++) {       // traditional:  245,  209,  206 ms
            one.hashCode();                         // reflection:  1685, 1955, 1899 ms
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
        stopwatch.stop();
    }

    @Test
    public void equalsBenchmark() throws Exception {
        Review three = SerializationUtils.clone(one);
        three.getAssets().get(0).setStatus(REJECTED);

        Stopwatch stopwatch = Stopwatch.createStarted();

        for (int i = 0; i < 1_000_000; i++) {       // traditional:   69,   71,   75 ms
            one.equals(three);                      // reflection:  3434, 3080, 3154 ms
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
        stopwatch.stop();
    }

    @Test
    public void toStringBenchmark() throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();

        for (int i = 0; i < 1_000_000; i++) {       // traditional: 1974, 2088, 1832 ms
            one.toString();                         // reflection:  7414, 7488, 7346 ms
        }
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
        stopwatch.stop();
    }

}