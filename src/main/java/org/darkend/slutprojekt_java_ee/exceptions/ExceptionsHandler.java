package org.darkend.slutprojekt_java_ee.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionsHandler {
    private final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e) {
        logger.warn(e.getMessage());

        return ResponseEntity.badRequest()
                .body(new ExceptionAsJson(LocalDateTime.now()
                        .toString(), HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleConstraintViolation(EntityNotFoundException e) {
        logger.warn(e.getMessage());

        return new ResponseEntity<>(new ExceptionAsJson(LocalDateTime.now()
                .toString(), HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
