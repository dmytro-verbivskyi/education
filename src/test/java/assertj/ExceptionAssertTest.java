package assertj;

import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExceptionAssertTest {

    @Test
    public void simpleExceptionValidation() {
        String message = "OMG!";
        assertThatThrownBy(() -> methodWithThrowOfRuntimeException(message))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("OMG!")
                .hasMessageEndingWith("G!")
                .hasNoCause();
    }

    @Test
    public void rethrownExceptionValidation() {
        assertThatThrownBy(() -> {
            try {
                throw new IOException("You must catch me");
            } catch (IOException e) {
                throw new IllegalArgumentException("OMG 42 != 43", e);
            }
        })
                .isNotExactlyInstanceOf(NumberFormatException.class)
                .hasMessage("OMG %d != %d", 42, 43)
                .isInstanceOf(IllegalArgumentException.class)
                .hasCauseInstanceOf(IOException.class)
                .hasStackTraceContaining("You must catch me")
        ;
    }

    private void methodWithThrowOfRuntimeException(String message) {
        throw new RuntimeException(message);
    }
}
