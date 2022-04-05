package org.darkend.slutprojekt_java_ee.integration_tests;

import org.darkend.slutprojekt_java_ee.beans.ModelMapperConfig;
import org.darkend.slutprojekt_java_ee.controller.CourseController;
import org.darkend.slutprojekt_java_ee.dto.CommonDto;
import org.darkend.slutprojekt_java_ee.dto.CourseDto;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.entity.CourseEntity;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.entity.TeacherEntity;
import org.darkend.slutprojekt_java_ee.repository.CourseRepository;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.darkend.slutprojekt_java_ee.security.GlobalMethodSecurityConfig;
import org.darkend.slutprojekt_java_ee.security.SecurityConfig;
import org.darkend.slutprojekt_java_ee.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
@Import({CourseService.class, CommonDto.class, ModelMapperConfig.class, SecurityConfig.class,
        GlobalMethodSecurityConfig.class})
@MockBean(RoleRepository.class)
class CourseIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CourseRepository repository;

    private final CourseDto courseDto = new CourseDto().setId(1L)
            .setName("Course Name")
            .setStudents(List.of(new StudentDto().setId(2L)
                    .setFullName("Student Name")
                    .setPhoneNumber("N/A")
                    .setEmail("email@email.com")))
            .setTeacher(new TeacherDto().setId(3L)
                    .setFullName("Teacher Name"));

    private final CourseDto courseDto2 = new CourseDto().setId(1L)
            .setName("Course Name")
            .setStudents(List.of(new StudentDto().setId(2L)
                    .setFullName("Student Name")
                    .setPhoneNumber("N/A")
                    .setEmail("email@email.com")))
            .setTeacher(new TeacherDto().setId(3L)
                    .setFullName("Teacher Name"));

    private final CourseEntity courseEntity = new CourseEntity().setId(1L)
            .setName("Course Name")
            .setStudents(List.of(new StudentEntity().setId(2L)
                    .setFirstName("Student")
                    .setLastName("Name")
                    .setPhoneNumber("N/A")
                    .setEmail("email@email.com")))
            .setTeacher(new TeacherEntity().setId(3L)
                    .setFirstName("Teacher")
                    .setLastName("Name"));

    @BeforeEach
    void setUp() {
        when(repository.findById(1L)).thenReturn(Optional.of(courseEntity));
        when(repository.findAll()).thenReturn(List.of(courseEntity));
        when(repository.save(any(CourseEntity.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return args[0];
        });
        doThrow(new EmptyResultDataAccessException("No course found with ID: " + 2L, 1)).when(repository)
                .deleteById(2L);
    }

    @Test
    @WithMockUser
    void getShouldReturnCourseDtoWithCorrectValues() throws Exception {
        mvc.perform(get("/courses/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(courseDto.getId()))
                .andExpect(jsonPath("$.name").value(courseDto.getName()))
                .andExpect(jsonPath("$.students[0]").value(courseDto.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$.teacher").value(courseDto.getTeacher()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getAllShouldReturnListOfCoursesWithCorrectValues() throws Exception {
        mvc.perform(get("/courses").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(courseDto.getId()))
                .andExpect(jsonPath("$[0].name").value(courseDto.getName()))
                .andExpect(jsonPath("$[0].students[0]").value(courseDto.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$[0].teacher").value(courseDto.getTeacher()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void createWithRoleUserShouldReturnForbidden() throws Exception {
        mvc.perform(post("/courses").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "name": "Course Name",
                                  "students": [
                                    {
                                      "id": 2,
                                      "fullName": "Student Name",
                                      "email": "email@email.com",
                                      "phoneNumber": "N/A"
                                    }
                                  ],
                                  "teacher": {
                                    "id": 3,
                                    "fullName": "Teacher Name"
                                  }
                                }
                                """))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createShouldReturnCourseWithCorrectValues() throws Exception {
        mvc.perform(post("/courses").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "name": "Course Name",
                                  "students": [
                                    {
                                      "id": 2,
                                      "fullName": "Student Name",
                                      "email": "email@email.com",
                                      "phoneNumber": "N/A"
                                    }
                                  ],
                                  "teacher": {
                                    "id": 3,
                                    "fullName": "Teacher Name"
                                  }
                                }
                                """))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(courseDto.getName()))
                .andExpect(jsonPath("$.students[0]").value(courseDto.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$.teacher").value(courseDto.getTeacher()))
                .andExpect(status().isCreated());

        verify(repository, times(1)).save(courseEntity);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createWithInvalidCourseShouldReturnBadRequest() throws Exception {
        mvc.perform(post("/courses").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "name": "Course Name",
                                  "students": [
                                    {
                                      "id": 2,
                                      "fullName": "Name",
                                      "email": "email@email.com",
                                      "phoneNumber": "N/A"
                                    }
                                  ],
                                  "teacher": {
                                    "id": 3,
                                    "fullName": "Teacher Name"
                                  }
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void getShouldReturnNotFoundWithInvalidId() throws Exception {
        mvc.perform(get("/courses/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser
    void deleteWithRoleUserShouldReturnForbidden() throws Exception {
        mvc.perform(delete("/courses/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteShouldRemoveCourseWithSameId() throws Exception {
        mvc.perform(delete("/courses/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteShouldReturnNotFoundWithInvalidId() throws Exception {
        mvc.perform(delete("/courses/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void setStudentsInCourseShouldUpdateListOfStudents() throws Exception {
        mvc.perform(patch("/courses/1/students").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                  {
                                    "id": 1,
                                    "fullName": "Student Name",
                                    "email": "email@email.com",
                                    "phoneNumber": "N/A"
                                  },
                                  {
                                    "id": 2,
                                    "fullName": "Test Name",
                                    "email": "test@email.com",
                                    "phoneNumber": "123"
                                  }
                                ]
                                """))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(courseDto.getName()))
                .andExpect(jsonPath("$.students[0].id").value(1L))
                .andExpect(jsonPath("$.students[0].fullName").value("Student Name"))
                .andExpect(jsonPath("$.students[1].id").value(2L))
                .andExpect(jsonPath("$.students[1].fullName").value("Test Name"))
                .andExpect(jsonPath("$.teacher").value(courseDto.getTeacher()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void setStudentsWithRoleUserShouldReturnForbidden() throws Exception {
        mvc.perform(patch("/courses/1/students").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                  {
                                    "id": 1,
                                    "fullName": "Student Name",
                                    "email": "email@email.com",
                                    "phoneNumber": "N/A"
                                  },
                                  {
                                    "id": 2,
                                    "fullName": "Test Name",
                                    "email": "test@email.com",
                                    "phoneNumber": "123"
                                  }
                                ]
                                """))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void setTeacherInCourseShouldUpdateTeacher() throws Exception {
        mvc.perform(patch("/courses/1/teacher").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "fullName": "Teacher Name"
                                }
                                """))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(courseDto2.getName()))
                .andExpect(jsonPath("$.teacher.fullName").value("Teacher Name"))
                .andExpect(jsonPath("$.teacher.id").value(1L))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void setTeacherWithRoleUserShouldReturnForbidden() throws Exception {
        mvc.perform(patch("/courses/1/teacher").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "fullName": "Teacher Name"
                                }
                                """))
                .andExpect(status().isForbidden());
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public Clock clock() {
            return Clock.systemDefaultZone();
        }
    }
}
