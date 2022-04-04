package org.darkend.slutprojekt_java_ee.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudentEntityTest {

    private StudentEntity student;

    @BeforeEach
    void setUp() {
        student = new StudentEntity();
    }

    @Test
    void getId() {
        student.setId(1L);

        assertThat(student.getId()).isEqualTo(1L);
    }

    @Test
    void getFirstName() {
        student.setFirstName("First");

        assertThat(student.getFirstName()).isEqualTo("First");
    }

    @Test
    void getLastName() {
        student.setLastName("Last");

        assertThat(student.getLastName()).isEqualTo("Last");
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
                "StudentEntity{id=null, firstName='null', lastName='null', email='null', phoneNumber='null'}");
    }

    @Test
    void testEquals() {
        assertThat(student.equals(new StudentEntity())).isTrue();
    }

    @Test
    void testHashCode() {
        assertThat(student).hasSameHashCodeAs(new StudentEntity());
    }

    @Test
    void testEqualsWithPersonEntity() {
        assertThat(student.equals(new PersonEntity())).isFalse();
    }
}
