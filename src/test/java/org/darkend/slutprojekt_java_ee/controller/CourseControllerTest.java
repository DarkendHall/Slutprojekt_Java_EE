package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.CourseDto;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.security.GlobalMethodSecurityConfig;
import org.darkend.slutprojekt_java_ee.security.SecurityConfig;
import org.darkend.slutprojekt_java_ee.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.time.Clock;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CourseController.class)
@ContextConfiguration
@Import({ModelMapper.class, SecurityConfig.class, GlobalMethodSecurityConfig.class})
class CourseControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private CourseService service;

    private final CourseDto course = new CourseDto().setId(1L)
            .setName("Course Name")
            .setStudents(List.of(new StudentDto().setId(2L)
                    .setFullName("Student Name")
                    .setPhoneNumber("N/A")
                    .setEmail("email@email.com")))
            .setTeacher(new TeacherDto().setId(3L)
                    .setFullName("Teacher Name"));

    @BeforeEach
    void setUp() {
        when(service.findCourseById(1L)).thenReturn(course);
        when(service.findCourseById(2L)).thenThrow(new EntityNotFoundException("No course found with ID: " + 2L));
        when(service.findAllCourses()).thenReturn(List.of(course));
        doThrow(new EntityNotFoundException("No course found with ID: " + 2L)).when(service)
                .deleteCourse(2L);
        when(service.createCourse(any(CourseDto.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return args[0];
        });
    }

    @Test
    @WithMockUser(username = "user")
    void getOneCourseWithValidIdOne() throws Exception {
        mvc.perform(get("/courses/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(course.getId()))
                .andExpect(jsonPath("$.name").value(course.getName()))
                .andExpect(jsonPath("$.students[0]").value(course.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$.teacher").value(course.getTeacher()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    void getOneCourseWithInvalidIdTwo() throws Exception {
        mvc.perform(get("/courses/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser(username = "user")
    void getAllReturnsListOfAllCourses() throws Exception {
        mvc.perform(get("/courses").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(course.getName()))
                .andExpect(jsonPath("$[0].students[0]").value(course.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$[0].teacher").value(course.getTeacher()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteOneCourseWithValidIdOne() throws Exception {
        mvc.perform(delete("/courses/1").accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .roles("ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteOneCourseWithInvalidIdTwo() throws Exception {
        mvc.perform(delete("/courses/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addNewCourseWithPostReturnsCreatedCourse() throws Exception {
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
                .andExpect(jsonPath("$.name").value(course.getName()))
                .andExpect(jsonPath("$.students[0]").value(course.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$.teacher").value(course.getTeacher()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addInvalidCourseWithPostReturnsBadRequest() throws Exception {
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
    @WithMockUser(username = "user")
    void addNewCourseWithRoleUserShouldReturnForbidden() throws Exception {
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

    @TestConfiguration
    static class TestConfig {

        @Bean
        public Clock clock() {
            return Clock.systemDefaultZone();
        }
    }
}
