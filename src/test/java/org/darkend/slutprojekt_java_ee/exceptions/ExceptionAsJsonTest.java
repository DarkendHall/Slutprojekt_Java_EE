package org.darkend.slutprojekt_java_ee.exceptions;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionAsJsonTest {

    ExceptionAsJson json = new ExceptionAsJson("time", HttpStatus.NOT_FOUND, "msg");

    @Test
    void timestamp() {
        assertThat(json.timestamp()).isEqualTo("time");
    }

    @Test
    void status() {
        assertThat(json.status()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void message() {
        assertThat(json.message()).isEqualTo("msg");
    }

    @Test
    void testToString() {
        assertThat(json).hasToString("ExceptionAsJson[timestamp=time, status=404 NOT_FOUND, message=msg]");
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple()
                .forClass(ExceptionAsJson.class)
                .verify();
    }
}
