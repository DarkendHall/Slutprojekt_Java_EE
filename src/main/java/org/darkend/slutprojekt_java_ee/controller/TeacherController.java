package org.darkend.slutprojekt_java_ee.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.darkend.slutprojekt_java_ee.dto.ObjectToJson;
import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.service.TeacherService;
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
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    public TeacherDto createTeacher(@Valid @RequestBody TeacherDto teacher, HttpServletResponse response) {
        String jsonBody = ObjectToJson.convert(teacher);
        logger.info("Received POST request with JSON body: {}", jsonBody);
        TeacherDto createdTeacher = teacherService.createTeacher(teacher);
        response.addHeader("Location", ServletUriComponentsBuilder.fromCurrentRequest()
                .build() + "/" + createdTeacher.getId());
        return createdTeacher;
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("{id}")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public void deleteTeacher(@PathVariable Long id) {
        logger.info("Received DELETE request for ID: {}", id);
        teacherService.deleteTeacher(id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("{id}")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not found")
    public TeacherDto findTeacherById(@PathVariable Long id) {
        logger.info("Received GET request for ID: {}", id);
        return teacherService.findTeacherById(id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping()
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not found")
    public List<TeacherDto> findAllTeachers() {
        logger.info("Received GET request for all teachers");
        return teacherService.findAllTeachers();
    }
}
