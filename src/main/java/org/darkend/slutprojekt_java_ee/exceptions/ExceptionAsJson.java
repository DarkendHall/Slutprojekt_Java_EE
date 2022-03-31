package org.darkend.slutprojekt_java_ee.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public record ExceptionAsJson(String timestamp, HttpStatus status, String message) {

    public String getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ExceptionAsJson{" +
                "timestamp='" + timestamp + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExceptionAsJson that = (ExceptionAsJson) o;
        return Objects.equals(timestamp, that.timestamp) && status == that.status && Objects.equals(
                message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, status, message);
    }
}
