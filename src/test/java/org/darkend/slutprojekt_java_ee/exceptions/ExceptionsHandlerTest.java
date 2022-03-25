package org.darkend.slutprojekt_java_ee.exceptions;

import org.junit.jupiter.api.Test;
import org.modelmapper.MappingException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ExceptionsHandlerTest {

    private final ExceptionsHandler handler = new ExceptionsHandler();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    void handleEntityNotFound() {
        var exception = new EntityNotFoundException("No such entity");

        var result = handler.handleEntityNotFound(exception);

        var response = new ResponseEntity<Object>(new ExceptionAsJson(LocalDateTime.now()
                .format(dateTimeFormatter), HttpStatus.NOT_FOUND, exception.getMessage()), HttpStatus.NOT_FOUND);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void handleConstraintViolation() {
        Validator validator = Validation.buildDefaultValidatorFactory()
                .getValidator();

        @Entity
        class ClassToValidate {
            @NotNull String name;

            public String getName() {
                return name;
            }

            public ClassToValidate setName(String name) {
                this.name = name;
                return this;
            }
        }

        var violations = validator.validate(new ClassToValidate().setName(null));

        var exception = new ConstraintViolationException(violations);

        var result = handler.handleConstraintViolation(exception);

        assertThat(result).isEqualTo(ResponseEntity.badRequest()
                .body(new ExceptionAsJson(LocalDateTime.now()
                        .format(dateTimeFormatter), HttpStatus.BAD_REQUEST, exception.getMessage())));
    }

    @Test
    void handleMapping() {

        var exception = new MappingException(List.of(new ErrorMessage("Error")));

        var result = handler.handleMapping(exception);

        assertThat(result).isEqualTo(ResponseEntity.badRequest()
                .body(new ExceptionAsJson(LocalDateTime.now()
                        .format(dateTimeFormatter), HttpStatus.BAD_REQUEST, exception.getMessage())));
    }

}
