package org.darkend.slutprojekt_java_ee.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.darkend.slutprojekt_java_ee.dto.ObjectToJson;
import org.darkend.slutprojekt_java_ee.dto.PrincipalDto;
import org.darkend.slutprojekt_java_ee.service.PrincipalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
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
import java.util.List;

@RestController
@RequestMapping("principals")
public class PrincipalController {

    private final PrincipalService principalService;
    private final Logger logger = LoggerFactory.getLogger(PrincipalController.class);

    public PrincipalController(PrincipalService principalService) {
        this.principalService = principalService;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized"), @ApiResponse(responseCode = "400",
            description = "Bad Request"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public PrincipalDto createPrincipal(@RequestBody PrincipalDto principal, HttpServletResponse response) {
        logger.info(String.format("Received POST request with JSON body: %s", ObjectToJson.convert(principal)));
        PrincipalDto createdPrincipal = principalService.createPrincipal(principal);
        response.addHeader("Location", ServletUriComponentsBuilder.fromCurrentRequest()
                .build() + "/" + createdPrincipal.getId());
        return createdPrincipal;
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("{id}")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public void deletePrincipal(@PathVariable Long id) {
        logger.info(String.format("Received DELETE request for ID: %d", id));
        principalService.deletePrincipal(id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("{id}")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized")})
    public PrincipalDto findPrincipalById(@PathVariable Long id) {
        logger.info(String.format("Received GET request for ID: %d", id));
        return principalService.findPrincipalById(id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping()
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized")})
    public List<PrincipalDto> findAllPrincipals() {
        logger.info("Received GET request for all principals");
        return principalService.findAllPrincipals();
    }
}
