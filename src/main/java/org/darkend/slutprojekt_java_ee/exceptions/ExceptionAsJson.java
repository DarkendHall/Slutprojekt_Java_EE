package org.darkend.slutprojekt_java_ee.exceptions;

import org.springframework.http.HttpStatus;

public class ExceptionAsJson {

    private final String timestamp;
    private final HttpStatus status;
    private final String message;

    public ExceptionAsJson(String timestamp, HttpStatus status, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
