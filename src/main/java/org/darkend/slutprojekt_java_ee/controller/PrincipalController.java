package org.darkend.slutprojekt_java_ee.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.darkend.slutprojekt_java_ee.dto.ObjectToJson;
import org.darkend.slutprojekt_java_ee.dto.PrincipalDto;
import org.darkend.slutprojekt_java_ee.service.PrincipalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({@ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "400",
            description = "Bad Request"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public PrincipalDto createPrincipal(@Valid @RequestBody PrincipalDto principal, HttpServletResponse response) {
        String jsonBody = ObjectToJson.convert(principal);
        logger.info("Received POST request with JSON body: {}", jsonBody);
        PrincipalDto createdPrincipal = principalService.createPrincipal(principal);
        response.addHeader("Location", ServletUriComponentsBuilder.fromCurrentRequest()
                .build() + "/" + createdPrincipal.getId());
        return createdPrincipal;
    }

    @DeleteMapping("{id}")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public void deletePrincipal(@PathVariable Long id) {
        logger.info("Received DELETE request for ID: {}", id);
        principalService.deletePrincipal(id);
    }

    @GetMapping("{id}")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized")})
    public PrincipalDto findPrincipalById(@PathVariable Long id) {
        logger.info("Received GET request for ID: {}", id);
        return principalService.findPrincipalById(id);
    }

    @GetMapping()
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized")})
    public List<PrincipalDto> findAllPrincipals() {
        logger.info("Received GET request for all principals");
        return principalService.findAllPrincipals();
    }
}
