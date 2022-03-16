package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.entity.SchoolEntity;
import org.darkend.slutprojekt_java_ee.service.SchoolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("schools")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping()
    public ResponseEntity<SchoolEntity> createSchool(@RequestBody SchoolEntity school) {
        SchoolEntity createdSchool = schoolService.createSchool(school);
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
    public ResponseEntity<SchoolEntity> findSchoolById(@PathVariable Long id) {
        Optional<SchoolEntity> foundSchool = schoolService.findSchoolById(id);
        return ResponseEntity.ok(foundSchool.orElseThrow(EntityNotFoundException::new));
    }

    @GetMapping()
    public ResponseEntity<List<SchoolEntity>> findAllSchools() {
        List<SchoolEntity> allSchools = schoolService.findAllSchools();
        return ResponseEntity.ok(allSchools);
    }
}
