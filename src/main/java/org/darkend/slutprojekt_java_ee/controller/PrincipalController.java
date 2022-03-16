package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.entity.PrincipalEntity;
import org.darkend.slutprojekt_java_ee.service.PrincipalService;
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
@RequestMapping("principals")
public class PrincipalController {

    PrincipalService principalService;

    public PrincipalController(PrincipalService principalService) {
        this.principalService = principalService;
    }

    @PostMapping()
    public ResponseEntity<PrincipalEntity> createPrincipal(@RequestBody PrincipalEntity principal) {
        PrincipalEntity createdPrincipal = principalService.createPrincipal(principal);
        return ResponseEntity.created(URI.create("/principals/" + createdPrincipal.getId()))
                .body(createdPrincipal);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePrincipal(@PathVariable Long id) {
        principalService.deletePrincipal(id);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("{id}")
    public ResponseEntity<PrincipalEntity> findPrincipalById(@PathVariable Long id) {
        Optional<PrincipalEntity> foundPrincipal = principalService.findPrincipalById(id);
        return ResponseEntity.ok(foundPrincipal.orElseThrow(EntityNotFoundException::new));
    }

    @GetMapping()
    public ResponseEntity<List<PrincipalEntity>> findAllPrincipals() {
        List<PrincipalEntity> allPrincipals = principalService.findAllPrincipals();
        return ResponseEntity.ok(allPrincipals);
    }
}
