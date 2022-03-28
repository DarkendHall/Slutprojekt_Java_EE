package org.darkend.slutprojekt_java_ee.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ClockBean {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
