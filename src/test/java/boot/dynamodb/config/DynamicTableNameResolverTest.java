package boot.dynamodb.config;

import boot.dynamodb.model.Comment;
import boot.dynamodb.model.User;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DynamicTableNameResolver.class})
@TestPropertySource(properties = {
        "dynamodb.table.name.comment=it-dynamic-Comment-table",
        "dynamodb.table.name.user=it-dynamic-User-table"
})
public class DynamicTableNameResolverTest {

    @Autowired
    private DynamicTableNameResolver resolver;

    @Mock
    private DynamoDBMapperConfig mockConfig;

    @Test
    public void commentTableNameIsRetrievedFromProperties() {
        assertThat(resolver.getTableName(Comment.class, mockConfig)).isEqualTo("it-dynamic-Comment-table");
    }

    @Test
    public void userTableNameIsRetrievedFromProperties() {
        assertThat(resolver.getTableName(User.class, mockConfig)).isEqualTo("it-dynamic-User-table");
    }

    @Test
    public void unregisteredClassForTableNameWillReturnsNull() {
        assertThat(resolver.getTableName(Mock.class, mockConfig)).isNull();
    }
}