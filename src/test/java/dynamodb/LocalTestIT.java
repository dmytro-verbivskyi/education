package dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import dynamodb.util.LocalDynamoDBCreationRule;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class LocalTestIT {

    @ClassRule
    public static final LocalDynamoDBCreationRule dynamoDB = new LocalDynamoDBCreationRule();

    private static AmazonDynamoDB dynamoClient;

    private static final String MY_TABLE_NAME = "Movies";

    // README
    // first run: ./gradlew clean test
    // it will copy libs with custom gradle task properly. Then you can develop your tests.

    @BeforeClass
    public static void setupTable() {
        dynamoClient = dynamoDB.getAmazonDynamoDB();

        System.out.println("Attempting to create table; please wait...");
        CreateTableResult createTableResult = dynamoClient.createTable(
                Arrays.asList(
                        new AttributeDefinition("year", ScalarAttributeType.N),
                        new AttributeDefinition("title", ScalarAttributeType.S)),
                MY_TABLE_NAME,
                Arrays.asList(
                        new KeySchemaElement("year", KeyType.HASH),     // Partition key
                        new KeySchemaElement("title", KeyType.RANGE))   // Sort key
                , new ProvisionedThroughput(10L, 10L));

        TableDescription tableDescription = createTableResult.getTableDescription();
        assertThat(tableDescription.getTableStatus()).isEqualTo("ACTIVE");
        assertThat(tableDescription.getTableName()).isEqualTo(MY_TABLE_NAME);
        assertThat(tableDescription.getItemCount()).isZero();
    }

    @Test
    public void listTables() throws Exception {
        ListTablesResult listTablesResult = dynamoClient.listTables();

        assertThat(listTablesResult).isNotNull();
        assertThat(listTablesResult.getTableNames()).containsOnly(MY_TABLE_NAME);
    }

}
