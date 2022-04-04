package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.UserDtoIn;
import org.darkend.slutprojekt_java_ee.dto.UserDtoOut;
import org.darkend.slutprojekt_java_ee.security.GlobalMethodSecurityConfig;
import org.darkend.slutprojekt_java_ee.security.SecurityConfig;
import org.darkend.slutprojekt_java_ee.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({ModelMapper.class, SecurityConfig.class, GlobalMethodSecurityConfig.class})
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    private final UserDtoIn userIn = new UserDtoIn().setId(2L)
            .setUsername("user")
            .setPassword("password")
            .setRoles(List.of("ROLE_USER"));

    private final UserDtoOut userOut = new UserDtoOut().setId(2L)
            .setUsername("user")
            .setRoles(List.of("ROLE_USER"));

    @BeforeEach
    void setUp() {
        when(service.createUser(any())).thenReturn(userOut);
    }

    @Test
    void addNewUserWithPostReturnsCreatedUser() throws Exception {
        mvc.perform(post("/users/signup").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 2,
                                  "username": "user",
                                  "password": "password",
                                  "roles": [
                                  	"ROLE_USER"
                                  ]
                                }
                                """))
                .andExpect(jsonPath("$.id").value(userIn.getId()))
                .andExpect(jsonPath("$.username").value(userIn.getUsername()))
                .andExpect(jsonPath("$.roles[0]").value(userIn.getRoles()
                        .get(0)))
                .andExpect(status().isCreated());
    }

    @Test
    void addInvalidUsersWithPostReturnsBadRequest() throws Exception {
        mvc.perform(post("/users/signup").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 2,
                                  "username": "user",
                                  "password": "pass",
                                  "roles": [
                                  	"ROLE_USER"
                                  ]
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
