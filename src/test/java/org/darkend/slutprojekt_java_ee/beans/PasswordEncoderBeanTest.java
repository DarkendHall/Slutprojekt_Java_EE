package org.darkend.slutprojekt_java_ee.beans;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

class PasswordEncoderBeanTest {

    @Test
    void passwordEncoderShouldReturnPasswordEncoder() {
        assertThat(new PasswordEncoderBean().passwordEncoder()).isInstanceOf(PasswordEncoder.class);
    }

}
