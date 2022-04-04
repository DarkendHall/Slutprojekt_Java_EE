package org.darkend.slutprojekt_java_ee.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Id;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CourseEntityTest {

    private CourseEntity course;

    @BeforeEach
    void setUp() {
        course = new CourseEntity();
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
        StudentEntity student = new StudentEntity();

        course.setStudents(List.of(student));

        assertThat(course.getStudents()).isEqualTo(List.of(student));
    }

    @Test
    void getTeacher() {
        TeacherEntity teacher = new TeacherEntity();

        course.setTeacher(teacher);

        assertThat(course.getTeacher()).isEqualTo(teacher);
    }

    @Test
    void testToString() {
        assertThat(course).hasToString("CourseEntity{id=null, name='null', students=[], teacher=null}");
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple()
                .forClass(CourseEntity.class)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }
}
