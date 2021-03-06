package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.service.TeacherService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
@Import(ModelMapper.class)
@AutoConfigureMockMvc(addFilters = false)
class TeacherControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TeacherService service;

    private final TeacherDto teacher = new TeacherDto().setId(1L)
            .setFullName("Teacher Name");

    @BeforeEach
    void setUp() {
        when(service.findTeacherById(1L)).thenReturn(teacher);
        when(service.findTeacherById(2L)).thenThrow(new EntityNotFoundException("No teacher found with ID: " + 2L));
        when(service.findAllTeachers()).thenReturn(List.of(teacher));
        doThrow(new EmptyResultDataAccessException("No teacher found with ID: " + 2L, 1)).when(service)
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

    @Test
    void addInvalidTeacherWithPostReturnsBadRequest() throws Exception {
        mvc.perform(post("/teachers").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "fullName": "Name"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public Clock clock() {
            return Clock.systemDefaultZone();
        }
    }
}
