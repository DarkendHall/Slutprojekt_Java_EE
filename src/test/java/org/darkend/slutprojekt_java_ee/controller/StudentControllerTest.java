package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentControllerTest {

    private final StudentEntity student = new StudentEntity().setId(1L)
            .setFirstName("Student");

    private StudentService service;
    private StudentController controller;


    @BeforeEach
    void setUp() {
        service = mock(StudentService.class);
        controller = new StudentController(service);
    }

    @Test
    void createStudentShouldReturnResponseCreated() {
        when(service.createStudent(student)).thenReturn(student);

        var result = controller.createStudent(student);

        assertThat(result).isEqualTo(ResponseEntity.created(URI.create("/students/" + student.getId()))
                .body(student));
    }

    @Test
    void deleteStudentShouldReturnResponseOk() {
        var result = controller.deleteStudent(1L);

        assertThat(result).isEqualTo(ResponseEntity.ok()
                .build());
    }

    @Test
    void findStudentShouldReturnResponseOk() {
        when(service.findStudentById(1L)).thenReturn(Optional.of(student));

        var result = controller.findStudentById(1L);

        assertThat(result).isEqualTo(ResponseEntity.ok(student));
    }

    @Test
    void findAllStudentsShouldReturnResponseOk() {
        when(service.findAllStudents()).thenReturn(List.of(student));

        var result = controller.findAllStudents();

        assertThat(result).isEqualTo(ResponseEntity.ok(List.of(student)));
    }
}