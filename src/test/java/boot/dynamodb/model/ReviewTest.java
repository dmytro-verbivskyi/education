package boot.dynamodb.model;

import org.apache.commons.lang3.SerializationUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.time.LocalDateTime;

import static boot.dynamodb.model.ApprovalStatus.APPROVED;
import static boot.dynamodb.model.ApprovalStatus.REJECTED;
import static boot.dynamodb.model.ReviewStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;

public class ReviewTest {

    @Test
    public void coverEntity() throws Exception {
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
                .hasToString("Review{id='id',"
                        + " reviewId='reviewId',"
                        + " createDate='-999999999-01-01T00:00',"
                        + " reviewOwner=Owner{id='owner Id', name='owner Name', email='owner Email'},"
                        + " status=PENDING,"
                        + " assets=[Asset{assetId=42, status=APPROVED}, Asset{assetId=567, status=PENDING}]"
                        + "}");
    }
}