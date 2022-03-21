package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.TeacherDTO;
import org.darkend.slutprojekt_java_ee.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherControllerTest {

    private final TeacherDTO teacher = new TeacherDTO();

    private TeacherService service;
    private TeacherController controller;


    @BeforeEach
    void setUp() {
        service = mock(TeacherService.class);
        controller = new TeacherController(service);
    }

    @Test
    void createTeacherShouldReturnResponseCreated() {
        when(service.createTeacher(teacher)).thenReturn(teacher);

        var result = controller.createTeacher(teacher);

        assertThat(result).isEqualTo(ResponseEntity.created(URI.create("/teachers/" + teacher.getId()))
                .body(teacher));
    }

    @Test
    void deleteTeacherShouldReturnResponseOk() {
        var result = controller.deleteTeacher(1L);

        assertThat(result).isEqualTo(ResponseEntity.ok()
                .build());
    }

    @Test
    void findTeacherShouldReturnResponseOk() {
        when(service.findTeacherById(1L)).thenReturn(teacher);

        var result = controller.findTeacherById(1L);

        assertThat(result).isEqualTo(ResponseEntity.ok(teacher));
    }

    @Test
    void findAllTeachersShouldReturnResponseOk() {
        when(service.findAllTeachers()).thenReturn(List.of(teacher));

        var result = controller.findAllTeachers();

        assertThat(result).isEqualTo(ResponseEntity.ok(List.of(teacher)));
    }
}