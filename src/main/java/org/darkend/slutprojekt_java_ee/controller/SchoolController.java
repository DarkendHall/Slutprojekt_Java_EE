package org.darkend.slutprojekt_java_ee.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.darkend.slutprojekt_java_ee.dto.CourseDto;
import org.darkend.slutprojekt_java_ee.dto.ObjectToJson;
import org.darkend.slutprojekt_java_ee.dto.PrincipalDto;
import org.darkend.slutprojekt_java_ee.dto.SchoolDto;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.service.SchoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("schools")
public class SchoolController {

    private final SchoolService schoolService;
    private final Logger logger = LoggerFactory.getLogger(SchoolController.class);

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    public SchoolDto createSchool(@Valid @RequestBody SchoolDto school, HttpServletResponse response) {
        String jsonBody = ObjectToJson.convert(school);
        logger.info("Received POST request with JSON body: {}", jsonBody);
        SchoolDto createdSchool = schoolService.createSchool(school);
        response.addHeader("Location", ServletUriComponentsBuilder.fromCurrentRequest()
                .build() + "/" + createdSchool.getId());
        return createdSchool;
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("{id}")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public void deleteSchool(@PathVariable Long id) {
        logger.info("Received DELETE request for ID: {}", id);
        schoolService.deleteSchool(id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("{id}")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not found")
    public SchoolDto findSchoolById(@PathVariable Long id) {
        logger.info("Received GET request for ID: {}", id);
        return schoolService.findSchoolById(id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping()
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not found")
    public List<SchoolDto> findAllSchools() {
        logger.info("Received GET request for all schools");
        return schoolService.findAllSchools();
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("{id}/courses")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public SchoolDto setCoursesInSchool(@PathVariable Long id, @RequestBody List<CourseDto> courses) {
        String jsonBody = ObjectToJson.convert(courses);
        logger.info("Received PATCH request with JSON body: {}", ObjectToJson.convert(courses));
        return schoolService.setCoursesInSchool(courses, id);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("{id}/students")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public SchoolDto setStudentsInSchool(@PathVariable Long id, @RequestBody List<StudentDto> students) {
        String jsonBody = ObjectToJson.convert(students);
        logger.info("Received PATCH request with JSON body: {}", ObjectToJson.convert(students));
        return schoolService.setStudentsInSchool(students, id);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("{id}/teachers")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public SchoolDto setTeachersInSchool(@PathVariable Long id, @RequestBody List<TeacherDto> teachers) {
        String jsonBody = ObjectToJson.convert(teachers);
        logger.info("Received PATCH request with JSON body: {}", ObjectToJson.convert(teachers));
        return schoolService.setTeachersInSchool(teachers, id);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("{id}/principal")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public SchoolDto setPrincipalInCourse(@PathVariable Long id, @RequestBody PrincipalDto principal) {
        String jsonBody = ObjectToJson.convert(principal);
        logger.info("Received PATCH request with JSON body: {}", ObjectToJson.convert(principal));
        return schoolService.setPrincipalInSchool(principal, id);
    }
}
