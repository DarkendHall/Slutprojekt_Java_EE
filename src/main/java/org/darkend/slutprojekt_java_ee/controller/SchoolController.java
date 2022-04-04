package org.darkend.slutprojekt_java_ee.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.darkend.slutprojekt_java_ee.dto.ObjectToJson;
import org.darkend.slutprojekt_java_ee.dto.SchoolDto;
import org.darkend.slutprojekt_java_ee.service.SchoolService;
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
@RequestMapping("schools")
public class SchoolController {

    private final SchoolService schoolService;
    private final Logger logger = LoggerFactory.getLogger(SchoolController.class);

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({@ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "400",
            description = "Bad Request"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public SchoolDto createSchool(@Valid @RequestBody SchoolDto school, HttpServletResponse response) {
        String jsonBody = ObjectToJson.convert(school);
        logger.info("Received POST request with JSON body: {}", jsonBody);
        SchoolDto createdSchool = schoolService.createSchool(school);
        response.addHeader("Location", ServletUriComponentsBuilder.fromCurrentRequest()
                .build() + "/" + createdSchool.getId());
        return createdSchool;
    }

    @DeleteMapping("{id}")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public void deleteSchool(@PathVariable Long id) {
        logger.info("Received DELETE request for ID: {}", id);
        schoolService.deleteSchool(id);
    }

    @GetMapping("{id}")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized")})
    public SchoolDto findSchoolById(@PathVariable Long id) {
        logger.info("Received GET request for ID: {}", id);
        return schoolService.findSchoolById(id);
    }

    @GetMapping()
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized")})
    public List<SchoolDto> findAllSchools() {
        logger.info("Received GET request for all schools");
        return schoolService.findAllSchools();
    }
}
