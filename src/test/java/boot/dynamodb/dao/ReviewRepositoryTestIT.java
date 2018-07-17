package boot.dynamodb.dao;

import boot.dynamodb.config.DynamoDbConfiguration;
import boot.dynamodb.model.ApprovalStatus;
import boot.dynamodb.model.Asset;
import boot.dynamodb.model.Owner;
import boot.dynamodb.model.Review;
import boot.dynamodb.util.DynamicTableNameResolver;
import boot.dynamodb.util.LocalDynamoDBCreationRule;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Arrays;

import static boot.dynamodb.util.LocalDynamoDBCreationRule.Client.WILL_BE_PROVIDED_BY_SPRING;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DynamoDbConfiguration.class, DynamicTableNameResolver.class})
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000",
        "amazon.aws.accesskey=access",
        "amazon.aws.secretkey=secret",
        "dynamodb.table.name.review=SomeDynamic-Review-table",
})
public class ReviewRepositoryTestIT {

    private static final String APPROVAL_ID = "4242";
    private static final String APPROVER_ID = "53";

    @ClassRule
    public static LocalDynamoDBCreationRule localDynamodb = new LocalDynamoDBCreationRule(WILL_BE_PROVIDED_BY_SPRING);

    @Value("${dynamodb.table.name.review}")
    private String reviewTableName;

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    private Review review = new Review()
            .setReviewId(APPROVAL_ID)
            .setCreateDate(LocalDateTime.now().toString())
            .setReviewOwner(new Owner()
                    .setId(APPROVER_ID)
                    .setName("Avatar Aang")
                    .setEmail("avatar.aang@nick.com"))
            .setAssets(Lists.newArrayList(
                    new Asset().setAssetId(42L).setStatus(ApprovalStatus.APPROVED),
                    new Asset().setAssetId(772727L).setStatus(ApprovalStatus.PENDING),
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
    public void findByReviewId() {
        repository.save(review);
        repository.save(review.setId(null));

        assertThat(repository.findByReviewId(APPROVAL_ID)).hasSize(2);
    }

    @Test
    public void findByReviewIdAndOwnerId() {
        String initialId = repository.save(review).getId();
        repository.save(review.setId(null).setReviewOwner(new Owner().setId("Other Owner Id")));
        repository.save(review.setId(null).setReviewId("Other Review Id"));

        assertThat(repository.findByReviewIdAndReviewOwnerId(APPROVAL_ID, APPROVER_ID)).hasValueSatisfying(actual -> {
            assertThat(actual.getId()).isEqualTo(initialId);
            assertThat(actual.getReviewId()).isEqualTo(APPROVAL_ID);
            assertThat(actual.getReviewOwner().getId()).isEqualTo(APPROVER_ID);
        });
    }

    @Test
    public void findByReviewIdAndOwnerIdReturnsOne_JustKeepingBackwardsCompatibility() {
        String id_1 = repository.save(review).getId();
        String id_2 = repository.save(review.setId(null)).getId();
        String id_3 = repository.save(review.setId(null)).getId();

        assertThat(repository.findByReviewIdAndReviewOwnerId(APPROVAL_ID, APPROVER_ID)).hasValueSatisfying(actual -> {
            assertThat(actual.getId()).isIn(id_1, id_2, id_3);
            assertThat(actual.getReviewId()).isEqualTo(APPROVAL_ID);
            assertThat(actual.getReviewOwner().getId()).isEqualTo(APPROVER_ID);
        });
    }

    @Test
    public void findByReviewIdAndOwnerIdReturnsEmptyOptional() throws Exception {
        assertThat(repository.findByReviewIdAndReviewOwnerId("unknownReviewId", APPROVER_ID)).isNotPresent();
    }

    @Test
    public void findAllByReviewIdIn() {
        String reviewId_1 = repository.save(review).getReviewId();
        String reviewId_2 = repository.save(review.setId(null)).getReviewId();
        String reviewId_3 = repository.save(review.setId(null)).getReviewId();

        assertThat(repository.findAllByReviewIdIn(Arrays.asList(reviewId_1, reviewId_2, reviewId_3))).hasSize(3);
    }
}