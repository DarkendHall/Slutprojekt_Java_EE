package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.SchoolDTO;
import org.darkend.slutprojekt_java_ee.service.SchoolService;
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
@RequestMapping("schools")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping()
    public ResponseEntity<SchoolDTO> createSchool(@RequestBody SchoolDTO school) {
        SchoolDTO createdSchool = schoolService.createSchool(school);
        return ResponseEntity.created(URI.create("/schools/" + createdSchool.getId()))
                .body(createdSchool);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("{id}")
    public ResponseEntity<SchoolDTO> findSchoolById(@PathVariable Long id) {
        SchoolDTO foundSchool = schoolService.findSchoolById(id);
        return ResponseEntity.ok(foundSchool);
    }

    @GetMapping()
    public ResponseEntity<List<SchoolDTO>> findAllSchools() {
        List<SchoolDTO> allSchools = schoolService.findAllSchools();
        return ResponseEntity.ok(allSchools);
    }
}
