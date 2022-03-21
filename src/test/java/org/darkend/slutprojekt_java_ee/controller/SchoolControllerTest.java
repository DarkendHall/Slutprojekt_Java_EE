package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.SchoolDTO;
import org.darkend.slutprojekt_java_ee.service.SchoolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class SchoolControllerTest {

    private final SchoolDTO school = new SchoolDTO();

    private SchoolService service;
    private SchoolController controller;


    @BeforeEach
    void setUp() {
        service = mock(SchoolService.class);
        controller = new SchoolController(service);
    }

    @Test
    void createSchoolShouldReturnResponseCreated() {
        when(service.createSchool(school)).thenReturn(school);

        var result = controller.createSchool(school);

        assertThat(result).isEqualTo(ResponseEntity.created(URI.create("/schools/" + school.getId()))
                .body(school));
    }

    @Test
    void deleteSchoolShouldReturnResponseOk() {
        var result = controller.deleteSchool(1L);

        assertThat(result).isEqualTo(ResponseEntity.ok()
                .build());
    }

    @Test
    void findSchoolShouldReturnResponseOk() {
        when(service.findSchoolById(1L)).thenReturn(school);

        var result = controller.findSchoolById(1L);

        assertThat(result).isEqualTo(ResponseEntity.ok(school));
    }

    @Test
    void findAllSchoolsShouldReturnResponseOk() {
        when(service.findAllSchools()).thenReturn(List.of(school));

        var result = controller.findAllSchools();

        assertThat(result).isEqualTo(ResponseEntity.ok(List.of(school)));
    }
}