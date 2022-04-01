package org.darkend.slutprojekt_java_ee.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeacherEntityTest {

    private TeacherEntity teacher;

    @BeforeEach
    void setUp() {
        teacher = new TeacherEntity();
    }

    @Test
    void getId() {
        teacher.setId(1L);

        assertThat(teacher.getId()).isEqualTo(1L);
    }

    @Test
    void getFirstName() {
        teacher.setFirstName("First");

        assertThat(teacher.getFirstName()).isEqualTo("First");
    }

    @Test
    void getLastName() {
        teacher.setLastName("Last");

        assertThat(teacher.getLastName()).isEqualTo("Last");
    }

    @Test
    void testToString() {
        assertThat(teacher).hasToString("TeacherEntity{id=null, firstName='null', lastName='null'}");
    }

    @Test
    void testEquals() {
        assertThat(teacher.equals(new TeacherEntity())).isTrue();
    }

    @Test
    void testHashCode() {
        assertThat(teacher).hasSameHashCodeAs(new TeacherEntity());
    }

    @Test
    void testEqualsWithPersonEntity() {
        assertThat(teacher.equals(new PersonEntity())).isFalse();
    }
}
