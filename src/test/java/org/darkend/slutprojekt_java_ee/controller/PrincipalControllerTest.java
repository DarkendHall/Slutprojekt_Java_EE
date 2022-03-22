package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.PrincipalDTO;
import org.darkend.slutprojekt_java_ee.service.PrincipalService;
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
@WebMvcTest(PrincipalController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ModelMapper.class)
class PrincipalControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private PrincipalService service;

    private final PrincipalDTO principal = new PrincipalDTO().setId(1L)
            .setFullName("Principal Name");

    @BeforeEach
    void setUp() {
        when(service.findPrincipalById(1L)).thenReturn(principal);
        when(service.findPrincipalById(2L)).thenThrow(new EntityNotFoundException("No principal found with ID: " + 2L));
        when(service.findAllPrincipals()).thenReturn(List.of(principal));
        doThrow(new EntityNotFoundException("No principal found with ID: " + 2L)).when(service)
                .deletePrincipal(2L);
        when(service.createPrincipal(any(PrincipalDTO.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return args[0];
        });
    }

    @Test
    void getOnePrincipalWithValidIdOne() throws Exception {
        mvc.perform(get("/principals/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(principal.getId()))
                .andExpect(jsonPath("$.fullName").value(principal.getFullName()))
                .andExpect(status().isOk());

    }

    @Test
    void getOnePrincipalWithInvalidIdTwo() throws Exception {
        mvc.perform(get("/principals/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    void getAllReturnsListOfAllPrincipals() throws Exception {
        mvc.perform(get("/principals").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(principal.getId()))
                .andExpect(jsonPath("$[0].fullName").value(principal.getFullName()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOnePrincipalWithValidIdOne() throws Exception {
        mvc.perform(delete("/principals/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOnePrincipalWithInvalidIdTwo() throws Exception {
        mvc.perform(delete("/principals/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
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
}