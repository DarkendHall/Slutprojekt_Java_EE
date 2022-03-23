package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.CourseDto;
import org.darkend.slutprojekt_java_ee.dto.ObjectToJson;
import org.darkend.slutprojekt_java_ee.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {

    private final CourseService courseService;

    private final Logger logger = LoggerFactory.getLogger(CourseController.class);

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping()
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto course) {
        logger.info(String.format("Received POST request with JSON body: %s", ObjectToJson.convert(course)));
        CourseDto createdCourse = courseService.createCourse(course);
        return ResponseEntity.created(URI.create("/courses/" + createdCourse.getId()))
                .body(createdCourse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        logger.info(String.format("Received DELETE request with ID: %d", id));
        courseService.deleteCourse(id);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("{id}")
    public ResponseEntity<CourseDto> findCourseById(@PathVariable Long id) {
        logger.info(String.format("Received GET request with ID: %d", id));
        CourseDto foundCourse = courseService.findCourseById(id);
        return ResponseEntity.ok(foundCourse);
    }

    @GetMapping()
    public ResponseEntity<List<CourseDto>> findAllCourses() {
        logger.info("Received GET request for all courses");
        List<CourseDto> allCourses = courseService.findAllCourses();
        return ResponseEntity.ok(allCourses);
    }
}
