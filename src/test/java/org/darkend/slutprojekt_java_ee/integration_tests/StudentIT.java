package org.darkend.slutprojekt_java_ee.integration_tests;

import org.darkend.slutprojekt_java_ee.beans.ModelMapperConfig;
import org.darkend.slutprojekt_java_ee.controller.StudentController;
import org.darkend.slutprojekt_java_ee.dto.CommonDto;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.darkend.slutprojekt_java_ee.repository.StudentRepository;
import org.darkend.slutprojekt_java_ee.security.GlobalMethodSecurityConfig;
import org.darkend.slutprojekt_java_ee.security.SecurityConfig;
import org.darkend.slutprojekt_java_ee.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
@Import({StudentService.class, CommonDto.class, ModelMapperConfig.class, SecurityConfig.class,
        GlobalMethodSecurityConfig.class})
@MockBean(RoleRepository.class)
class StudentIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentRepository repository;

    StudentDto studentDto = new StudentDto().setId(1L)
            .setFullName("Student Name")
            .setPhoneNumber("N/A")
            .setEmail("email@email.com");

    StudentEntity studentEntity = new StudentEntity().setId(1L)
            .setFirstName("Student")
            .setLastName("Name")
            .setPhoneNumber("N/A")
            .setEmail("email@email.com");

    @BeforeEach
    void setUp() {
        when(repository.findById(1L)).thenReturn(Optional.of(studentEntity));
        when(repository.findAll()).thenReturn(List.of(studentEntity));
        when(repository.save(any(StudentEntity.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return args[0];
        });
        doThrow(new EmptyResultDataAccessException("No student found with ID: " + 2L, 1)).when(repository)
                .deleteById(2L);
    }

    @Test
    @WithMockUser
    void getShouldReturnStudentDtoWithCorrectValues() throws Exception {
        mvc.perform(get("/students/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(studentDto.getId()))
                .andExpect(jsonPath("$.fullName").value(studentDto.getFullName()))
                .andExpect(jsonPath("$.email").value(studentDto.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(studentDto.getPhoneNumber()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getAllShouldReturnListOfStudentsWithCorrectValues() throws Exception {
        mvc.perform(get("/students").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(studentDto.getId()))
                .andExpect(jsonPath("$[0].fullName").value(studentDto.getFullName()))
                .andExpect(jsonPath("$[0].email").value(studentDto.getEmail()))
                .andExpect(jsonPath("$[0].phoneNumber").value(studentDto.getPhoneNumber()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void createWithRoleUserShouldReturnForbidden() throws Exception {
        mvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "fullName": "Student Name",
                                  "email": "email@email.com",
                                  "phoneNumber": "N/A"
                                }
                                """))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createShouldReturnStudentWithCorrectValues() throws Exception {
        mvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "fullName": "Student Name",
                                  "email": "email@email.com",
                                  "phoneNumber": "N/A"
                                }
                                """))
                .andExpect(jsonPath("$.id").value(studentDto.getId()))
                .andExpect(jsonPath("$.fullName").value(studentDto.getFullName()))
                .andExpect(jsonPath("$.email").value(studentDto.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(studentDto.getPhoneNumber()))
                .andExpect(status().isCreated());

        verify(repository, times(1)).save(studentEntity);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createWithInvalidStudentShouldReturnBadRequest() throws Exception {
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
    @WithMockUser
    void getShouldReturnNotFoundWithInvalidId() throws Exception {
        mvc.perform(get("/students/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser
    void deleteWithRoleUserShouldReturnForbidden() throws Exception {
        mvc.perform(delete("/students/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteShouldRemoveStudentWithSameId() throws Exception {
        mvc.perform(delete("/students/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteShouldReturnNotFoundWithInvalidId() throws Exception {
        mvc.perform(delete("/students/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void setEmailInStudentShouldUpdateEmailInStudent() throws Exception {
        mvc.perform(patch("/students/1/email").contentType(MediaType.APPLICATION_JSON)
                        .content("test@test.test"))
                .andExpect(jsonPath("$.id").value(studentDto.getId()))
                .andExpect(jsonPath("$.fullName").value(studentDto.getFullName()))
                .andExpect(jsonPath("$.email").value("test@test.test"))
                .andExpect(jsonPath("$.phoneNumber").value(studentDto.getPhoneNumber()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void setEmailWithRoleUserShouldReturnForbidden() throws Exception {
        mvc.perform(patch("/students/1/email").contentType(MediaType.APPLICATION_JSON)
                        .content("test@test.test"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void setPhoneNumberInStudentShouldUpdatePhoneNumberInStudent() throws Exception {
        mvc.perform(patch("/students/1/phonenumber").contentType(MediaType.APPLICATION_JSON)
                        .content("phoneNumber"))
                .andExpect(jsonPath("$.id").value(studentDto.getId()))
                .andExpect(jsonPath("$.fullName").value(studentDto.getFullName()))
                .andExpect(jsonPath("$.email").value(studentDto.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value("phoneNumber"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void setPhoneNumberWithRoleUserShouldReturnForbidden() throws Exception {
        mvc.perform(patch("/students/1/phonenumber").contentType(MediaType.APPLICATION_JSON)
                        .content("phoneNumber"))
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
