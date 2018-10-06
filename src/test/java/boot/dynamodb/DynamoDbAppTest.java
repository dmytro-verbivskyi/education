package boot.dynamodb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dynamodb")
public class DynamoDbAppTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void contextLoadsSuccessfully() {
        LOG.info("contextLoadsSuccessfully");
    }
}