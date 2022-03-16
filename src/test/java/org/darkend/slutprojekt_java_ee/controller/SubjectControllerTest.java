package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.entity.SubjectEntity;
import org.darkend.slutprojekt_java_ee.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SubjectControllerTest {

    private final SubjectEntity subject = new SubjectEntity().setId(1L)
            .setName("Subject");

    private SubjectService service;
    private SubjectController controller;


    @BeforeEach
    void setUp() {
        service = mock(SubjectService.class);
        controller = new SubjectController(service);
    }

    @Test
    void createSubjectShouldReturnResponseCreated() {
        when(service.createSubject(subject)).thenReturn(subject);

        var result = controller.createSubject(subject);

        assertThat(result).isEqualTo(ResponseEntity.created(URI.create("/subjects/" + subject.getId()))
                .body(subject));
    }

    @Test
    void deleteSubjectShouldReturnResponseOk() {
        var result = controller.deleteSubject(1L);

        assertThat(result).isEqualTo(ResponseEntity.ok()
                .build());
    }

    @Test
    void findSubjectShouldReturnResponseOk() {
        when(service.findSubjectById(1L)).thenReturn(Optional.of(subject));

        var result = controller.findSubjectById(1L);

        assertThat(result).isEqualTo(ResponseEntity.ok(subject));
    }

    @Test
    void findAllSubjectsShouldReturnResponseOk() {
        when(service.findAllSubjects()).thenReturn(List.of(subject));

        var result = controller.findAllSubjects();

        assertThat(result).isEqualTo(ResponseEntity.ok(List.of(subject)));
    }
}