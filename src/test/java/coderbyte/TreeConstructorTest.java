package coderbyte;

import org.junit.Test;

import static coderbyte.TreeConstructor.TreeConstructor;
import static org.assertj.core.api.Assertions.assertThat;

public class TreeConstructorTest {

    @Test
    public void first() {
        assertThat(TreeConstructor("(1,2)", "(2,4)", "(7,2)")).isEqualTo("true");
    }

    @Test
    public void second() {
        assertThat(TreeConstructor("(1,2)", "(2,4)", "(5,7)", "(7,2)", "(9,5)")).isEqualTo("true");
        /*   4 - 2 - 7 - 5 - 9
                  \
                   1         */
    }

    @Test
    public void third() {
        assertThat(TreeConstructor("(1,2)", "(3,2)", "(2,12)", "(5,2)")).isEqualTo("false");
        /*  12 - 2 - 3
                / \
               5   1         */
    }

    @Test
    public void forth() {
        assertThat(TreeConstructor("(1,2)", "(3,2)", "(2,12)", "(5,7), (4,5)")).isEqualTo("false");
        /*  12 - 2 - 3      7 - 5 - 4
                  \
                   1         */
    }

    @Test
    public void fifth() {
        assertThat(TreeConstructor("(1,2)", "(3,2)", "(2,12)", "(5,3)", "(6,5)", "(4,1)", "(6,4)")).isEqualTo("false");
        /*  12 - 2 - 3 - 5
                  \       \
                   1 - 4 - 6        */
    }
}