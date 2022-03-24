package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping()
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto student) {
        StudentDto createdStudent = studentService.createStudent(student);
        return ResponseEntity.created(URI.create("/students/" + createdStudent.getId()))
                .body(createdStudent);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok()
                .build();
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("{id}")
    public ResponseEntity<StudentDto> findStudentById(@PathVariable Long id) {
        var foundStudent = studentService.findStudentById(id);
        return ResponseEntity.ok(foundStudent);
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping()
    public ResponseEntity<List<StudentDto>> findAllStudents() {
        List<StudentDto> allStudents = studentService.findAllStudents();
        return ResponseEntity.ok(allStudents);
    }
}
