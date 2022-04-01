package org.darkend.slutprojekt_java_ee.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StudentDtoTest {

    private StudentDto student;

    @BeforeEach
    void setUp() {
        student = new StudentDto();
    }

    @Test
    void getId() {
        student.setId(1L);

        assertThat(student.getId()).isEqualTo(1L);
    }

    @Test
    void getFullName() {
        student.setFullName("Name");

        assertThat(student.getFullName()).isEqualTo("Name");
    }

    @Test
    void getEmail() {
        student.setEmail("Email");

        assertThat(student.getEmail()).isEqualTo("Email");
    }

    @Test
    void getPhoneNumber() {
        student.setPhoneNumber("1234");

        assertThat(student.getPhoneNumber()).isEqualTo("1234");
    }

    @Test
    void testToString() {
        assertThat(student).hasToString(
                "StudentDto{id=null, fullName='null', email='null', phoneNumber='null'}");
    }

    @Test
    void testEquals() {
        assertThat(student).isEqualTo(new StudentDto());
    }

    @Test
    void testHashCode() {
        assertThat(student.hashCode()).isEqualTo(new StudentDto().hashCode());
    }
}
