package coderbyte;

import org.junit.Test;

import static coderbyte.TreeConstructor.TreeConstructor;
import static org.assertj.core.api.Assertions.assertThat;

public class TreeConstructorTest {

    @Test
    public void first() {
        assertThat(TreeConstructor("(1111,2)", "(2,44)", "(7,2)")).isEqualTo("true");
    }

    @Test
    public void second() {
        assertThat(TreeConstructor("(1,2)", "(2,4)", "(5,7)", "(7,2)", "(9,5)")).isEqualTo("true");
    }

    @Test
    public void third() {
        assertThat(TreeConstructor("(1,2)", "(3,2)", "(2,12)", "(5,2)")).isEqualTo("false");
    }
}