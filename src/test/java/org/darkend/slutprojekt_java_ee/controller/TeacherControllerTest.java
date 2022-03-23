package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.service.TeacherService;
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
@WebMvcTest(TeacherController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ModelMapper.class)
class TeacherControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private TeacherService service;

    private final TeacherDto teacher = new TeacherDto().setId(1L)
            .setFullName("Teacher Name");

    @BeforeEach
    void setUp() {
        when(service.findTeacherById(1L)).thenReturn(teacher);
        when(service.findTeacherById(2L)).thenThrow(new EntityNotFoundException("No teacher found with ID: " + 2L));
        when(service.findAllTeachers()).thenReturn(List.of(teacher));
        doThrow(new EntityNotFoundException("No teacher found with ID: " + 2L)).when(service)
                .deleteTeacher(2L);
        when(service.createTeacher(any(TeacherDto.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return args[0];
        });
    }

    @Test
    void getOneTeacherWithValidIdOne() throws Exception {
        mvc.perform(get("/teachers/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(teacher.getId()))
                .andExpect(jsonPath("$.fullName").value(teacher.getFullName()))
                .andExpect(status().isOk());
    }

    @Test
    void getOneTeacherWithInvalidIdTwo() throws Exception {
        mvc.perform(get("/teachers/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    void getAllReturnsListOfAllTeachers() throws Exception {
        mvc.perform(get("/teachers").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(teacher.getId()))
                .andExpect(jsonPath("$[0].fullName").value(teacher.getFullName()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOneTeacherWithValidIdOne() throws Exception {
        mvc.perform(delete("/teachers/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOneTeacherWithInvalidIdTwo() throws Exception {
        mvc.perform(delete("/teachers/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addNewTeacherWithPostReturnsCreatedTeacher() throws Exception {
        mvc.perform(post("/teachers").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "fullName": "Teacher Name"
                                }
                                """))
                .andExpect(jsonPath("$.id").value(teacher.getId()))
                .andExpect(jsonPath("$.fullName").value(teacher.getFullName()))
                .andExpect(status().isCreated());
    }
}
