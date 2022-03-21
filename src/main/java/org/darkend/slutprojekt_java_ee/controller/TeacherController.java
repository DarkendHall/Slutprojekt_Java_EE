package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.TeacherDTO;
import org.darkend.slutprojekt_java_ee.service.TeacherService;
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
@RequestMapping("teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping()
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacher) {
        TeacherDTO createdTeacher = teacherService.createTeacher(teacher);
        return ResponseEntity.created(URI.create("/teachers/" + createdTeacher.getId()))
                .body(createdTeacher);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("{id}")
    public ResponseEntity<TeacherDTO> findTeacherById(@PathVariable Long id) {
        TeacherDTO foundTeacher = teacherService.findTeacherById(id);
        return ResponseEntity.ok(foundTeacher);
    }

    @GetMapping()
    public ResponseEntity<List<TeacherDTO>> findAllTeachers() {
        List<TeacherDTO> allTeachers = teacherService.findAllTeachers();
        return ResponseEntity.ok(allTeachers);
    }
}
