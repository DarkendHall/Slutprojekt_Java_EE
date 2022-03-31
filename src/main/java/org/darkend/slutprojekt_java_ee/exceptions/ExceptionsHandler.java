package org.darkend.slutprojekt_java_ee.exceptions;

import org.modelmapper.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ExceptionsHandler {

    private final Clock clock;
    private final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ExceptionsHandler(Clock clock) {
        this.clock = clock;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        logger.error(e.getMessage());

        return ResponseEntity.badRequest()
                .body(new ExceptionAsJson(LocalDateTime.now(clock)
                        .format(dateTimeFormatter), HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        logger.error(e.getMessage());

        return new ResponseEntity<>(new ExceptionAsJson(LocalDateTime.now(clock)
                .format(dateTimeFormatter), HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MappingException.class)
    public ResponseEntity<Object> handleMappingException(MappingException e) {
        logger.error(e.getMessage());

        return ResponseEntity.badRequest()
                .body(new ExceptionAsJson(LocalDateTime.now(clock)
                        .format(dateTimeFormatter), HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<Object> handleInvalidNameException(InvalidNameException e) {
        logger.error(e.getMessage());

        return ResponseEntity.badRequest()
                .body(new ExceptionAsJson(LocalDateTime.now(clock)
                        .format(dateTimeFormatter), HttpStatus.BAD_REQUEST, e.getMessage()));

    }

    @ExceptionHandler
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(
            SQLIntegrityConstraintViolationException e) {
        logger.error(e.getMessage());

        return ResponseEntity.badRequest()
                .body(new ExceptionAsJson(LocalDateTime.now(clock)
                        .format(dateTimeFormatter), HttpStatus.BAD_REQUEST, e.getMessage()));

    }

    @ExceptionHandler
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.warn(e.getMessage());

        return ResponseEntity.badRequest()
                .body(new ExceptionAsJson(LocalDateTime.now(clock)
                        .format(dateTimeFormatter), HttpStatus.BAD_REQUEST, e.getMessage()));
    }
}
