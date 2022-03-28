package org.darkend.slutprojekt_java_ee.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

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
        assertThat(teacher.toString()).isEqualTo("TeacherDto{id=null, fullName='null'}");
    }

    @Test
    void testEquals() {
        assertThat(teacher).isEqualTo(new TeacherDto());
    }

    @Test
    void testHashCode() {
        assertThat(teacher.hashCode()).isEqualTo(new TeacherDto().hashCode());
    }
}
