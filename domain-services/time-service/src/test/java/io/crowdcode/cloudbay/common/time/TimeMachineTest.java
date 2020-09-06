package io.crowdcode.cloudbay.common.time;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Ingo Düppe (Crowdcode)
 */
class TimeMachineTest {

    @Test
    void testMinusFixed() throws Exception {
        TimeMachine.setFixed();
        LocalDateTime now = TimeMachine.now();
        TimeMachine.clockMinus(2, ChronoUnit.MINUTES);
        LocalDateTime before = TimeMachine.now();
        assertThat(before.isBefore(now)).isTrue();

    }
}