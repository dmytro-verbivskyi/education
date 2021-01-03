package boot.dynamodb.dao;

import boot.dynamodb.config.DynamoDbConfiguration;
import boot.dynamodb.model.*;
import boot.dynamodb.util.DynamicTableNameResolver;
import boot.dynamodb.util.LocalDynamoDBCreationRule;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

import static boot.dynamodb.model.ApprovalStatus.APPROVED;
import static boot.dynamodb.model.ReviewStatus.CONFIRMED;
import static boot.dynamodb.model.ReviewStatus.PENDING;
import static boot.dynamodb.util.LocalDynamoDBCreationRule.Client.WILL_BE_PROVIDED_BY_SPRING;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DynamoDbConfiguration.class, DynamicTableNameResolver.class})
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000",
        "amazon.aws.accesskey=access",
        "amazon.aws.secretkey=secret",
        "dynamodb.table.name.review=SomeDynamic-Review-table",
})
@Ignore //todo: need to be fixed!
public class ReviewRepositoryTestIT {

    private static final String REVIEW_ID = "4242";
    private static final String UNKNOWN_REVIEW_ID = "7833333";
    private static final String APPROVER_ID = "53";
    private static final Long ASSET_ID = 772727L;
    private static final Long UNKNOWN_ASSET_ID = 99112L;

    @ClassRule
    public static LocalDynamoDBCreationRule localDynamodb = new LocalDynamoDBCreationRule(WILL_BE_PROVIDED_BY_SPRING);

    @Value("${dynamodb.table.name.review}")
    private String reviewTableName;

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    private Review review = new Review()
            .setReviewId(REVIEW_ID)
            .setCreateDate(LocalDateTime.now().toString())
            .setStatus(PENDING)
            .setReviewOwner(new Owner()
                    .setId(APPROVER_ID)
                    .setName("Avatar Aang")
                    .setEmail("avatar.aang@nick.com"))
            .setAssets(Lists.newArrayList(
                    new Asset().setAssetId(42L).setStatus(APPROVED),
                    new Asset().setAssetId(ASSET_ID).setStatus(ApprovalStatus.PENDING),
                    new Asset().setAssetId(82828L).setStatus(ApprovalStatus.REJECTED)
            ));

    @Before
    public void init() {
        localDynamodb.setDynamoClient(amazonDynamoDB);
        localDynamodb.deleteTable(reviewTableName);
        localDynamodb.createTable(Review.class, reviewTableName);
    }

    @Test
    public void findById() {
        assertThat(review.getId()).isNull();
        repository.save(review);
        assertThat(review.getId()).isNotNull();

        assertThat(repository.findById(review.getId())).isPresent().hasValue(review);
    }

    @Test
    public void findByIdNull() throws Exception {
        assertThatIllegalArgumentException().isThrownBy(() ->
                repository.findById(null)
        );
    }

    @Test
    public void findByReviewId() {
        repository.save(review);
        repository.save(review.setId(null));

        assertThat(repository.findByReviewId(REVIEW_ID)).hasSize(2);
    }

    @Test
    public void findByReviewIdNull() throws Exception {
        assertThatIllegalArgumentException().isThrownBy(() ->
                repository.findByReviewId(null)
        ).withMessageStartingWith("Creating conditions on null property values not supported");
    }

    @Test
    public void findByReviewIdAndOwnerId() {
        String initialId = repository.save(review).getId();
        repository.save(review.setId(null).setReviewOwner(new Owner().setId("Other Owner Id")));
        repository.save(review.setId(null).setReviewId("Other Review Id"));

        assertThat(repository.findByReviewIdAndReviewOwnerId(REVIEW_ID, APPROVER_ID)).hasValueSatisfying(actual -> {
            assertThat(actual.getId()).isEqualTo(initialId);
            assertThat(actual.getReviewId()).isEqualTo(REVIEW_ID);
            assertThat(actual.getReviewOwner().getId()).isEqualTo(APPROVER_ID);
        });
    }

    @Test
    public void findByReviewIdAndOwnerIdReturnsOne_JustKeepingBackwardsCompatibility() {
        String id1 = repository.save(review).getId();
        String id2 = repository.save(review.setId(null)).getId();
        String id3 = repository.save(review.setId(null)).getId();

        assertThat(repository.findByReviewIdAndReviewOwnerId(REVIEW_ID, APPROVER_ID)).hasValueSatisfying(actual -> {
            assertThat(actual.getId()).isIn(id1, id2, id3);
            assertThat(actual.getReviewId()).isEqualTo(REVIEW_ID);
            assertThat(actual.getReviewOwner().getId()).isEqualTo(APPROVER_ID);
        });
    }

    @Test
    public void findByReviewIdAndOwnerIdReturnsEmptyOptional() {
        assertThat(repository.findByReviewIdAndReviewOwnerId("unknownReviewId", APPROVER_ID)).isNotPresent();
    }

    @Test
    public void findByReviewIdAndReviewOwnerId_reviewIdBlank() throws Exception {
        assertThatIllegalArgumentException().isThrownBy(() ->
                repository.findByReviewIdAndReviewOwnerId("", null)
        ).withMessage("reviewId cannot be null or blank");
    }

    @Test
    public void findByReviewIdAndReviewOwnerId_ownerIdBlank() throws Exception {
        assertThatIllegalArgumentException().isThrownBy(() ->
                repository.findByReviewIdAndReviewOwnerId("a", "")
        ).withMessage("ownerId cannot be null or blank");
    }

    @Test
    public void findAllByReviewIdIn() {
        repository.save(review);
        repository.save(review.setId(null));
        repository.save(review.setId(null));

        assertThat(repository.findAllByReviewIdIn(singletonList(REVIEW_ID))).hasSize(3);
    }

    @Test
    public void findAllByReviewIdInNull() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                repository.findAllByReviewIdIn(null)
        ).withMessage("Creating conditions on null parameters not supported: please specify a value for 'reviewId'");
    }

    @Test
    public void findAllByReviewIdInEmptyIds() {
        assertThatThrownBy(() ->
                repository.findAllByReviewIdIn(Lists.emptyList())
        ).isInstanceOf(AmazonDynamoDBException.class);
    }

    @Test
    public void updateReviewStatus() {
        assertThat(review.getStatus()).isNotEqualTo(CONFIRMED);
        repository.save(review);
        repository.save(review.setId(null));
        repository.save(review.setId(null));

        assertThat(repository.updateReviewStatus(REVIEW_ID, APPROVER_ID, CONFIRMED)).isEqualTo(3);
        assertThat(repository.findAllByReviewIdIn(singletonList(REVIEW_ID)))
                .hasSize(3)
                .extracting("status").containsOnly(CONFIRMED);
    }

    @Test
    public void updateReviewStatus_statusNull() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                repository.updateReviewStatus(null, null, null)
        ).withMessage("reviewStatus cannot be null");
    }

    @Test
    public void updateReviewStatus_reviewIdNull() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                repository.updateReviewStatus(null, null, ReviewStatus.CONFIRMED)
        ).withMessage("reviewId cannot be null or blank");
    }

    @Test
    public void updateReviewStatus_ownerIdNull() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                repository.updateReviewStatus("s", null, ReviewStatus.CONFIRMED)
        ).withMessage("ownerId cannot be null or blank");
    }

    @Test
    public void updateReviewApprovalStatus() throws Exception {
        assertThat(review.getAssets().get(1)).satisfies(asset -> {
            assertThat(asset.getAssetId()).isEqualTo(ASSET_ID);
            assertThat(asset.getStatus()).isEqualTo(ApprovalStatus.PENDING);
        });
        repository.save(review);
        repository.save(review.setId(null));
        repository.save(review.setId(null));

        Asset assetApprovals = new Asset().setAssetId(ASSET_ID).setStatus(APPROVED);

        assertThat(repository.updateReviewAppovalStatus(REVIEW_ID, APPROVER_ID, assetApprovals)).isEqualTo(3);

        List<Review> allReviewsThatWereUpdated = repository.findAllByReviewIdIn(singletonList(REVIEW_ID));

        List<Asset> expectedAssets = review.getAssets();
        expectedAssets.get(1).setStatus(APPROVED);
        assertThat(allReviewsThatWereUpdated).hasSize(3).satisfies(actualList -> {
            for (Review r : actualList) {
                assertThat(r.getAssets()).isEqualTo(expectedAssets);
            }
        });
    }

    @Test
    public void updateReviewApprovalStatusReturnZeroIfNoReviewForPairOfIdAndOwnerId() throws Exception {
        repository.save(review);
        repository.save(review.setId(null));
        repository.save(review.setId(null));

        Asset assetApprovals = new Asset().setAssetId(ASSET_ID).setStatus(APPROVED);
        assertThat(repository.updateReviewAppovalStatus(UNKNOWN_REVIEW_ID, APPROVER_ID, assetApprovals)).isZero();
    }

    @Test
    public void updateReviewApprovalStatusReturnZeroIfAssetIdDoesNotMatch() throws Exception {
        repository.save(review);
        repository.save(review.setId(null));
        repository.save(review.setId(null));

        Asset assetApprovals = new Asset().setAssetId(UNKNOWN_ASSET_ID).setStatus(APPROVED);
        assertThat(repository.updateReviewAppovalStatus(REVIEW_ID, APPROVER_ID, assetApprovals)).isZero();
    }

    @Test
    public void updateReviewApprovalStatus_assetNull() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                repository.updateReviewAppovalStatus(null, null, null)
        ).withMessage("assetApprovals cannot be null");
    }

    @Test
    public void updateReviewApprovalStatus_assetAssetIdNull() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                repository.updateReviewAppovalStatus(null, null, new Asset())
        ).withMessage("assetId cannot be null");
    }

    @Test
    public void updateReviewApprovalStatus_assetStatusNull() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                repository.updateReviewAppovalStatus(null, null, new Asset().setAssetId(2L))
        ).withMessage("approvalStatus cannot be null");
    }
}