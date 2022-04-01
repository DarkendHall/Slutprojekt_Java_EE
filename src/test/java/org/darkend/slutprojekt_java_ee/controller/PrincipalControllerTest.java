package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.PrincipalDto;
import org.darkend.slutprojekt_java_ee.security.GlobalMethodSecurityConfig;
import org.darkend.slutprojekt_java_ee.security.SecurityConfig;
import org.darkend.slutprojekt_java_ee.service.PrincipalService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
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

@WebMvcTest(PrincipalController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({ModelMapper.class, SecurityConfig.class, GlobalMethodSecurityConfig.class})
class PrincipalControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private PrincipalService service;

    private final PrincipalDto principal = new PrincipalDto().setId(1L)
            .setFullName("Principal Name");

    @BeforeEach
    void setUp() {
        when(service.findPrincipalById(1L)).thenReturn(principal);
        when(service.findPrincipalById(2L)).thenThrow(new EntityNotFoundException("No principal found with ID: " + 2L));
        when(service.findAllPrincipals()).thenReturn(List.of(principal));
        doThrow(new EmptyResultDataAccessException("No principal found with ID: " + 2L, 1)).when(service)
                .deletePrincipal(2L);
        when(service.createPrincipal(any(PrincipalDto.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return args[0];
        });
    }

    @Test
    @WithMockUser(username = "user")
    void getOnePrincipalWithValidIdOne() throws Exception {
        mvc.perform(get("/principals/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(principal.getId()))
                .andExpect(jsonPath("$.fullName").value(principal.getFullName()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    void getOnePrincipalWithInvalidIdTwo() throws Exception {
        mvc.perform(get("/principals/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser(username = "user")
    void getAllReturnsListOfAllPrincipals() throws Exception {
        mvc.perform(get("/principals").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(principal.getId()))
                .andExpect(jsonPath("$[0].fullName").value(principal.getFullName()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteOnePrincipalWithValidIdOne() throws Exception {
        mvc.perform(delete("/principals/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteOnePrincipalWithInvalidIdTwo() throws Exception {
        mvc.perform(delete("/principals/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addNewPrincipalWithPostReturnsCreatedPrincipal() throws Exception {
        mvc.perform(post("/principals").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "fullName": "Principal Name"
                                }
                                """))
                .andExpect(jsonPath("$.id").value(principal.getId()))
                .andExpect(jsonPath("$.fullName").value(principal.getFullName()))
                .andExpect(status().isCreated());
    }

    @Test
    void addInvalidPrincipalWithPostReturnsBadRequest() throws Exception {
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
