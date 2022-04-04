package org.darkend.slutprojekt_java_ee.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeacherDtoTest {

    private TeacherDto teacher;

    @BeforeEach
    void setUp() {
        teacher = new TeacherDto();
    }

    @Test
    void getId() {
        teacher.setId(1L);

        assertThat(teacher.getId()).isEqualTo(1L);
    }

    @Test
    void getFullName() {
        teacher.setFullName("Name");

        assertThat(teacher.getFullName()).isEqualTo("Name");
    }

    @Test
    void testToString() {
        assertThat(teacher).hasToString("TeacherDto{id=null, fullName='null'}");
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple()
                .forClass(TeacherDto.class)
                .verify();
    }
}
