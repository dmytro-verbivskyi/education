package codewars;

import com.google.common.collect.ImmutableMap;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

class HumanReadableDuration {

    private static final ImmutableMap<TimeUnit, Duration> DURATIONS = ImmutableMap.<TimeUnit, Duration>builder()
            .put(DAYS, new Duration("day", " "))
            .put(HOURS, new Duration("hour", " "))
            .put(MINUTES, new Duration("minute", " "))
            .put(SECONDS, new Duration("second", " and "))
            .build();

    static String formatDuration(int seconds) {
        StringBuilder result = new StringBuilder();
        int amountOfDays = seconds / 86400;
        int amountOfHours = (seconds - (amountOfDays * 86400)) / 3600;
        int amountOfMinutes = (seconds - (amountOfDays * 86400) - (amountOfHours * 3600)) / 60;
        int amountOfSeconds = seconds - (amountOfDays * 86400) - (amountOfHours * 3600) - (amountOfMinutes * 60);

        if (amountOfDays > 0) {
            apply(result, amountOfDays, DAYS);
        }
        if (amountOfHours > 0) {
            apply(result, amountOfHours, HOURS);
        }
        if (amountOfMinutes > 0) {
            apply(result, amountOfMinutes, MINUTES);
        }
        if (amountOfSeconds > 0) {
            apply(result, amountOfSeconds, SECONDS);
        }

        String str = result.toString();
        return str.isEmpty() ? "now" : str;
    }

    private static void apply(StringBuilder result, int amount, TimeUnit unit) {
        boolean isPlural = (amount != 1);
        result.append(result.length() > 0 ? DURATIONS.get(unit).appender : "")
                .append(amount).append(" ")
                .append(DURATIONS.get(unit).name).append((isPlural ? "s" : ""));
    }

    private static class Duration {
        String name;
        String appender;

        Duration (String name, String appender) {
            this.name = name;
            this.appender = appender;
        }
    }
}
