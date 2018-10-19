package completablefuture;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GreetServiceTest {

    @Test
    public void name() throws Exception {
        GreetService greetService  = new GreetService();

        assertThat(greetService.getGreeting("UA").get().getGreet()).isEqualTo("Привіт");
    }
}