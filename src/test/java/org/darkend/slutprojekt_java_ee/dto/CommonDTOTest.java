package org.darkend.slutprojekt_java_ee.dto;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CommonDTOTest {

    StudentDTO dto;

    @BeforeEach
    public void setUp() {
        dto = new StudentDTO();
    }

    @Test
    void emptyStringShouldThrowExceptionOnGenerateFirstName() {
        dto.setFullName("");
        assertThatThrownBy(() -> CommonDTO.generateFirstName(dto)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void emptyStringShouldThrowExceptionOnGenerateLastName() {
        dto.setFullName("");
        assertThatThrownBy(() -> CommonDTO.generateLastName(dto)).isInstanceOf(IllegalArgumentException.class);
    }

}