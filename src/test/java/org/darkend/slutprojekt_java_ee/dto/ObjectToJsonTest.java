package org.darkend.slutprojekt_java_ee.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ObjectToJsonTest {

    @Test
    void convertShouldReturnObjectAsString() {
        var result = ObjectToJson.convert(List.of("test", "test2", "test3", "test4"));

        assertThat(result).isEqualTo("[\"test\",\"test2\",\"test3\",\"test4\"]");
    }

    @Test
    void convertInvalidObjectShouldThrowException() {
        Object invalid = mock(Object.class);
        when(invalid.toString()).thenReturn(invalid.getClass()
                .getName());
        assertThatThrownBy(() -> ObjectToJson.convert(invalid)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void nullValuesShouldNotBeIncludedWithTeacherDto() {
        var result = ObjectToJson.convert(new TeacherDto().setId(null)
                .setFullName("test test"));

        assertThat(result).isEqualTo("{\"fullName\":\"test test\"}");
    }

    @Test
    void nullValuesShouldNotBeIncludedWithPrincipalDto() {
        var result = ObjectToJson.convert(new PrincipalDto().setId(null)
                .setFullName("test test"));

        assertThat(result).isEqualTo("{\"fullName\":\"test test\"}");
    }

    @Test
    void nullValuesShouldNotBeIncludedWithSchoolDto() {
        var result = ObjectToJson.convert(new SchoolDto().setId(null)
                .setName("test"));

        assertThat(result).isEqualTo("{\"name\":\"test\"}");
    }

    @Test
    void nullValuesShouldNotBeIncludedWithStudentDto() {
        var result = ObjectToJson.convert(new StudentDto().setId(null)
                .setFullName("test test"));

        assertThat(result).isEqualTo("{\"fullName\":\"test test\"}");
    }

    @Test
    void nullValuesShouldNotBeIncludedWithCourseDto() {
        var result = ObjectToJson.convert(new CourseDto().setId(null)
                .setName("test"));

        assertThat(result).isEqualTo("{\"name\":\"test\"}");
    }
}
