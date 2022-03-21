package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.PrincipalDTO;
import org.darkend.slutprojekt_java_ee.service.PrincipalService;
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
@RequestMapping("principals")
public class PrincipalController {

    private final PrincipalService principalService;

    public PrincipalController(PrincipalService principalService) {
        this.principalService = principalService;
    }

    @PostMapping()
    public ResponseEntity<PrincipalDTO> createPrincipal(@RequestBody PrincipalDTO principal) {
        PrincipalDTO createdPrincipal = principalService.createPrincipal(principal);
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
    public ResponseEntity<PrincipalDTO> findPrincipalById(@PathVariable Long id) {
        PrincipalDTO foundPrincipal = principalService.findPrincipalById(id);
        return ResponseEntity.ok(foundPrincipal);
    }

    @GetMapping()
    public ResponseEntity<List<PrincipalDTO>> findAllPrincipals() {
        List<PrincipalDTO> allPrincipals = principalService.findAllPrincipals();
        return ResponseEntity.ok(allPrincipals);
    }
}
