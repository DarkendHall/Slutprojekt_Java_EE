package org.darkend.slutprojekt_java_ee.dto;

import org.darkend.slutprojekt_java_ee.beans.ModelMapperConfig;
import org.darkend.slutprojekt_java_ee.entity.CourseEntity;
import org.darkend.slutprojekt_java_ee.entity.PrincipalEntity;
import org.darkend.slutprojekt_java_ee.entity.SchoolEntity;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.entity.TeacherEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ModelMapperConfigTest {

    private final ModelMapper mapper = ModelMapperConfig.getModelMapper();

    @Nested
    class SchoolEntityToDTOTests {
        private SchoolEntity school;

        @BeforeEach
        public void setUp() {
            TeacherEntity teacher = new TeacherEntity().setId(1L)
                    .setFirstName("First")
                    .setLastName("Last");

            StudentEntity student = new StudentEntity().setId(1L)
                    .setFirstName("First")
                    .setLastName("Last")
                    .setEmail("email@email.com")
                    .setPhoneNumber("N/A");

            CourseEntity course = new CourseEntity().setId(1L)
                    .setName("Name")
                    .setStudents(List.of(student))
                    .setTeacher(teacher);

            school = new SchoolEntity().setId(1L)
                    .setName("Name")
                    .setStudents(List.of(student))
                    .setTeachers(List.of(teacher))
                    .setCourses(List.of(course))
                    .setAddress("Address")
                    .setCity("City");

            PrincipalEntity principal = new PrincipalEntity().setId(1L)
                    .setFirstName("First")
                    .setLastName("Last");

            school.setPrincipal(principal);
        }

        @Test
        void fullMappingShouldBeTheEqualToManualDTO() {
            StudentDto studentDto = new StudentDto().setId(1L)
                    .setFullName("First Last")
                    .setEmail("email@email.com")
                    .setPhoneNumber("N/A");

            TeacherDto teacherDto = new TeacherDto().setId(1L)
                    .setFullName("First Last");

            CourseDto courseDto = new CourseDto().setId(1L)
                    .setName("Name")
                    .setStudents(List.of(studentDto))
                    .setTeacher(teacherDto);

            SchoolDto schoolDto = new SchoolDto().setId(1L)
                    .setName("Name")
                    .setCity("City")
                    .setAddress("Address")
                    .setStudents(List.of(studentDto))
                    .setTeachers(List.of(teacherDto))
                    .setCourses(List.of(courseDto));

            PrincipalDto principalDto = new PrincipalDto().setId(1L)
                    .setFullName("First Last");
            schoolDto.setPrincipal(principalDto);

            var result = mapper.map(school, SchoolDto.class);

            assertThat(result).isEqualTo(schoolDto);
        }
    }

    @Nested
    class SchoolDtoToEntityTests {

        private SchoolDto schoolDto;

        @BeforeEach
        public void setUp() {
            StudentDto studentDto = new StudentDto().setId(1L)
                    .setFullName("First Last")
                    .setEmail("email@email.com")
                    .setPhoneNumber("N/A");

            TeacherDto teacherDto = new TeacherDto().setId(1L)
                    .setFullName("First Last");

            CourseDto courseDto = new CourseDto().setId(1L)
                    .setName("Name")
                    .setStudents(List.of(studentDto))
                    .setTeacher(teacherDto);

            schoolDto = new SchoolDto().setId(1L)
                    .setName("Name")
                    .setCity("City")
                    .setAddress("Address")
                    .setStudents(List.of(studentDto))
                    .setTeachers(List.of(teacherDto))
                    .setCourses(List.of(courseDto));

            PrincipalDto principalDto = new PrincipalDto().setId(1L)
                    .setFullName("First Last");

            schoolDto.setPrincipal(principalDto);
        }

        @Test
        void fullMappingShouldBeTheEqualToManualEntity() {
            TeacherEntity teacher = new TeacherEntity().setId(1L)
                    .setFirstName("First")
                    .setLastName("Last");

            StudentEntity student = new StudentEntity().setId(1L)
                    .setFirstName("First")
                    .setLastName("Last")
                    .setEmail("email@email.com")
                    .setPhoneNumber("N/A");

            CourseEntity course = new CourseEntity().setId(1L)
                    .setName("Name")
                    .setStudents(List.of(student))
                    .setTeacher(teacher);

            SchoolEntity school = new SchoolEntity().setId(1L)
                    .setName("Name")
                    .setStudents(List.of(student))
                    .setTeachers(List.of(teacher))
                    .setCourses(List.of(course))
                    .setAddress("Address")
                    .setCity("City");

            PrincipalEntity principal = new PrincipalEntity().setId(1L)
                    .setFirstName("First")
                    .setLastName("Last");

            school.setPrincipal(principal);

            var result = mapper.map(schoolDto, SchoolEntity.class);

            assertThat(result).isEqualTo(school);
        }
    }
}
