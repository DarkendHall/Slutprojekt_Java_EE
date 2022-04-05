package org.darkend.slutprojekt_java_ee.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CourseDtoTest {

    private CourseDto course;

    @BeforeEach
    void setUp() {
        course = new CourseDto();
    }

    @Test
    void getId() {
        course.setId(1L);

        assertThat(course.getId()).isEqualTo(1L);
    }

    @Test
    void getName() {
        course.setName("Name");

        assertThat(course.getName()).isEqualTo("Name");
    }

    @Test
    void getStudents() {
        StudentDto student = new StudentDto();

        course.setStudents(List.of(student));

        assertThat(course.getStudents()).isEqualTo(List.of(student));
    }

    @Test
    void getTeacher() {
        TeacherDto teacher = new TeacherDto();

        course.setTeacher(teacher);

        assertThat(course.getTeacher()).isEqualTo(teacher);
    }

    @Test
    void testToString() {
        assertThat(course).hasToString("CourseDto{id=null, name='null', students=null, teacher=null}");
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple()
                .forClass(CourseDto.class)
                .verify();
    }
}
