package org.darkend.slutprojekt_java_ee.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Id;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SchoolEntityTest {

    private SchoolEntity school;

    @BeforeEach
    void setUp() {
        school = new SchoolEntity();
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
        PrincipalEntity principal = new PrincipalEntity();

        school.setPrincipal(principal);

        assertThat(school.getPrincipal()).isEqualTo(principal);
    }

    @Test
    void getStudents() {
        StudentEntity student = new StudentEntity();

        school.setStudents(List.of(student));

        assertThat(school.getStudents()).isEqualTo(List.of(student));
    }

    @Test
    void getCourses() {
        CourseEntity course = new CourseEntity();

        school.setCourses(List.of(course));

        assertThat(school.getCourses()).isEqualTo(List.of(course));
    }

    @Test
    void getTeachers() {
        TeacherEntity teacher = new TeacherEntity();

        school.setTeachers(List.of(teacher));

        assertThat(school.getTeachers()).isEqualTo(List.of(teacher));
    }

    @Test
    void testToString() {
        assertThat(school).hasToString(
                "SchoolEntity{id=null, name='null', city='null', address='null', principal=null, students=[], " +
                        "courses=[], teachers=[]}");
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple()
                .forClass(SchoolEntity.class)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }
}
