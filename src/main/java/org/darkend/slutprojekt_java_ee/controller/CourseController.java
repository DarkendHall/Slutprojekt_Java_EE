package org.darkend.slutprojekt_java_ee.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.darkend.slutprojekt_java_ee.dto.CourseDto;
import org.darkend.slutprojekt_java_ee.dto.ObjectToJson;
import org.darkend.slutprojekt_java_ee.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized"), @ApiResponse(responseCode = "400",
            description = "Bad Request"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public CourseDto createCourse(@RequestBody CourseDto course, HttpServletResponse response) {
        logger.info(String.format("Received POST request with JSON body: %s", ObjectToJson.convert(course)));
        CourseDto createdCourse = courseService.createCourse(course);
        response.addHeader("Location", ServletUriComponentsBuilder.fromCurrentRequest()
                .build() + "/" + createdCourse.getId());
        return createdCourse;
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("{id}")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public void deleteCourse(@PathVariable Long id) {
        logger.info(String.format("Received DELETE request for ID: %d", id));
        courseService.deleteCourse(id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("{id}")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized")})
    public CourseDto findCourseById(@PathVariable Long id) {
        logger.info(String.format("Received GET request for ID: %d", id));
        return courseService.findCourseById(id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping()
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized")})
    public List<CourseDto> findAllCourses() {
        logger.info("Received GET request for all courses");
        return courseService.findAllCourses();
    }
}
