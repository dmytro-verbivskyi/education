package boot.dynamodb.util;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.junit.rules.ExternalResource;

import static java.util.Objects.requireNonNull;

/**
 * Creates a local DynamoDB instance for testing.
 */
public class LocalDynamoDBCreationRule extends ExternalResource {

    private DynamoDBProxyServer server;
    private AmazonDynamoDB dynamoClient;

    public enum Client {
        WILL_BE_PROVIDED_BY_SPRING, PLEASE_CREATE
    }

    private final Client clientOption;

    public LocalDynamoDBCreationRule(Client clientOption) {
        this.clientOption = clientOption;
        // ===== IMPORTANT =====
        // first run: ./gradlew clean test
        // it will copy libs with custom gradle task properly. Then you can develop your tests.
        System.setProperty("sqlite4java.library.path", "./build/libs/");
    }

    @Override
    protected void before() {
        try {
            this.server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory"});
            server.start();

            if (clientOption == Client.WILL_BE_PROVIDED_BY_SPRING) {
                return;
            }
            setDynamoClient(AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                            "access", "secret"))
                    )
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                            "http://localhost:8000", "us-west-1")
                    )
                    .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setDynamoClient(AmazonDynamoDB dynamoClient) {
        this.dynamoClient = dynamoClient;
    }

    public AmazonDynamoDB getDynamoClient() {
        return dynamoClient;
    }

    @Override
    protected void after() {
        if (server == null) {
            return;
        }
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTable(String tableName) {
        requireNonNull(dynamoClient, "To use LocalDynamoDb helper setDynamoClient() created by Spring");

        dynamoClient.listTables().getTableNames().stream()
                .filter(name -> name.equals(tableName))
                .forEach(name -> dynamoClient.deleteTable(name));
    }

    public void createTable(Class entityClass, String tableName) {
        requireNonNull(dynamoClient, "To use LocalDynamoDb helper setDynamoClient() created by Spring");
        dynamoClient.createTable(new DynamoDBMapper(dynamoClient)
                .generateCreateTableRequest(entityClass)
                .withTableName(tableName)
                .withProvisionedThroughput(new ProvisionedThroughput(5L, 5L)));
    }

}
