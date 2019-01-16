package assertj;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionalAssertTest {

    @Test
    public void optionalTest() throws Exception {
        assertThat(Optional.ofNullable(null)).isNotPresent();

        assertThat(Optional.ofNullable("Hello"))
                .isNotNull()
                .isPresent()
                .hasValue("Hello")
                .hasValueSatisfying(s -> assertThat(s).startsWith("Hell").endsWith("o"));
    }

    @Test
    public void failingTestProduceReadableOutput() throws Exception {
        try {
            String nullStr = null;

            assertThat(Optional.ofNullable(nullStr))
                    .as("This will be a title of failing test. It works %s String.printf() %d",
                            "like method", 42)
                    .hasValueSatisfying(s -> assertThat(s).isEqualTo("Hello"));
        } catch (AssertionError e) {
            System.out.println(e);
        }
    }
}
