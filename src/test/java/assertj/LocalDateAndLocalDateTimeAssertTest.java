package assertj;

import com.google.common.util.concurrent.Uninterruptibles;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateAndLocalDateTimeAssertTest {

    @Test
    public void withLocalDate() {
        assertThat(LocalDate.now())
                .isToday()
                .isStrictlyBetween(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
    }

    @Test
    public void withLocalDateTime() {
        LocalDateTime nowInTokyo = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
        Uninterruptibles.sleepUninterruptibly(20L, TimeUnit.MILLISECONDS);
        LocalDateTime nowInParis = LocalDateTime.now(ZoneId.of("Europe/Paris"));

        assertThat(nowInTokyo).isAfter(nowInParis);
    }
}
