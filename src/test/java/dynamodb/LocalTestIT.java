package dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import dynamodb.util.LocalDynamoDBCreationRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class LocalTestIT {

    @ClassRule
    public static final LocalDynamoDBCreationRule dynamoDB = new LocalDynamoDBCreationRule();

    private AmazonDynamoDB dynamoClient = dynamoDB.getAmazonDynamoDB();

    // README
    // first run: ./gradlew clean test
    // it will copy libs with custom gradle task properly. Then you can develop your tests.

    @Test
    public void emptyDatabase() throws Exception {
        ListTablesResult listTablesResult = dynamoClient.listTables();

        assertThat(listTablesResult).isNotNull();
        assertThat(listTablesResult.getTableNames()).isEmpty();
    }
//
//    @Test
//    public void selectFromTable() throws Exception {
//        ListTablesResult listTablesResult = dynamoClient.listTables();
//
//        assertThat(listTablesResult).isNotNull();
//        assertThat(listTablesResult.getTableNames()).isEmpty();
//
//        createSomeTables();
//
//        listTablesResult = dynamoClient.listTables();
//
//        assertThat(listTablesResult).isNotNull();
//        assertThat(listTablesResult.getTableNames()).isEmpty();
//    }

//    private void createSomeTables() {
//        DynamoDBMapper mapper = new DynamoDBMapper(dynamoClient);
//
//        CreateTableRequest tableRequest = mapper.generateCreateTableRequest(EntityOne.class);
//        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
//        dynamoClient.createTable(tableRequest);
//    }
//
//    @DynamoDBTable(tableName = "entityOne")
//    private class EntityOne {
//        public String id;
//        public String title;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//    }
}
