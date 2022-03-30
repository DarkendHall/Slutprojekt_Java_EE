package org.darkend.slutprojekt_java_ee.exceptions;

import org.darkend.slutprojekt_java_ee.controller.CourseController;
import org.darkend.slutprojekt_java_ee.dto.CourseDto;
import org.junit.jupiter.api.Test;
import org.modelmapper.MappingException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ExceptionsHandlerTest {

    private final Clock clock = Clock.fixed(Instant.ofEpochSecond(0L), ZoneId.systemDefault());
    private final ExceptionsHandler handler = new ExceptionsHandler(clock);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    void handleEntityNotFoundException() {
        var exception = new EntityNotFoundException("No such entity");

        var result = handler.handleEntityNotFoundException(exception);

        var response = new ResponseEntity<Object>(new ExceptionAsJson(LocalDateTime.now(clock)
                .format(dateTimeFormatter), HttpStatus.NOT_FOUND, exception.getMessage()), HttpStatus.NOT_FOUND);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void handleConstraintViolationException() {
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

        var result = handler.handleConstraintViolationException(exception);

        assertThat(result).isEqualTo(ResponseEntity.badRequest()
                .body(new ExceptionAsJson(LocalDateTime.now(clock)
                        .format(dateTimeFormatter), HttpStatus.BAD_REQUEST, exception.getMessage())));
    }

    @Test
    void handleMappingException() {
        var exception = new MappingException(List.of(new ErrorMessage("Error")));

        var result = handler.handleMappingException(exception);

        assertThat(result).isEqualTo(ResponseEntity.badRequest()
                .body(new ExceptionAsJson(LocalDateTime.now(clock)
                        .format(dateTimeFormatter), HttpStatus.BAD_REQUEST, exception.getMessage())));
    }

    @Test
    void handleInvalidNameException() {
        var exception = new InvalidNameException("test");

        var result = handler.handleInvalidNameException(exception);

        assertThat(result).isEqualTo(ResponseEntity.badRequest()
                .body(new ExceptionAsJson(LocalDateTime.now(clock)
                        .format(dateTimeFormatter), HttpStatus.BAD_REQUEST, exception.getMessage())));
    }

    @Test
    void handleMethodArgumentNotValidException() throws NoSuchMethodException {
        Validator validator = Validation.buildDefaultValidatorFactory()
                .getValidator();

        CourseDto courseDto = new CourseDto().setId(1L);
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(courseDto, "courseDto");
        SpringValidatorAdapter adapter = new SpringValidatorAdapter(validator);
        adapter.validate(courseDto, bindingResult);
        var exception = new MethodArgumentNotValidException(new MethodParameter(
                CourseController.class.getDeclaredMethod("createCourse", CourseDto.class, HttpServletResponse.class),
                0), bindingResult);

        var result = handler.handleMethodArgumentNotValidException(exception);

        assertThat(result).isEqualTo(ResponseEntity.badRequest()
                .body(new ExceptionAsJson(LocalDateTime.now(clock)
                        .format(dateTimeFormatter), HttpStatus.BAD_REQUEST, exception.getMessage())));
    }
}
