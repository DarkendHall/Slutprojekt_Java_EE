package org.darkend.slutprojekt_java_ee.integration_tests;

import org.darkend.slutprojekt_java_ee.beans.ModelMapperConfig;
import org.darkend.slutprojekt_java_ee.controller.PrincipalController;
import org.darkend.slutprojekt_java_ee.dto.CommonDto;
import org.darkend.slutprojekt_java_ee.dto.PrincipalDto;
import org.darkend.slutprojekt_java_ee.entity.PrincipalEntity;
import org.darkend.slutprojekt_java_ee.repository.PrincipalRepository;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.darkend.slutprojekt_java_ee.service.PrincipalService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrincipalController.class)
@Import({PrincipalService.class, CommonDto.class, ModelMapperConfig.class})
@MockBean(RoleRepository.class)
@AutoConfigureMockMvc(addFilters = false)
class PrincipalIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PrincipalRepository repository;

    PrincipalDto principalDto = new PrincipalDto().setId(1L)
            .setFullName("First Last");

    PrincipalEntity principalEntity = new PrincipalEntity().setId(1L)
            .setFirstName("First")
            .setLastName("Last");

    @BeforeEach
    void setUp() {
        when(repository.findById(1L)).thenReturn(Optional.of(principalEntity));
        when(repository.findAll()).thenReturn(List.of(principalEntity));
        when(repository.save(any(PrincipalEntity.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return args[0];
        });
        doThrow(new EmptyResultDataAccessException("No principal found with ID: " + 2L, 1)).when(repository)
                .deleteById(2L);
    }

    @Test
    void getShouldReturnPrincipalDtoWithCorrectValues() throws Exception {
        mvc.perform(get("/principals/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(principalDto.getId()))
                .andExpect(jsonPath("$.fullName").value(principalDto.getFullName()))
                .andExpect(status().isOk());
    }

    @Test
    void getAllShouldReturnListOfPrincipalsWithCorrectValues() throws Exception {
        mvc.perform(get("/principals").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(principalDto.getId()))
                .andExpect(jsonPath("$[0].fullName").value(principalDto.getFullName()))
                .andExpect(status().isOk());
    }

    @Test
    void createShouldReturnPrincipalWithCorrectValues() throws Exception {
        mvc.perform(post("/principals").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "fullName": "First Last"
                                }
                                """))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("First Last"))
                .andExpect(status().isCreated());

        verify(repository, times(1)).save(principalEntity);
    }

    @Test
    void getShouldReturnNotFoundWithInvalidId() throws Exception {
        mvc.perform(get("/principals/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    void deleteShouldRemovePrincipalWithSameId() throws Exception {
        mvc.perform(delete("/principals/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteShouldReturnNotFoundWithInvalidId() throws Exception {
        mvc.perform(delete("/principals/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createWithInvalidPrincipalShouldReturnBadRequest() throws Exception {
        mvc.perform(post("/principals").contentType(MediaType.APPLICATION_JSON)
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
