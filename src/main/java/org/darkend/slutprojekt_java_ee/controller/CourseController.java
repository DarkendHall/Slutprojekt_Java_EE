package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.CourseDTO;
import org.darkend.slutprojekt_java_ee.service.CourseService;
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

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping()
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO course) {
        CourseDTO createdCourse = courseService.createCourse(course);
        return ResponseEntity.created(URI.create("/courses/" + createdCourse.getId()))
                .body(createdCourse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("{id}")
    public ResponseEntity<CourseDTO> findCourseById(@PathVariable Long id) {
        CourseDTO foundCourse = courseService.findCourseById(id);
        return ResponseEntity.ok(foundCourse);
    }

    @GetMapping()
    public ResponseEntity<List<CourseDTO>> findAllCourses() {
        List<CourseDTO> allCourses = courseService.findAllCourses();
        return ResponseEntity.ok(allCourses);
    }
}
