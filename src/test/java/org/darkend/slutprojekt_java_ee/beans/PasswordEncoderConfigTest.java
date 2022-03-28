package org.darkend.slutprojekt_java_ee.beans;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

class PasswordEncoderConfigTest {

    @Test
    void passwordEncoderShouldReturnPasswordEncoder() {
        assertThat(new PasswordEncoderConfig().passwordEncoder()).isInstanceOf(PasswordEncoder.class);
    }

}
