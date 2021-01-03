package boot.dynamodb.dao;

import boot.dynamodb.config.DynamoDbConfiguration;
import boot.dynamodb.model.Comment;
import boot.dynamodb.util.DynamicTableNameResolver;
import boot.dynamodb.util.LocalDynamoDBCreationRule;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
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

import java.util.Arrays;
import java.util.Optional;

import static boot.dynamodb.util.LocalDynamoDBCreationRule.Client.WILL_BE_PROVIDED_BY_SPRING;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DynamoDbConfiguration.class, DynamicTableNameResolver.class})
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000",
        "amazon.aws.accesskey=access",
        "amazon.aws.secretkey=secret",
        "dynamodb.table.name.comment=SomeDynamic-Comment-table",
})
@Ignore //todo: need to be fixed!
public class CommentRepositoryIT {

    @ClassRule
    public static LocalDynamoDBCreationRule localDynamodb = new LocalDynamoDBCreationRule(WILL_BE_PROVIDED_BY_SPRING);

    @Value("${dynamodb.table.name.comment}")
    private String commentTableName;

    @Autowired
    private CommentRepository repository;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Before
    public void init() {
        localDynamodb.setDynamoClient(amazonDynamoDB);
        localDynamodb.deleteTable(commentTableName);
        localDynamodb.createTable(Comment.class, commentTableName);
    }

    @Test
    public void findById() {
        Comment one = commentWithUser("user");
        repository.save(one);

        final String idOfComment = one.getId();

        Optional<Comment> result = repository.findById(idOfComment);
        assertThat(result).isPresent().hasValue(one);
    }

    @Test
    public void findByIdReturnsEmptyOptional() {
        final String idOfComment = "wrong-id";

        Optional<Comment> result = repository.findById(idOfComment);
        assertThat(result).isNotPresent();
    }

    @Test
    public void findByUserId() {
        String userOne = "user1";
        String userTwo = "user2";

        repository.saveAll(Arrays.asList(
                commentWithUser(userOne),
                commentWithUser(userOne),
                commentWithUser(userOne),
                commentWithUser(userOne),
                commentWithUser(userTwo)
        ));

        assertThat(repository.findByUserId(userOne)).isNotEmpty().hasSize(4);
        assertThat(repository.findByUserId(userTwo)).isNotEmpty().hasSize(1);
        assertThat(repository.findByUserId("wrong")).isEmpty();
    }

    @Test
    public void findByCollectionIdAndAssetIdNotNull() {
        String collectionOne = "collection1";

        repository.saveAll(Arrays.asList(
                commentWithCollectionAndAsset(collectionOne, "Something"),
                commentWithCollectionAndAsset(collectionOne, "not null"),
                commentWithCollectionAndAsset(collectionOne, null),
                commentWithCollectionAndAsset(collectionOne, "Even empty are not allowed"),
                commentWithCollectionAndAsset(collectionOne, "")
        ));

        assertThat(repository.findByCollectionIdAndAssetIdNotNull(collectionOne)).isNotEmpty().hasSize(3);
        assertThat(repository.findByCollectionIdAndAssetIdNotNull("otherCollection")).isEmpty();
    }

    private Comment commentWithCollectionAndAsset(String collectionId, String assetId) {
        Comment comment = new Comment();
        comment.setCollectionId(collectionId);
        comment.setAssetId(assetId);
        return comment;
    }

    private Comment commentWithUser(String userId) {
        Comment comment = new Comment();
        comment.setUserId(userId);
        return comment;
    }

}