package boot.dynamodb.config;

import boot.dynamodb.model.Comment;
import boot.dynamodb.model.User;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DynamicTableNameResolver.class})
@TestPropertySource(properties = {
        "dynamodb.table.name.comment=it-dynamic-Comment-table"
})
public class DynamicTableNameResolverTest {

    private DynamoDBMapperConfig emptyConfig;

    private DynamicTableNameResolver resolver;

    @Autowired
    private Environment realEnvironment;

    private Environment spyEnvironment;

    @Before
    public void setUp() {
        spyEnvironment = spy(realEnvironment);
        resolver = new DynamicTableNameResolver(spyEnvironment);
    }

    @Test
    public void classWithoutProperAnnotationCausesException() {
        assertThatThrownBy(() ->
                resolver.getTableName(Mock.class, emptyConfig)
        ).hasMessage("There is no TableNameResolver annotation with Mock.class")
                .isInstanceOf(TableNameResolver.NoTableNameResolverException.class);
    }

    @Test
    public void getTableNameRegisterTableFromClassAnnotation() {
        assertThat(resolver.getTableName(Comment.class, emptyConfig)).isEqualTo("it-dynamic-Comment-table");
        assertThat(resolver.getTableName(Comment.class, emptyConfig)).isEqualTo("it-dynamic-Comment-table");
        assertThat(resolver.getTableName(Comment.class, emptyConfig)).isEqualTo("it-dynamic-Comment-table");

        verify(spyEnvironment, times(1)).getProperty("dynamodb.table.name.comment");
    }

    @Test
    public void propertyForUserTableWasForgottenThenExceptionWillBeThrown() {
        assertThatThrownBy(() ->
                resolver.getTableName(User.class, emptyConfig)
        ).hasMessage("No property found 'dynamodb.table.name.user' to configure dynamo table")
                .isInstanceOf(TableNameResolver.NoPropertyValueTableNameResolverException.class);
    }

}