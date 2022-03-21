package org.darkend.slutprojekt_java_ee.dto;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CommonDTOTest {

    StudentDTO student = new StudentDTO().setFullName("First");
    TeacherDTO teacher = new TeacherDTO().setFullName("First Last");

    @Test
    void firstNameAndLastNameShouldBeCombinedToFullName() {
        var result = CommonDTO.generateFullName("First", "Last");

        assertThat(result).isEqualTo("First Last");
    }

    @Test
    void generateFirstNameShouldGetTheFirstOfTheTwoNames() {
        var result = CommonDTO.generateFirstName(teacher);

        assertThat(result).isEqualTo("First");
    }

    @Test
    void generateLastNameShouldGetTheSecondOfTheTwoNames() {
        var result = CommonDTO.generateLastName(teacher);

        assertThat(result).isEqualTo("Last");
    }

    @Test
    void emptyStringShouldThrowExceptionOnGenerateFirstName() {
        assertThatThrownBy(() -> CommonDTO.generateFirstName(student)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void emptyStringShouldThrowExceptionOnGenerateLastName() {
        assertThatThrownBy(() -> CommonDTO.generateLastName(student)).isInstanceOf(IllegalArgumentException.class);
    }

}