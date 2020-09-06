package io.crowdcode.cloudbay.common.time;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class TimeMachine {

    private static Clock clock = Clock.systemDefaultZone();

    public static LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

    public static void setClock(Clock clock) {
        TimeMachine.clock = clock;
    }

    public static void setFixedOn(Instant instant) {
        TimeMachine.clock = Clock.fixed(instant, ZoneId.systemDefault());
    }

    public static void clockPlus(long amount, ChronoUnit chronoUnit) {
        setFixedOn(clock.instant().plus(amount, chronoUnit));
    }

    public static void clockMinus(long amount, ChronoUnit chronoUnit) {
        setFixedOn(clock.instant().minus(amount, chronoUnit));
    }

    public static void setFixed() {
        setFixedOn(Instant.now());
    }
}
