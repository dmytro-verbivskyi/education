package codewars;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SortTheOddTest {

    @Test
    public void exampleTest1() {
        assertThat(SortTheOdd.sortArray(new int[]{5, 3, 2, 8, 1, 4})).containsExactly(1, 3, 2, 8, 5, 4);
    }

    @Test
    public void exampleTest2() {
        assertThat(SortTheOdd.sortArray(new int[]{5, 3, 1, 8, 0})).containsExactly(1, 3, 5, 8, 0);
    }

    @Test
    public void exampleTest3() {
        assertThat(SortTheOdd.sortArray(new int[]{})).isEmpty();
    }

    @Test
    public void onlyEven() {
        assertThat(SortTheOdd.sortArray(new int[]{2, 4})).containsExactly(2, 4);
    }

    @Test
    public void onlyOdd() {
        assertThat(SortTheOdd.sortArray(new int[]{5, 3})).containsExactly(3, 5);
    }
}
