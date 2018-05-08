package codewars;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CountingDuplicatesTest {

    @Test
    public void abcdeReturnsZero() {
        assertThat(CountingDuplicates.duplicateCount("abcde")).isZero();
    }

    @Test
    public void abcdeaReturnsOne() {
        assertThat(CountingDuplicates.duplicateCount("abcdea")).isEqualTo(1);
    }

    @Test
    public void indivisibilitReturnsOne() {
        assertThat(CountingDuplicates.duplicateCount("indivisibility")).isEqualTo(1);
    }

    @Test
    public void indivisibilitiesReturnsTwo() {
        assertThat(CountingDuplicates.duplicateCount("indivisibilities")).isEqualTo(2);
    }

    @Test
    public void twoDuplicatesReturnsTwo() {
        assertThat(CountingDuplicates.duplicateCount("aA11")).isEqualTo(2);
    }
}
