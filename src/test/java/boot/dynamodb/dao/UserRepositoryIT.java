package boot.dynamodb.dao;

import boot.dynamodb.config.DynamoDbConfiguration;
import boot.dynamodb.model.User;
import boot.dynamodb.util.DynamicTableNameResolver;
import boot.dynamodb.util.LocalDynamoDBCreationRule;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static boot.dynamodb.util.LocalDynamoDBCreationRule.Client.WILL_BE_PROVIDED_BY_SPRING;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DynamoDbConfiguration.class, DynamicTableNameResolver.class})
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000",
        "amazon.aws.accesskey=access",
        "amazon.aws.secretkey=secret",
        "amazon.aws.region=us-west-2",
        "dynamodb.table.name.comment=comment",
        "dynamodb.table.name.user=SomeDynamic-User-table"
})
public class UserRepositoryIT {

    @ClassRule
    public static LocalDynamoDBCreationRule localDynamodb = new LocalDynamoDBCreationRule(WILL_BE_PROVIDED_BY_SPRING);

    @Value("${dynamodb.table.name.user}")
    private String userTableName;

    @Autowired
    private UserRepository repository;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Before
    public void init() {
        localDynamodb.setDynamoClient(amazonDynamoDB);
        localDynamodb.deleteTable(userTableName);
        localDynamodb.createTable(User.class, userTableName);
    }

    @Test
    public void sampleTestCase() {
        User dave = new User("Dave", "Matthews");
        repository.save(dave);

        User carter = new User("Carter", "Beauford");
        repository.save(carter);

        List<User> result = repository.findByLastName("Matthews");

        assertThat(result).hasSize(1).containsOnly(dave);
    }

}

