package codewars;

import org.junit.Test;

import static codewars.HumanReadableDuration.formatDuration;
import static org.assertj.core.api.Assertions.assertThat;

public class HumanReadableDurationTest {

    @Test
    public void nowTest() throws Exception {
        assertThat(formatDuration(0)).isEqualTo("now");
    }

    @Test
    public void secondsTest() throws Exception {
        assertThat(formatDuration(1)).isEqualTo("1 second");
        assertThat(formatDuration(11)).isEqualTo("11 seconds");
        assertThat(formatDuration(59)).isEqualTo("59 seconds");
    }

    @Test
    public void minutesTest() throws Exception {
        assertThat(formatDuration(60)).isEqualTo("1 minute");
        assertThat(formatDuration(120)).isEqualTo("2 minutes");
    }

    @Test
    public void minutesAndSecondsTest() throws Exception {
        assertThat(formatDuration(61)).isEqualTo("1 minute and 1 second");
        assertThat(formatDuration(119)).isEqualTo("1 minute and 59 seconds");
        assertThat(formatDuration(125)).isEqualTo("2 minutes and 5 seconds");
        assertThat(formatDuration(3541)).isEqualTo("59 minutes and 1 second");
    }

    @Test
    public void hoursTest() throws Exception {
        assertThat(formatDuration(3600)).isEqualTo("1 hour");
        assertThat(formatDuration(10800)).isEqualTo("3 hours");
    }

    @Test
    public void hoursMinutesAndSecondsTest() throws Exception {
        assertThat(formatDuration(3661)).isEqualTo("1 hour 1 minute and 1 second");
        assertThat(formatDuration(64651)).isEqualTo("17 hours 57 minutes and 31 seconds");
    }

    @Test
    public void daysTest() throws Exception {
        assertThat(formatDuration(86400)).isEqualTo("1 day");
        assertThat(formatDuration(259200)).isEqualTo("3 days");
    }
}
