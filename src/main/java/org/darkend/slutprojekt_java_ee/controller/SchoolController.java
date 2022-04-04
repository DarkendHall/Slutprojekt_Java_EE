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
import java.util.List;

@RestController
@RequestMapping("schools")
public class SchoolController {

    private final SchoolService schoolService;
    private final Logger logger = LoggerFactory.getLogger(SchoolController.class);

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized"), @ApiResponse(responseCode = "400",
            description = "Bad Request"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public SchoolDto createSchool(@RequestBody SchoolDto school, HttpServletResponse response) {
        logger.info(String.format("Received POST request with JSON body: %s", ObjectToJson.convert(school)));
        SchoolDto createdSchool = schoolService.createSchool(school);
        response.addHeader("Location", ServletUriComponentsBuilder.fromCurrentRequest()
                .build() + "/" + createdSchool.getId());
        return createdSchool;
    }

    @DeleteMapping("{id}")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public void deleteSchool(@PathVariable Long id) {
        logger.info(String.format("Received DELETE request for ID: %d", id));
        schoolService.deleteSchool(id);
    }

    @GetMapping("{id}")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized")})
    public SchoolDto findSchoolById(@PathVariable Long id) {
        logger.info(String.format("Received GET request for ID: %d", id));
        return schoolService.findSchoolById(id);
    }

    @GetMapping()
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized")})
    public List<SchoolDto> findAllSchools() {
        logger.info("Received GET request for all schools");
        return schoolService.findAllSchools();
    }

    @PatchMapping("{id}/courses")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized"), @ApiResponse(responseCode = "400",
            description = "Bad Request"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public SchoolDto setCoursesInSchool(@PathVariable Long id, @RequestBody List<CourseDto> courses) {
        String jsonBody = ObjectToJson.convert(courses);
        logger.info("Received PATCH request with JSON body: {}", ObjectToJson.convert(courses));
        return schoolService.setCoursesInSchool(courses, id);
    }

    @PatchMapping("{id}/students")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized"), @ApiResponse(responseCode = "400",
            description = "Bad Request"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public SchoolDto setStudentsInSchool(@PathVariable Long id, @RequestBody List<StudentDto> students) {
        String jsonBody = ObjectToJson.convert(students);
        logger.info("Received PATCH request with JSON body: {}", ObjectToJson.convert(students));
        return schoolService.setStudentsInSchool(students, id);
    }

    @PatchMapping("{id}/teachers")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized"), @ApiResponse(responseCode = "400",
            description = "Bad Request"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public SchoolDto setTeachersInSchool(@PathVariable Long id, @RequestBody List<TeacherDto> teachers) {
        String jsonBody = ObjectToJson.convert(teachers);
        logger.info("Received PATCH request with JSON body: {}", ObjectToJson.convert(teachers));
        return schoolService.setTeachersInSchool(teachers, id);
    }

    @PatchMapping("{id}/principal")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized"), @ApiResponse(responseCode = "400",
            description = "Bad Request"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public SchoolDto setPrincipalInCourse(@PathVariable Long id, @RequestBody PrincipalDto principal) {
        String jsonBody = ObjectToJson.convert(principal);
        logger.info("Received PATCH request with JSON body: {}", ObjectToJson.convert(principal));
        return schoolService.setPrincipalInSchool(principal, id);
    }
}
