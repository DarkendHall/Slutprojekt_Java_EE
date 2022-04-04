package org.darkend.slutprojekt_java_ee.jms.confiq;

import org.junit.jupiter.api.Test;
import org.springframework.jms.support.converter.MessageConverter;

import static org.assertj.core.api.Assertions.assertThat;


class JmsConfigTest {

    private final JmsConfig jmsConfig = new JmsConfig();

    @Test
    void messageConverterIsConfigured() {
        var converter = jmsConfig.messageConverter();

        assertThat(converter).isInstanceOf(MessageConverter.class);
    }
}
