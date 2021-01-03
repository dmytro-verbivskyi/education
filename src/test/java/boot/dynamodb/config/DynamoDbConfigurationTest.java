package boot.dynamodb.config;

import boot.dynamodb.util.DynamicTableNameResolver;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DynamoDbConfiguration.class, DynamicTableNameResolver.class})
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000",
//        "amazon.aws.accesskey=access",
//        "amazon.aws.secretkey=secret"
})
public class DynamoDbConfigurationTest {

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Ignore //todo: need to be fixed!
    @Test
    public void ifKeysAreMissingThenInstanceProfileCredentialsGoingToBeUsedInsideClient() throws Exception {
        assertThatThrownBy(() -> amazonDynamoDB.listTables())
                .hasMessage("Unable to load credentials from service endpoint")
                .isInstanceOf(SdkClientException.class);
    }
}