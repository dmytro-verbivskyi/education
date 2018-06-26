package dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.google.common.collect.ImmutableMap;
import dynamodb.util.LocalDynamoDBCreationRule;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@RunWith(JUnit4.class)
public class LocalTestIT {

    @ClassRule
    public static final LocalDynamoDBCreationRule dynamoDB = new LocalDynamoDBCreationRule();
    public static final String YEAR = "year";
    public static final String TITLE = "title";
    public static final String INFO = "info";

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

    @Test
    public void crud() {
        DynamoDB dynamoDB = new DynamoDB(dynamoClient);
        Table table = dynamoDB.getTable(MY_TABLE_NAME);

        int year = 2015;
        String title = "The Big New Movie";
        Map<String, Object> infoMap = ImmutableMap.of(
                "plot", "Nothing happens at all.",
                "rating", 0);

        // =========== C - create ===========
        PutItemOutcome putResult = table.putItem(new Item()
                .withPrimaryKey(YEAR, year, TITLE, title)
                .withMap(INFO, infoMap));

        assertThat(putResult.getItem()).isNull();
        assertThat(putResult.getPutItemResult()).isNotNull();

        // =========== R - read ===========
        GetItemSpec spec = new GetItemSpec()
                .withPrimaryKey(YEAR, year, TITLE, title);
        Item item = table.getItem(spec);

        assertThat(item).isNotNull();
        assertThat(item.get(YEAR)).isEqualTo(BigDecimal.valueOf(2015));
        assertThat(item.get(TITLE)).isEqualTo("The Big New Movie");
        HashMap<String, Object> infoData = (HashMap<String, Object>) item.get(INFO);
        assertThat(infoData).hasSize(2)
                .containsEntry("plot", "Nothing happens at all.")
                .containsEntry("rating", BigDecimal.ZERO);

        // =========== U - update ===========
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(YEAR, year, TITLE, title)
                .withUpdateExpression("set info.rating = :r, info.plot = :p, info.actors = :a")
                .withValueMap(new ValueMap()
                        .withNumber(":r", 5.5)
                        .withString(":p", "Everything happens all at once.")
                        .withList(":a", Arrays.asList("Larry", "Moe", "Curly")))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        UpdateItemOutcome updateOutcome = table.updateItem(updateItemSpec);
        HashMap<String, Object> updatedInfoData = (HashMap<String, Object>) updateOutcome.getItem().get(INFO);
        assertThat(updatedInfoData).hasSize(3)
                .containsEntry("plot", "Everything happens all at once.")
                .containsEntry("rating", BigDecimal.valueOf(5.5))
                .containsEntry("actors", Arrays.asList("Larry", "Moe", "Curly"));

        // =========== U2 - advanced Atomic increment / decrement ===========
        UpdateItemSpec atomicUpdateSpec = new UpdateItemSpec()
                .withPrimaryKey(YEAR, year, TITLE, title)
                .withUpdateExpression("set info.rating = info.rating + :val")
                .withValueMap(new ValueMap()
                        .withNumber(":val", 4.1))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        UpdateItemOutcome atomicUpdateOutcome = table.updateItem(atomicUpdateSpec);
        HashMap<String, Object> atomicInfoData = (HashMap<String, Object>) atomicUpdateOutcome.getItem().get(INFO);
        assertThat(atomicInfoData)
                .containsEntry("rating", BigDecimal.valueOf(9.6));

        // =========== U3 - conditional features for update ===========
        UpdateItemSpec conditionalUpdateSpec = new UpdateItemSpec()
                .withPrimaryKey(YEAR, year, TITLE, title)
                .withUpdateExpression("remove info.actors[0]")
                .withConditionExpression("size(info.actors) > :num")
                .withValueMap(new ValueMap()
                        .withNumber(":num", 3))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        assertThatThrownBy(() -> table.updateItem(conditionalUpdateSpec))
                .isInstanceOf(ConditionalCheckFailedException.class)
                .hasMessageContaining("The conditional request failed");

        conditionalUpdateSpec.withConditionExpression("size(info.actors) >= :num");
        UpdateItemOutcome conditionalOutcome = table.updateItem(conditionalUpdateSpec);

        HashMap<String, Object> conditionalInfoData = (HashMap<String, Object>) conditionalOutcome.getItem().get(INFO);
        assertThat(conditionalInfoData)
                .containsEntry("actors", Arrays.asList("Moe", "Curly"));

        // =========== D - delete ===========
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey(YEAR, year, TITLE, title))
                .withConditionExpression("info.rating <= :val")
                .withValueMap(new ValueMap().withNumber(":val", 10));

        DeleteItemOutcome deleteOutcome = table.deleteItem(deleteItemSpec);
        assertThat(deleteOutcome.getDeleteItemResult()).isNotNull();
    }
}
