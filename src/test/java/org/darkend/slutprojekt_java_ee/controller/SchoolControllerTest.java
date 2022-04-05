package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.CourseDto;
import org.darkend.slutprojekt_java_ee.dto.PrincipalDto;
import org.darkend.slutprojekt_java_ee.dto.SchoolDto;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.security.GlobalMethodSecurityConfig;
import org.darkend.slutprojekt_java_ee.security.SecurityConfig;
import org.darkend.slutprojekt_java_ee.service.SchoolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
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

import javax.persistence.EntityNotFoundException;
import java.time.Clock;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SchoolController.class)
@Import({ModelMapper.class, SecurityConfig.class, GlobalMethodSecurityConfig.class})
class SchoolControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SchoolService service;

    private final StudentDto student = new StudentDto().setId(2L)
            .setFullName("Student Name")
            .setPhoneNumber("N/A")
            .setEmail("email@email.com");

    private final TeacherDto teacher = new TeacherDto().setId(3L)
            .setFullName("Teacher Name");

    private final SchoolDto school = new SchoolDto().setId(1L)
            .setName("School Name")
            .setAddress("Address")
            .setCity("City")
            .setStudents(List.of(student))
            .setTeachers(List.of(teacher))
            .setPrincipal(new PrincipalDto().setId(4L)
                    .setFullName("Principal Name"))
            .setCourses(List.of(new CourseDto().setId(5L)
                    .setName("Course Name")
                    .setStudents(List.of(student))
                    .setTeacher(teacher)));

    @BeforeEach
    void setUp() {
        when(service.findSchoolById(1L)).thenReturn(school);
        when(service.findSchoolById(2L)).thenThrow(new EntityNotFoundException("No school found with ID: " + 2L));
        when(service.findAllSchools()).thenReturn(List.of(school));
        doThrow(new EmptyResultDataAccessException("No school found with ID: " + 2L, 1)).when(service)
                .deleteSchool(2L);
        when(service.createSchool(any(SchoolDto.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return args[0];
        });
        when(service.updateStudentsInSchool(List.of(new StudentDto().setId(1L)
                .setFullName("Student Name")
                .setEmail("email@email.com")
                .setPhoneNumber("N/A"), new StudentDto().setId(2L)
                .setFullName("Test Name")
                .setEmail("test@email.com")
                .setPhoneNumber("123")), 1L)).thenReturn(school);
        when(service.updateTeachersInSchool(List.of(new TeacherDto().setId(1L)
                .setFullName("Teacher Name"), new TeacherDto().setId(2L)
                .setFullName("Test Name")), 2L)).thenReturn(school);
        when(service.updateCoursesInSchool(anyList(), anyLong())).thenReturn(new SchoolDto().setId(1L)
                .setName("School Name")
                .setAddress("Address")
                .setCity("City")
                .setStudents(List.of(student))
                .setTeachers(List.of(teacher))
                .setPrincipal(new PrincipalDto().setId(4L)
                        .setFullName("Principal Name"))
                .setCourses(List.of(new CourseDto().setId(1L)
                        .setName("Course Name")
                        .setStudents(List.of(student))
                        .setTeacher(teacher))));
        when(service.updatePrincipalInSchool(new PrincipalDto().setId(1L)
                .setFullName("Principal Name"), 2L)).thenReturn(new SchoolDto().setId(1L)
                .setName("School Name")
                .setAddress("Address")
                .setCity("City")
                .setStudents(List.of(student))
                .setTeachers(List.of(teacher))
                .setPrincipal(new PrincipalDto().setId(1L)
                        .setFullName("Principal Name"))
                .setCourses(List.of(new CourseDto().setId(5L)
                        .setName("Course Name")
                        .setStudents(List.of(student))
                        .setTeacher(teacher))));
    }

    @Test
    @WithMockUser(username = "user")
    void getOneSchoolWithValidIdOne() throws Exception {
        mvc.perform(get("/schools/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(school.getId()))
                .andExpect(jsonPath("$.name").value(school.getName()))
                .andExpect(jsonPath("$.city").value(school.getCity()))
                .andExpect(jsonPath("$.address").value(school.getAddress()))
                .andExpect(jsonPath("$.principal").value(school.getPrincipal()))
                .andExpect(jsonPath("$.students[0]").value(school.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$.courses[0]").value(school.getCourses()
                        .get(0)))
                .andExpect(jsonPath("$.teachers[0]").value(school.getTeachers()
                        .get(0)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    void getOneSchoolWithInvalidIdTwo() throws Exception {
        mvc.perform(get("/schools/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser(username = "user")
    void getAllReturnsListOfAllSchools() throws Exception {
        mvc.perform(get("/schools").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(school.getId()))
                .andExpect(jsonPath("$[0].name").value(school.getName()))
                .andExpect(jsonPath("$[0].city").value(school.getCity()))
                .andExpect(jsonPath("$[0].address").value(school.getAddress()))
                .andExpect(jsonPath("$[0].principal").value(school.getPrincipal()))
                .andExpect(jsonPath("$[0].students[0]").value(school.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$[0].courses[0]").value(school.getCourses()
                        .get(0)))
                .andExpect(jsonPath("$[0].teachers[0]").value(school.getTeachers()
                        .get(0)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteOneSchoolWithValidIdOne() throws Exception {
        mvc.perform(delete("/schools/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteOneSchoolWithInvalidIdTwo() throws Exception {
        mvc.perform(delete("/schools/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addNewSchoolWithPostReturnsCreatedSchool() throws Exception {
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
                .andExpect(jsonPath("$.id").value(school.getId()))
                .andExpect(jsonPath("$.name").value(school.getName()))
                .andExpect(jsonPath("$.city").value(school.getCity()))
                .andExpect(jsonPath("$.address").value(school.getAddress()))
                .andExpect(jsonPath("$.principal").value(school.getPrincipal()))
                .andExpect(jsonPath("$.students[0]").value(school.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$.courses[0]").value(school.getCourses()
                        .get(0)))
                .andExpect(jsonPath("$.teachers[0]").value(school.getTeachers()
                        .get(0)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addInvalidSchoolWithPostReturnsBadRequest() throws Exception {
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

    @Test
    @WithMockUser(username = "user")
    void addNewSchoolWithRoleUserShouldReturnForbidden() throws Exception {
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
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void setCoursesInSchoolShouldUpdateListOfCourses() throws Exception {
        var newCourse = new CourseDto().setId(1L)
                .setName("Course Name")
                .setStudents(List.of(student))
                .setTeacher(new TeacherDto().setId(3L)
                        .setFullName("Teacher Name"));

        mvc.perform(patch("/schools/1/courses").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                  {
                                    "id": 1,
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
                                ]
                                """))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(school.getName()))
                .andExpect(jsonPath("$.courses[0].id").value(newCourse.getId()))
                .andExpect(jsonPath("$.students[0]").value(school.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$.teachers[0]").value(school.getTeachers()
                        .get(0)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void setCoursesWithRoleUserShouldReturnForbidden() throws Exception {
        var newCourse = new CourseDto().setId(1L)
                .setName("Course Name")
                .setStudents(List.of(student))
                .setTeacher(new TeacherDto().setId(3L)
                        .setFullName("Teacher Name"));

        mvc.perform(patch("/schools/1/courses").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                  {
                                    "id": 1,
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
                                ]
                                """))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void setStudentsInSchoolShouldUpdateListOfStudents() throws Exception {
        mvc.perform(patch("/schools/1/students").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                  {
                                    "id": 1,
                                    "fullName": "Student Name",
                                    "email": "email@email.com",
                                    "phoneNumber": "N/A"
                                  },
                                  {
                                    "id": 2,
                                    "fullName": "Test Name",
                                    "email": "test@email.com",
                                    "phoneNumber": "123"
                                  }
                                ]
                                """))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(school.getName()))
                .andExpect(jsonPath("$.students[0]").value(school.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$.students[0].fullName").value("Student Name"))
                .andExpect(jsonPath("$.teachers[0]").value(school.getTeachers()
                        .get(0)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void setStudentsWithRoleUserShouldReturnForbidden() throws Exception {
        mvc.perform(patch("/schools/1/students").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                  {
                                    "id": 1,
                                    "fullName": "Student Name",
                                    "email": "email@email.com",
                                    "phoneNumber": "N/A"
                                  },
                                  {
                                    "id": 2,
                                    "fullName": "Test Name",
                                    "email": "test@email.com",
                                    "phoneNumber": "123"
                                  }
                                ]
                                """))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void setTeachersInSchoolShouldUpdateListOfTeachers() throws Exception {
        mvc.perform(patch("/schools/2/teachers").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                  {
                                    "id": 1,
                                    "fullName": "Teacher Name"
                                  },
                                  {
                                    "id": 2,
                                    "fullName": "Test Name"
                                  }
                                ]
                                """))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(school.getName()))
                .andExpect(jsonPath("$.teachers[0]").value(school.getTeachers()
                        .get(0)))
                .andExpect(jsonPath("$.teachers[0].fullName").value("Teacher Name"))
                .andExpect(jsonPath("$.students[0]").value(school.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$.city").value(school.getCity()))
                .andExpect(jsonPath("$.address").value(school.getAddress()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void setTeachersWithRoleUserShouldReturnForbidden() throws Exception {
        mvc.perform(patch("/schools/2/teachers").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                  {
                                    "id": 1,
                                    "fullName": "Teacher Name"
                                  },
                                  {
                                    "id": 2,
                                    "fullName": "Test Name"
                                  }
                                ]
                                """))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void setPrincipalInSchoolShouldUpdatePrincipal() throws Exception {
        mvc.perform(patch("/schools/2/principal").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                    "id": 1,
                                    "fullName": "Principal Name"
                                  }
                                """))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(school.getName()))
                .andExpect(jsonPath("$.principal").value(new PrincipalDto().setId(1L)
                        .setFullName("Principal Name")))
                .andExpect(jsonPath("$.teachers[0]").value(school.getTeachers()
                        .get(0)))
                .andExpect(jsonPath("$.students[0]").value(school.getStudents()
                        .get(0)))
                .andExpect(jsonPath("$.city").value(school.getCity()))
                .andExpect(jsonPath("$.address").value(school.getAddress()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void setPrincipalWithRoleUserShouldReturnForbidden() throws Exception {
        mvc.perform(patch("/schools/2/principal").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                    "id": 1,
                                    "fullName": "Principal Name"
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
