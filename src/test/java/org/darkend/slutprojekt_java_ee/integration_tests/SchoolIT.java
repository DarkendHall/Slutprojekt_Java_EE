package org.darkend.slutprojekt_java_ee.integration_tests;

import org.darkend.slutprojekt_java_ee.beans.ModelMapperConfig;
import org.darkend.slutprojekt_java_ee.controller.SchoolController;
import org.darkend.slutprojekt_java_ee.dto.CommonDto;
import org.darkend.slutprojekt_java_ee.dto.CourseDto;
import org.darkend.slutprojekt_java_ee.dto.PrincipalDto;
import org.darkend.slutprojekt_java_ee.dto.SchoolDto;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.entity.CourseEntity;
import org.darkend.slutprojekt_java_ee.entity.PrincipalEntity;
import org.darkend.slutprojekt_java_ee.entity.SchoolEntity;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.entity.TeacherEntity;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.darkend.slutprojekt_java_ee.repository.SchoolRepository;
import org.darkend.slutprojekt_java_ee.service.SchoolService;
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

@WebMvcTest(SchoolController.class)
@Import({SchoolService.class, CommonDto.class, ModelMapperConfig.class})
@MockBean(RoleRepository.class)
@AutoConfigureMockMvc(addFilters = false)
class SchoolIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SchoolRepository repository;

    private final StudentDto studentDto = new StudentDto().setId(2L)
            .setFullName("Student Name")
            .setPhoneNumber("N/A")
            .setEmail("email@email.com");

    private final TeacherDto teacherDto = new TeacherDto().setId(3L)
            .setFullName("Teacher Name");

    private final SchoolDto schoolDto = new SchoolDto().setId(1L)
            .setName("School Name")
            .setAddress("Address")
            .setCity("City")
            .setStudents(List.of(studentDto))
            .setTeachers(List.of(teacherDto))
            .setPrincipal(new PrincipalDto().setId(4L)
                    .setFullName("Principal Name"))
            .setCourses(List.of(new CourseDto().setId(5L)
                    .setName("Course Name")
                    .setStudents(List.of(studentDto))
                    .setTeacher(teacherDto)));

    private final StudentEntity studentEntity = new StudentEntity().setId(2L)
            .setFirstName("Student")
            .setLastName("Name")
            .setPhoneNumber("N/A")
            .setEmail("email@email.com");

    private final TeacherEntity teacherEntity = new TeacherEntity().setId(3L)
            .setFirstName("Teacher")
            .setLastName("Name");

    private final SchoolEntity schoolEntity = new SchoolEntity().setId(1L)
            .setName("School Name")
            .setAddress("Address")
            .setCity("City")
            .setStudents(List.of(studentEntity))
            .setTeachers(List.of(teacherEntity))
            .setPrincipal(new PrincipalEntity().setId(4L)
                    .setFirstName("Principal")
                    .setLastName("Name"))
            .setCourses(List.of(new CourseEntity().setId(5L)
                    .setName("Course Name")
                    .setStudents(List.of(studentEntity))
                    .setTeacher(teacherEntity)));

    @BeforeEach
    void setUp() {
        when(repository.findById(1L)).thenReturn(Optional.of(schoolEntity));
        when(repository.findAll()).thenReturn(List.of(schoolEntity));
        when(repository.save(any(SchoolEntity.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return args[0];
        });
        doThrow(new EmptyResultDataAccessException("No school found with ID: " + 2L, 1)).when(repository)
                .deleteById(2L);
    }

    @Test
    void getShouldReturnSchoolDtoWithCorrectValues() throws Exception {
        mvc.perform(get("/schools/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(schoolDto.getId()))
                .andExpect(jsonPath("$.name").value(schoolDto.getName()))
                .andExpect(jsonPath("$.city").value(schoolDto.getCity()))
                .andExpect(jsonPath("$.address").value(schoolDto.getAddress()))
                .andExpect(jsonPath("$.principal").value(schoolDto.getPrincipal()))
                .andExpect(jsonPath("$.students[0]").value(schoolDto.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$.courses[0]").value(schoolDto.getCourses()
                        .get(0)))
                .andExpect(jsonPath("$.teachers[0]").value(schoolDto.getTeachers()
                        .get(0)))
                .andExpect(status().isOk());
    }

    @Test
    void getAllShouldReturnListOfSchoolsWithCorrectValues() throws Exception {
        mvc.perform(get("/schools").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(schoolDto.getId()))
                .andExpect(jsonPath("$[0].name").value(schoolDto.getName()))
                .andExpect(jsonPath("$[0].city").value(schoolDto.getCity()))
                .andExpect(jsonPath("$[0].address").value(schoolDto.getAddress()))
                .andExpect(jsonPath("$[0].principal").value(schoolDto.getPrincipal()))
                .andExpect(jsonPath("$[0].students[0]").value(schoolDto.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$[0].courses[0]").value(schoolDto.getCourses()
                        .get(0)))
                .andExpect(jsonPath("$[0].teachers[0]").value(schoolDto.getTeachers()
                        .get(0)))
                .andExpect(status().isOk());
    }

    @Test
    void createShouldReturnSchoolWithCorrectValues() throws Exception {
        mvc.perform(post("/schools").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "name": "School Name",
                                  "city": "City",
                                  "address": "Address",
                                  "principal": {
                                    "id": 4,
                                    "fullName": "Principal Name"
                                  },
                                  "students": [
                                    {
                                      "id": 2,
                                      "fullName": "Student Name",
                                      "email": "email@email.com",
                                      "phoneNumber": "N/A"
                                    }
                                  ],
                                  "courses": [
                                    {
                                      "id": 5,
                                      "name": "Course Name",
                                      "students": [
                                        {
                                          "id": 2,
                                          "fullName": "Student Name",
                                          "email": "email@email.com",
                                          "phoneNumber": "N/A"
                                        }
                                      ],
                                      "teacher": {
                                        "id": 3,
                                        "fullName": "Teacher Name"
                                      }
                                    }
                                  ],
                                  "teachers": [
                                    {
                                      "id": 3,
                                      "fullName": "Teacher Name"
                                    }
                                  ]
                                }
                                """))
                .andExpect(jsonPath("$.id").value(schoolDto.getId()))
                .andExpect(jsonPath("$.name").value(schoolDto.getName()))
                .andExpect(jsonPath("$.city").value(schoolDto.getCity()))
                .andExpect(jsonPath("$.address").value(schoolDto.getAddress()))
                .andExpect(jsonPath("$.principal").value(schoolDto.getPrincipal()))
                .andExpect(jsonPath("$.students[0]").value(schoolDto.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$.courses[0]").value(schoolDto.getCourses()
                        .get(0)))
                .andExpect(jsonPath("$.teachers[0]").value(schoolDto.getTeachers()
                        .get(0)))
                .andExpect(status().isCreated());
    }

    @Test
    void getShouldReturnNotFoundWithInvalidId() throws Exception {
        mvc.perform(get("/schools/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    void deleteShouldRemoveSchoolWithSameId() throws Exception {
        mvc.perform(delete("/schools/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteShouldReturnNotFoundWithInvalidId() throws Exception {
        mvc.perform(delete("/schools/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createWithInvalidSchoolShouldReturnBadRequest() throws Exception {
        mvc.perform(post("/schools").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "name": "School Name",
                                  "city": "City",
                                  "address": "Address",
                                  "principal": {
                                    "id": 4,
                                    "fullName": "Principal Name"
                                  },
                                  "students": [
                                    {
                                      "id": 2,
                                      "fullName": "",
                                      "email": "email@email.com",
                                      "phoneNumber": "N/A"
                                    }
                                  ],
                                  "courses": [
                                    {
                                      "id": 5,
                                      "name": "Course Name",
                                      "students": [
                                        {
                                          "id": 2,
                                          "fullName": "Student Name",
                                          "email": "email@email.com",
                                          "phoneNumber": "N/A"
                                        }
                                      ],
                                      "teacher": {
                                        "id": 3,
                                        "fullName": "Teacher Name"
                                      }
                                    }
                                  ],
                                  "teachers": [
                                    {
                                      "id": 3,
                                      "fullName": "Teacher Name"
                                    }
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
