package org.darkend.slutprojekt_java_ee.beans;

import org.junit.jupiter.api.Test;

import java.time.Clock;

import static org.assertj.core.api.Assertions.assertThat;

class ClockBeanTest {

    private final ClockBean clockBean = new ClockBean();

    @Test
    void clockBeanIsInstanceOfClock() {
        var result = clockBean.clock();

        assertThat(result).isInstanceOf(Clock.class);
    }

}
