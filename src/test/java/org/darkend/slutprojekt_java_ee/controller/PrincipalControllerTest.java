package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.entity.PrincipalEntity;
import org.darkend.slutprojekt_java_ee.service.PrincipalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PrincipalControllerTest {

    private final PrincipalEntity principal = new PrincipalEntity().setId(1L)
            .setFirstName("Principal");

    private PrincipalService service;
    private PrincipalController controller;


    @BeforeEach
    void setUp() {
        service = mock(PrincipalService.class);
        controller = new PrincipalController(service);
    }

    @Test
    void createPrincipalShouldReturnResponseCreated() {
        when(service.createPrincipal(principal)).thenReturn(principal);

        var result = controller.createPrincipal(principal);

        assertThat(result).isEqualTo(ResponseEntity.created(URI.create("/principals/" + principal.getId()))
                .body(principal));
    }

    @Test
    void deletePrincipalShouldReturnResponseOk() {
        var result = controller.deletePrincipal(1L);

        assertThat(result).isEqualTo(ResponseEntity.ok()
                .build());
    }

    @Test
    void findPrincipalShouldReturnResponseOk() {
        when(service.findPrincipalById(1L)).thenReturn(Optional.of(principal));

        var result = controller.findPrincipalById(1L);

        assertThat(result).isEqualTo(ResponseEntity.ok(principal));
    }

    @Test
    void findAllPrincipalsShouldReturnResponseOk() {
        when(service.findAllPrincipals()).thenReturn(List.of(principal));

        var result = controller.findAllPrincipals();

        assertThat(result).isEqualTo(ResponseEntity.ok(List.of(principal)));
    }
}