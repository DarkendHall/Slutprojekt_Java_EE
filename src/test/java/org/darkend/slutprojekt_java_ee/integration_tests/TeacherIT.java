package org.darkend.slutprojekt_java_ee.integration_tests;

import org.darkend.slutprojekt_java_ee.beans.ModelMapperConfig;
import org.darkend.slutprojekt_java_ee.controller.TeacherController;
import org.darkend.slutprojekt_java_ee.dto.CommonDto;
import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.entity.TeacherEntity;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.darkend.slutprojekt_java_ee.repository.TeacherRepository;
import org.darkend.slutprojekt_java_ee.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import java.time.Clock;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeacherController.class)
@Import({TeacherService.class, CommonDto.class, ModelMapperConfig.class})
@MockBean(RoleRepository.class)
@AutoConfigureMockMvc(addFilters = false)
class TeacherIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TeacherRepository repository;

    TeacherDto teacherDto = new TeacherDto().setId(1L)
            .setFullName("First Last");

    TeacherEntity teacherEntity = new TeacherEntity().setId(1L)
            .setFirstName("First")
            .setLastName("Last");

    @BeforeEach
    void setUp() {
        when(repository.findById(1L)).thenReturn(Optional.of(teacherEntity));
        when(repository.findAll()).thenReturn(List.of(teacherEntity));
        when(repository.save(any(TeacherEntity.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return args[0];
        });
        doThrow(new EmptyResultDataAccessException("No teacher found with ID: " + 2L, 1)).when(repository)
                .deleteById(2L);
    }

    @Test
    void getShouldReturnTeacherDtoWithCorrectValues() throws Exception {
        mvc.perform(get("/teachers/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(teacherDto.getId()))
                .andExpect(jsonPath("$.fullName").value(teacherDto.getFullName()))
                .andExpect(status().isOk());
    }

    @Test
    void getAllShouldReturnListOfTeachersWithCorrectValues() throws Exception {
        mvc.perform(get("/teachers").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(teacherDto.getId()))
                .andExpect(jsonPath("$[0].fullName").value(teacherDto.getFullName()))
                .andExpect(status().isOk());
    }

    @Test
    void createShouldReturnTeacherWithCorrectValues() throws Exception {
        mvc.perform(post("/teachers").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "fullName": "First Last"
                                }
                                """))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("First Last"))
                .andExpect(status().isCreated());

        verify(repository, atMostOnce()).save(teacherEntity);
    }

    @Test
    void getShouldReturnNotFoundWithInvalidId() throws Exception {
        mvc.perform(get("/teachers/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    void deleteShouldRemoveTeacherWithSameId() throws Exception {
        mvc.perform(delete("/teachers/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteShouldReturnNotFoundWithInvalidId() throws Exception {
        mvc.perform(delete("/teachers" +
                        "/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public Clock clock() {
            return Clock.systemDefaultZone();
        }
    }
}
