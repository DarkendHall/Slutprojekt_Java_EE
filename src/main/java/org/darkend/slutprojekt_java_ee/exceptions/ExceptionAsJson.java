package org.darkend.slutprojekt_java_ee.exceptions;

import org.springframework.http.HttpStatus;

public record ExceptionAsJson(String timestamp, HttpStatus status, String message) {
}
