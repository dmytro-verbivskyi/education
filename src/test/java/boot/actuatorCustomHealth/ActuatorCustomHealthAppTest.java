package boot.actuatorCustomHealth;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("actuatorCustomHealth")
public class ActuatorCustomHealthAppTest {

    @Test
    public void contextLoadsSuccessfully() {
    }

}