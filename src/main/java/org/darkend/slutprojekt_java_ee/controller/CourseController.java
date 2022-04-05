package org.darkend.slutprojekt_java_ee.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.darkend.slutprojekt_java_ee.dto.CourseDto;
import org.darkend.slutprojekt_java_ee.dto.ObjectToJson;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.service.CourseService;
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
@RequestMapping("courses")
public class CourseController {

    private final CourseService courseService;
    private final Logger logger = LoggerFactory.getLogger(CourseController.class);

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    public CourseDto createCourse(@Valid @RequestBody CourseDto course, HttpServletResponse response) {
        String jsonBody = ObjectToJson.convert(course);
        logger.info("Received POST request with JSON body: {}", jsonBody);
        CourseDto createdCourse = courseService.createCourse(course);
        response.addHeader("Location", ServletUriComponentsBuilder.fromCurrentRequest()
                .build() + "/" + createdCourse.getId());
        return createdCourse;
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("{id}")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public void deleteCourse(@PathVariable Long id) {
        logger.info("Received DELETE request for ID: {}", id);
        courseService.deleteCourse(id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("{id}")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not found")
    public CourseDto findCourseById(@PathVariable Long id) {
        logger.info("Received GET request for ID: {}", id);
        return courseService.findCourseById(id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping()
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not found")
    public List<CourseDto> findAllCourses() {
        logger.info("Received GET request for all courses");
        return courseService.findAllCourses();
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("{id}/students")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public CourseDto setStudentsInCourse(@PathVariable Long id, @RequestBody List<StudentDto> students) {
        String jsonBody = ObjectToJson.convert(students);
        logger.info("Received PATCH request with JSON body: {}", jsonBody);
        return courseService.setStudentsInCourse(students, id);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("{id}/teacher")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public CourseDto setTeacherInCourse(@PathVariable Long id, @RequestBody TeacherDto teacher) {
        String jsonBody = ObjectToJson.convert(teacher);
        logger.info("Received PATCH request with JSON body: {}", jsonBody);
        return courseService.setTeacherInCourse(teacher, id);
    }
}
