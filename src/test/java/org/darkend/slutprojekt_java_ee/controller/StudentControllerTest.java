package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.service.StudentService;
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
@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ModelMapper.class)
class StudentControllerTest {

    @Autowired
    MockMvc mvc;

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
        doThrow(new EntityNotFoundException("No student found with ID: " + 2L)).when(service)
                .deleteStudent(2L);
        when(service.createStudent(any(StudentDto.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return args[0];
        });
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
}
