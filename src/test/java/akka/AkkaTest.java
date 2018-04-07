package akka;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AkkaTest {

    @Test
    public void helloWorld() {
        AkkaGuy guy = new AkkaGuy();

        assertThat(guy.sayHi()).isEqualTo("Good afternoon");
    }
}
