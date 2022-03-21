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

import java.util.Set;

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
                    .setStudents(Set.of(student))
                    .setTeacher(teacher);

            school = new SchoolEntity().setId(1L)
                    .setName("Name")
                    .setStudents(Set.of(student))
                    .setTeachers(Set.of(teacher))
                    .setCourses(Set.of(course))
                    .setAddress("Address")
                    .setCity("City");

            PrincipalEntity principal = new PrincipalEntity().setId(1L)
                    .setFirstName("First")
                    .setLastName("Last");

            school.setPrincipal(principal);
        }

        @Test
        void fullMappingShouldBeTheEqualToManualDTO() {
            StudentDTO studentDto = new StudentDTO().setId(1L)
                    .setFullName("First Last")
                    .setEmail("email@email.com")
                    .setPhoneNumber("N/A");

            TeacherDTO teacherDto = new TeacherDTO().setId(1L)
                    .setFullName("First Last");

            CourseDTO courseDto = new CourseDTO().setId(1L)
                    .setName("Name")
                    .setStudents(Set.of(studentDto))
                    .setTeacher(teacherDto);

            SchoolDTO schoolDto = new SchoolDTO().setId(1L)
                    .setName("Name")
                    .setCity("City")
                    .setAddress("Address")
                    .setStudents(Set.of(studentDto))
                    .setTeachers(Set.of(teacherDto))
                    .setCourses(Set.of(courseDto));

            PrincipalDTO principalDto = new PrincipalDTO().setId(1L)
                    .setFullName("First Last");
            schoolDto.setPrincipal(principalDto);

            var result = mapper.map(school, SchoolDTO.class);

            assertThat(result).isEqualTo(schoolDto);
        }

    }

    @Nested
    class SchoolDTOToEntityTests {

        private SchoolDTO schoolDto;

        @BeforeEach
        public void setUp() {
            StudentDTO studentDto = new StudentDTO().setId(1L)
                    .setFullName("First Last")
                    .setEmail("email@email.com")
                    .setPhoneNumber("N/A");

            TeacherDTO teacherDto = new TeacherDTO().setId(1L)
                    .setFullName("First Last");

            CourseDTO courseDto = new CourseDTO().setId(1L)
                    .setName("Name")
                    .setStudents(Set.of(studentDto))
                    .setTeacher(teacherDto);

            schoolDto = new SchoolDTO().setId(1L)
                    .setName("Name")
                    .setCity("City")
                    .setAddress("Address")
                    .setStudents(Set.of(studentDto))
                    .setTeachers(Set.of(teacherDto))
                    .setCourses(Set.of(courseDto));

            PrincipalDTO principalDto = new PrincipalDTO().setId(1L)
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
                    .setStudents(Set.of(student))
                    .setTeacher(teacher);

            SchoolEntity school = new SchoolEntity().setId(1L)
                    .setName("Name")
                    .setStudents(Set.of(student))
                    .setTeachers(Set.of(teacher))
                    .setCourses(Set.of(course))
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
