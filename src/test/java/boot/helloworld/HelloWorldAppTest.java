package boot.helloworld;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldAppTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void contextLoadsSuccessfully() {
        LOG.info("contextLoadsSuccessfully");
    }

}
