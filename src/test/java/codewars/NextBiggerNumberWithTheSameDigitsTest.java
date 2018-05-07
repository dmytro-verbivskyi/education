package codewars;

import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static codewars.NextBiggerNumberWithTheSameDigits.nextBiggerNumber;
import static codewars.NextBiggerNumberWithTheSameDigits.nextBiggerNumberBestPerformance;
import static org.assertj.core.api.Assertions.assertThat;

public class NextBiggerNumberWithTheSameDigitsTest {

    @Test
    public void wrongVariants() {
        assertThat(nextBiggerNumber(9)).isEqualTo(-1);
        assertThat(nextBiggerNumber(111)).isEqualTo(-1);
        assertThat(nextBiggerNumber(531)).isEqualTo(-1);
    }

    @Test
    public void basicTests() {
        assertThat(nextBiggerNumber(12)).isEqualTo(21);
        assertThat(nextBiggerNumber(513)).isEqualTo(531);
        assertThat(nextBiggerNumber(2017)).isEqualTo(2071);
        assertThat(nextBiggerNumber(414)).isEqualTo(441);
        assertThat(nextBiggerNumber(144)).isEqualTo(414);
        assertThat(nextBiggerNumber(199)).isEqualTo(919);
    }

    @Test
    public void notSmallNumberTest() {
        assertThat(nextBiggerNumber(10010099)).isEqualTo(10010909);
    }

    @Test
    public void notSmall() {
        assertThat(nextBiggerNumber(1234)).isEqualTo(1243);
    }

    @Test
    public void duplicates() {
        assertThat(nextBiggerNumber(199)).isEqualTo(919);
    }

    @Test
    public void performanceTest() {
        int max = 5000;
        long input = 10010099;
        Stopwatch stopwatch = Stopwatch.createStarted();

        for (int i = 0; i < max; i++) {
            nextBiggerNumberBestPerformance(input);
        }
        System.out.printf("betterWay for %d times: %s\n", max, stopwatch.elapsed(TimeUnit.MILLISECONDS));

        stopwatch.reset();
        stopwatch.start();

        for (int i = 0; i < max; i++) {
            nextBiggerNumber(input);
        }
        System.out.printf("myWay for %d times: %s\n", max, stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
