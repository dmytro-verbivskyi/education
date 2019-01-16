package assertj;

import org.assertj.core.data.Offset;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PrimitivesAndWrappersTest {

    @Test
    public void withBoolean() {
        assertThat(Boolean.TRUE).isTrue().isNotEqualTo(false);

        assertThat(Boolean.FALSE).isFalse().isNotNull();

        assertThatThrownBy(() ->
                assertThat((Boolean) null).isFalse()
        )
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Expecting actual not to be null");
    }

    @Test
    public void withChar() {
        assertThat('μ').inUnicode().isBetween('λ', 'ξ');
    }

    @Test
    public void withNumber() {
        assertThat(Long.MIN_VALUE).isNotZero().isNegative().isLessThanOrEqualTo(42L).isEqualTo(0x7fffffffffffffffL + 1);

        assertThat(102d)
                .isEqualTo(Double.valueOf("102"))
                .isCloseTo(100d, Offset.offset(2d))
                .hasSameHashCodeAs(Double.parseDouble("102"))
                .isNotNaN()
                .hasToString("102.0");
    }

    @Test
    public void withStrings() {
        assertThat("  ").isBlank().hasSize(2).containsOnlyWhitespaces().doesNotContain("hello");

        assertThat("").isBlank().matches(s -> s.isEmpty()).isEmpty();

        assertThat((String) null).isNullOrEmpty();

        assertThat("hello" + System.lineSeparator() + "world")
                .hasLineCount(2).isNotNull().startsWith("he").endsWith("ld");
    }

    @Test
    public void withArray() {
        int[][] array = new int[][]{{1, 2, 3}, {4, 5, 6}};

        assertThat(array)
                .hasSize(2)
                .allMatch(ints -> {
                    int sum = Arrays.stream(ints).sum();
                    return sum == 6 || sum == 15;
                })
                .isEqualTo(new int[][]{{1, 2, 3}, {4, 5, 6}});
    }
}
