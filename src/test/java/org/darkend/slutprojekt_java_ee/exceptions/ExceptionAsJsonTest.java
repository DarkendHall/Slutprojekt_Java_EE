package org.darkend.slutprojekt_java_ee.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.*;

class ExceptionAsJsonTest {

    ExceptionAsJson json = new ExceptionAsJson("time", HttpStatus.NOT_FOUND, "msg");

    @Test
    void getTimestamp() {
        assertThat(json.getTimestamp()).isEqualTo("time");
    }

    @Test
    void getStatus() {
        assertThat(json.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getMessage() {
        assertThat(json.getMessage()).isEqualTo("msg");
    }

    @Test
    void testToString() {
        assertThat(json.toString()).isEqualTo("ExceptionAsJson{timestamp='time', status=404 NOT_FOUND, message='msg'}");
    }

    @Test
    void testEquals() {
        assertThat(json).isEqualTo(new ExceptionAsJson("time", HttpStatus.NOT_FOUND, "msg"));
    }

    @Test
    void testHashCode() {
        assertThat(json.hashCode()).isEqualTo(new ExceptionAsJson("time", HttpStatus.NOT_FOUND, "msg").hashCode());
    }
}
