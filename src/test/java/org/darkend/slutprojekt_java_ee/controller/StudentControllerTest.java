package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.time.Clock;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
@Import(ModelMapper.class)
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService service;

    private final StudentDto student = new StudentDto().setId(2L)
            .setFullName("Student Name")
            .setPhoneNumber("N/A")
            .setEmail("email@email.com");

    @BeforeEach
    void setUp() {
        when(service.findStudentById(1L)).thenReturn(student);
        when(service.findStudentById(2L)).thenThrow(new EntityNotFoundException("No student found with ID: " + 2L));
        when(service.findAllStudents()).thenReturn(List.of(student));
        doThrow(new EmptyResultDataAccessException("No student found with ID: " + 2L, 1)).when(service)
                .deleteStudent(2L);
        when(service.createStudent(any(StudentDto.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return args[0];
        });
        when(service.setEmail("test@test.test", 2L)).thenReturn(new StudentDto().setId(2L)
                .setFullName("Student Name")
                .setPhoneNumber("N/A")
                .setEmail("test@test.test"));
        when(service.setPhoneNumber("phoneNumber", 2L)).thenReturn(new StudentDto().setId(2L)
                .setFullName("Student Name")
                .setPhoneNumber("phoneNumber")
                .setEmail("email@email.com"));
    }

    @Test
    void getOneStudentWithValidIdOne() throws Exception {
        mvc.perform(get("/students/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.fullName").value(student.getFullName()))
                .andExpect(jsonPath("$.email").value(student.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(student.getPhoneNumber()))
                .andExpect(status().isOk());

    }

    @Test
    void getOneStudentWithInvalidIdTwo() throws Exception {
        mvc.perform(get("/students/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    void getAllReturnsListOfAllStudents() throws Exception {
        mvc.perform(get("/students").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(student.getId()))
                .andExpect(jsonPath("$[0].fullName").value(student.getFullName()))
                .andExpect(jsonPath("$[0].email").value(student.getEmail()))
                .andExpect(jsonPath("$[0].phoneNumber").value(student.getPhoneNumber()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOneStudentWithValidIdOne() throws Exception {
        mvc.perform(delete("/students/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOneStudentWithInvalidIdTwo() throws Exception {
        mvc.perform(delete("/students/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addNewStudentWithPostReturnsCreatedStudent() throws Exception {
        mvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 2,
                                  "fullName": "Student Name",
                                  "email": "email@email.com",
                                  "phoneNumber": "N/A"
                                }
                                """))
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.fullName").value(student.getFullName()))
                .andExpect(jsonPath("$.email").value(student.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(student.getPhoneNumber()))
                .andExpect(status().isCreated());
    }

    @Test
    void addInvalidStudentWithPostReturnsBadRequest() throws Exception {
        mvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 2,
                                  "fullName": "Name",
                                  "email": "email@email.com",
                                  "phoneNumber": "N/A"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void setEmailInStudentShouldUpdateEmailInStudent() throws Exception {
        mvc.perform(patch("/students/2/email").contentType(MediaType.APPLICATION_JSON)
                        .content("test@test.test"))
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.fullName").value(student.getFullName()))
                .andExpect(jsonPath("$.email").value("test@test.test"))
                .andExpect(jsonPath("$.phoneNumber").value(student.getPhoneNumber()))
                .andExpect(status().isOk());
    }

    @Test
    void setPhoneNumberInStudentShouldUpdatePhoneNumberInStudent() throws Exception {
        mvc.perform(patch("/students/2/phonenumber").contentType(MediaType.APPLICATION_JSON)
                        .content("phoneNumber"))
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.fullName").value(student.getFullName()))
                .andExpect(jsonPath("$.email").value(student.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value("phoneNumber"))
                .andExpect(status().isOk());
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public Clock clock() {
            return Clock.systemDefaultZone();
        }
    }
}
