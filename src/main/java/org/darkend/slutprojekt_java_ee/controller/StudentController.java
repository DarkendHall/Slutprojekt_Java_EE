package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.ObjectToJson;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.service.StudentService;
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
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping()
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto student) {
        logger.warn(String.format("Received POST request with JSON body: %s", ObjectToJson.convert(student)));
        StudentDto createdStudent = studentService.createStudent(student);
        return ResponseEntity.created(URI.create("/students/" + createdStudent.getId()))
                .body(createdStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        logger.warn(String.format("Received DELETE request with ID: %d", id));
        studentService.deleteStudent(id);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentDto> findStudentById(@PathVariable Long id) {
        logger.warn(String.format("Received GET request with ID: %d", id));
        var foundStudent = studentService.findStudentById(id);
        return ResponseEntity.ok(foundStudent);
    }

    @GetMapping()
    public ResponseEntity<List<StudentDto>> findAllStudents() {
        logger.warn("Received GET request for all students");
        List<StudentDto> allStudents = studentService.findAllStudents();
        return ResponseEntity.ok(allStudents);
    }
}
