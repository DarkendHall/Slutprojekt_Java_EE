package org.darkend.slutprojekt_java_ee.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SchoolDtoTest {

    private SchoolDto school;

    @BeforeEach
    void setUp() {
        school = new SchoolDto();
    }

    @Test
    void getId() {
        school.setId(1L);

        assertThat(school.getId()).isEqualTo(1L);
    }

    @Test
    void getName() {
        school.setName("Name");

        assertThat(school.getName()).isEqualTo("Name");
    }

    @Test
    void getCity() {
        school.setCity("City");

        assertThat(school.getCity()).isEqualTo("City");
    }

    @Test
    void getAddress() {
        school.setAddress("Address");

        assertThat(school.getAddress()).isEqualTo("Address");
    }

    @Test
    void getPrincipal() {
        PrincipalDto principal = new PrincipalDto();

        school.setPrincipal(principal);

        assertThat(school.getPrincipal()).isEqualTo(principal);
    }

    @Test
    void getStudents() {
        StudentDto student = new StudentDto();

        school.setStudents(List.of(student));

        assertThat(school.getStudents()).isEqualTo(List.of(student));
    }

    @Test
    void getCourses() {
        CourseDto course = new CourseDto();

        school.setCourses(List.of(course));

        assertThat(school.getCourses()).isEqualTo(List.of(course));
    }

    @Test
    void getTeachers() {
        TeacherDto teacher = new TeacherDto();

        school.setTeachers(List.of(teacher));

        assertThat(school.getTeachers()).isEqualTo(List.of(teacher));
    }

    @Test
    void testToString() {
        assertThat(school).hasToString(
                "SchoolDto{id=null, name='null', city='null', address='null', principal=null, students=null, " +
                        "courses=null, teachers=null}");
    }

    @Test
    void testEquals() {
        assertThat(school).isEqualTo(new SchoolDto());
    }

    @Test
    void testHashCode() {
        assertThat(school.hashCode()).isEqualTo(new SchoolDto().hashCode());
    }
}
