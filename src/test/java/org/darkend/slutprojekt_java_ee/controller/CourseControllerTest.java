package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.CourseDTO;
import org.darkend.slutprojekt_java_ee.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseControllerTest {

    private final CourseDTO course = new CourseDTO();

    private CourseService service;
    private CourseController controller;


    @BeforeEach
    void setUp() {
        service = mock(CourseService.class);
        controller = new CourseController(service);
    }

    @Test
    void createCourseShouldReturnResponseCreated() {
        when(service.createCourse(course)).thenReturn(course);

        var result = controller.createCourse(course);

        assertThat(result).isEqualTo(ResponseEntity.created(URI.create("/courses/" + course.getId()))
                .body(course));
    }

    @Test
    void deleteCourseShouldReturnResponseOk() {
        var result = controller.deleteCourse(1L);

        assertThat(result).isEqualTo(ResponseEntity.ok()
                .build());
    }

    @Test
    void findCourseShouldReturnResponseOk() {
        when(service.findCourseById(1L)).thenReturn(course);

        var result = controller.findCourseById(1L);

        assertThat(result).isEqualTo(ResponseEntity.ok(course));
    }

    @Test
    void findAllCoursesShouldReturnResponseOk() {
        when(service.findAllCourses()).thenReturn(List.of(course));

        var result = controller.findAllCourses();

        assertThat(result).isEqualTo(ResponseEntity.ok(List.of(course)));
    }
}