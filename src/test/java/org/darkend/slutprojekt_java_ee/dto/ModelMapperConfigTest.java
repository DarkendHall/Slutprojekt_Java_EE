package org.darkend.slutprojekt_java_ee.dto;

import org.darkend.slutprojekt_java_ee.beans.ModelMapperConfig;
import org.darkend.slutprojekt_java_ee.entity.CourseEntity;
import org.darkend.slutprojekt_java_ee.entity.PrincipalEntity;
import org.darkend.slutprojekt_java_ee.entity.RoleEntity;
import org.darkend.slutprojekt_java_ee.entity.SchoolEntity;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.entity.TeacherEntity;
import org.darkend.slutprojekt_java_ee.entity.UserEntity;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModelMapperConfigTest {

    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final ModelMapper mapper = ModelMapperConfig.getModelMapper(roleRepository);

    @Test
    void fullMappingShouldBeEqualToManualUserEntity() {
        RoleEntity role = new RoleEntity().setId(1L)
                .setRole("USER");
        UserEntity user = new UserEntity().setId(1L)
                .setPassword("password")
                .setUsername("username")
                .setRoles(List.of(role));

        UserDtoIn userDto = new UserDtoIn().setId(1L)
                .setUsername("username")
                .setPassword("password")
                .setRoles(List.of("USER"));

        when(roleRepository.findByRole("USER")).thenReturn(role);

        var result = mapper.map(userDto, UserEntity.class);

        assertThat(result).isEqualTo(user);
    }

    @Test
    void fullMappingShouldBeEqualToManualUserDtoOut() {
        RoleDto role = new RoleDto().setId(1L)
                .setRole("USER");
        UserDtoOut user = new UserDtoOut().setId(1L)
                .setUsername("username")
                .setRoles(List.of(role.getRole()));

        RoleEntity roleEntity = new RoleEntity().setId(1L)
                .setRole("USER");
        UserEntity userEntity = new UserEntity().setId(1L)
                .setUsername("username")
                .setPassword("password")
                .setRoles(List.of(roleEntity));

        when(roleRepository.findByRole("USER")).thenReturn(roleEntity);

        var result = mapper.map(userEntity, UserDtoOut.class);

        assertThat(result).isEqualTo(user);
    }


    @Test
    void fullMappingShouldBeEqualToManualSchoolDto() {
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


    @Test
    void fullMappingShouldBeEqualToManualSchoolEntity() {

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
