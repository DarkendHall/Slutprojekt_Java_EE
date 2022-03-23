package org.darkend.slutprojekt_java_ee.controller;

import org.darkend.slutprojekt_java_ee.dto.ObjectToJson;
import org.darkend.slutprojekt_java_ee.dto.PrincipalDto;
import org.darkend.slutprojekt_java_ee.service.PrincipalService;
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
@RequestMapping("principals")
public class PrincipalController {

    private final PrincipalService principalService;
    private final Logger logger = LoggerFactory.getLogger(PrincipalController.class);

    public PrincipalController(PrincipalService principalService) {
        this.principalService = principalService;
    }

    @PostMapping()
    public ResponseEntity<PrincipalDto> createPrincipal(@RequestBody PrincipalDto principal) {
        logger.info(String.format("Received POST request with JSON body: %s", ObjectToJson.convert(principal)));
        PrincipalDto createdPrincipal = principalService.createPrincipal(principal);
        return ResponseEntity.created(URI.create("/principals/" + createdPrincipal.getId()))
                .body(createdPrincipal);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePrincipal(@PathVariable Long id) {
        logger.info(String.format("Received DELETE request with ID: %d", id));
        principalService.deletePrincipal(id);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("{id}")
    public ResponseEntity<PrincipalDto> findPrincipalById(@PathVariable Long id) {
        logger.info(String.format("Received GET request with ID: %d", id));
        PrincipalDto foundPrincipal = principalService.findPrincipalById(id);
        return ResponseEntity.ok(foundPrincipal);
    }

    @GetMapping()
    public ResponseEntity<List<PrincipalDto>> findAllPrincipals() {
        logger.info("Received GET request for all principals");
        List<PrincipalDto> allPrincipals = principalService.findAllPrincipals();
        return ResponseEntity.ok(allPrincipals);
    }
}
