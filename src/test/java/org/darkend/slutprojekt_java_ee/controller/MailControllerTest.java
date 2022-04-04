package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.MailDto;
import org.darkend.slutprojekt_java_ee.security.GlobalMethodSecurityConfig;
import org.darkend.slutprojekt_java_ee.security.SecurityConfig;
import org.darkend.slutprojekt_java_ee.service.MailService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MailController.class)
@Import({ModelMapper.class, SecurityConfig.class, GlobalMethodSecurityConfig.class})
class MailControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean()
    private MailService service;

    private final MailDto mail = new MailDto().setId(1L)
            .setMsg("Send your message here");

    @BeforeEach
    void setUp() {
        when(service.newMail(mail, 1L)).thenReturn(mail);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void newMailWithPostReturnsCreatedCourse() throws Exception {
        mvc.perform(post("/courses/1/message").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "msg": "Send your message here"
                                }
                                """))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.msg").value("Send your message here"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void newMailWithRoleUserShouldReturnForbidden() throws Exception {
        mvc.perform(post("/courses/1/message").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "msg": "Send your message here"
                                }
                                """))
                .andExpect(status().isForbidden());
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public Clock clock() {
            return Clock.systemDefaultZone();
        }
    }
}
