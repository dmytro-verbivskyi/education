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
import java.util.Optional;

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
            .setOwner(new Owner()
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
        assertThat(review.getId()).isNullOrEmpty();
        repository.save(review);
        assertThat(review.getId()).isNotNull();

        Optional<Review> result = repository.findById(review.getId());
        assertThat(result).isPresent().hasValue(review);
    }

    @Test
    public void findByReviewId() throws Exception {
        repository.save(review);
        repository.save(review.setId(null));

        assertThat(repository.findByReviewId(APPROVAL_ID)).hasSize(2);
    }

    @Test
    public void shouldFindByReviewIdAndApprover() throws Exception {
        repository.save(review);

        Review actual = repository.findByReviewIdAndOwnerId(APPROVAL_ID, APPROVER_ID);

        assertThat(actual).isNotNull();
//        assertThat(actual.getReviewId()).isEqualTo(CART_REVIEW.getReviewId());
//        assertThat(actual.getUser()).isEqualTo(CART_REVIEW.getUser());
//        assertThat(actual.getAssets()).isEqualTo(CART_REVIEW.getAssets());
//        assertThat(actual.getStatus()).isEqualTo(CART_REVIEW.getStatus());
//        assertThat(actual.getCreateDate()).isEqualTo(CART_REVIEW.getCreateDate());
//        assertThat(actual.getUpdateDate()).isEqualTo(CART_REVIEW.getUpdateDate());
    }
/*
    @Test
    public void shouldNotFindByCartReviewIdAndApprover() throws Exception {
        CartReview cartReview = CART_REVIEW;
        repository.save(cartReview);
        assertThat(repository.findByReviewIdAndUserId(APPROVAL_ID, UNKNOWN_APPROVER_ID)).isNull();
    }
    */
}