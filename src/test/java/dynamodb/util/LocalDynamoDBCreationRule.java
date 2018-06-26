package dynamodb.util;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.junit.rules.ExternalResource;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Creates a local DynamoDB instance for testing.
 */
public class LocalDynamoDBCreationRule extends ExternalResource {

    private DynamoDBProxyServer server;
    private AmazonDynamoDB amazonDynamoDB;

    public LocalDynamoDBCreationRule() {
        // first run: ./gradlew clean test
        // it will copy libs with custom gradle task properly. Then you can develop your tests.
        System.setProperty("sqlite4java.library.path", "./build/libs/");
    }

    @Override
    protected void before() throws Throwable {
        try {
            final String port = getAvailablePort();
            this.server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory", "-port", port});
            server.start();
            amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                            "access", "secret"))
                    )
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                            "http://localhost:" + port, "us-west-1")
                    )
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public AmazonDynamoDB getAmazonDynamoDB() {
        return amazonDynamoDB;
    }

    private String getAvailablePort() {
        try (final ServerSocket serverSocket = new ServerSocket(0)) {
            return String.valueOf(serverSocket.getLocalPort());
        } catch (IOException e) {
            throw new RuntimeException("Available port was not found", e);
        }
    }
}
