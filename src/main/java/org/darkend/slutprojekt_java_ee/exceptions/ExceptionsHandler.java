package org.darkend.slutprojekt_java_ee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception) {
        return ResponseEntity.badRequest()
                .body(new ExceptionAsJson(LocalDateTime.now()
                        .toString(), HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleConstraintViolation(EntityNotFoundException exception) {
        return new ResponseEntity<>(new ExceptionAsJson(LocalDateTime.now()
                .toString(), HttpStatus.BAD_REQUEST, exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
