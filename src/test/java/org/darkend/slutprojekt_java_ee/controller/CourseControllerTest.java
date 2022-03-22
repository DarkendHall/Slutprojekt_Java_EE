package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.CourseDTO;
import org.darkend.slutprojekt_java_ee.dto.StudentDTO;
import org.darkend.slutprojekt_java_ee.dto.TeacherDTO;
import org.darkend.slutprojekt_java_ee.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ModelMapper.class)
class CourseControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private CourseService service;

    private final CourseDTO course = new CourseDTO().setId(1L)
            .setName("Course Name")
            .setStudents(List.of(new StudentDTO().setId(2L)
                    .setFullName("Student Name")
                    .setPhoneNumber("N/A")
                    .setEmail("email@email.com")))
            .setTeacher(new TeacherDTO().setId(3L)
                    .setFullName("Teacher Name"));

    @BeforeEach
    void setUp() {
        when(service.findCourseById(1L)).thenReturn(course);
        when(service.findCourseById(2L)).thenThrow(new EntityNotFoundException("No course found with ID: " + 2L));
        when(service.findAllCourses()).thenReturn(List.of(course));
        doThrow(new EntityNotFoundException("No course found with ID: " + 2L)).when(service)
                .deleteCourse(2L);
        when(service.createCourse(any(CourseDTO.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return args[0];
        });
    }

    @Test
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
    void getOneCourseWithInvalidIdTwo() throws Exception {
        mvc.perform(get("/courses/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
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
    void deleteOneCourseWithValidIdOne() throws Exception {
        mvc.perform(delete("/courses/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOneCourseWithInvalidIdTwo() throws Exception {
        mvc.perform(delete("/courses/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
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
}
