package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.entity.SubjectEntity;
import org.darkend.slutprojekt_java_ee.service.SubjectService;
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
@RequestMapping("subjects")
public class SubjectController {

    SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping()
    public ResponseEntity<SubjectEntity> createSubject(@RequestBody SubjectEntity subject) {
        SubjectEntity createdSubject = subjectService.createSubject(subject);
        return ResponseEntity.created(URI.create("/subjects/" + createdSubject.getId()))
                .body(createdSubject);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("{id}")
    public ResponseEntity<SubjectEntity> findSubjectById(@PathVariable Long id) {
        Optional<SubjectEntity> foundSubject = subjectService.findSubjectById(id);
        return ResponseEntity.ok(foundSubject.orElseThrow(EntityNotFoundException::new));
    }

    @GetMapping()
    public ResponseEntity<List<SubjectEntity>> findAllSubjects() {
        List<SubjectEntity> allSubjects = subjectService.findAllSubjects();
        return ResponseEntity.ok(allSubjects);
    }
}
