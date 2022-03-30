package org.darkend.slutprojekt_java_ee.dto;


import org.darkend.slutprojekt_java_ee.exceptions.InvalidNameException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CommonDtoTest {

    StudentDto student = new StudentDto().setFullName("First");
    TeacherDto teacher = new TeacherDto().setFullName("First Last");

    @Test
    void firstNameAndLastNameShouldBeCombinedToFullName() {
        var result = CommonDto.generateFullName("First", "Last");

        assertThat(result).isEqualTo("First Last");
    }

    @Test
    void generateFirstNameShouldGetTheFirstOfTheTwoNames() {
        var result = CommonDto.generateFirstName(teacher);

        assertThat(result).isEqualTo("First");
    }

    @Test
    void generateLastNameShouldGetTheSecondOfTheTwoNames() {
        var result = CommonDto.generateLastName(teacher);

        assertThat(result).isEqualTo("Last");
    }

    @Test
    void emptyStringShouldThrowExceptionOnGenerateFirstName() {
        assertThatThrownBy(() -> CommonDto.generateFirstName(student)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    void emptyStringShouldThrowExceptionOnGenerateLastName() {
        assertThatThrownBy(() -> CommonDto.generateLastName(student)).isInstanceOf(InvalidNameException.class);
    }
}
