package assertj;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class ExceptionAssertTest {

    @Test
    public void simpleExceptionValidation() {
        assertThatThrownBy(() -> methodWithThrowOfRuntimeException("OMG!"))
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

    private MockService mockBean = Mockito.mock(MockService.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void mockBeanInvocationWithExceptionValidation() throws Exception {
        assertThatThrownBy(() -> {
            mockBean.hello();
            methodWithThrowOfRuntimeException("OMG!");
        })
                .isInstanceOf(RuntimeException.class)
                .hasMessage("OMG!");

        verify(mockBean, times(1)).hello();
        verify(mockBean, never()).helloVoid();
    }

    @Test
    public void wrongMockBeanInvocationWithExceptionValidation() throws Exception {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("OMG!");

        mockBean.hello();
        methodWithThrowOfRuntimeException("OMG!");

        verify(mockBean, times(100500)).hello();
        verify(mockBean, never()).helloVoid();
    }

    private void methodWithThrowOfRuntimeException(String message) {
        throw new RuntimeException(message);
    }

    private class MockService {

        String hello() {
            return "HelloWorld";
        }

        void helloVoid() {
            System.out.println("hello");
        }
    }
}
