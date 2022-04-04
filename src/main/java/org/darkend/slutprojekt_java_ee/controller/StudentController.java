package org.darkend.slutprojekt_java_ee.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.darkend.slutprojekt_java_ee.dto.ObjectToJson;
import org.darkend.slutprojekt_java_ee.dto.PrincipalDto;
import org.darkend.slutprojekt_java_ee.dto.SchoolDto;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;
    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    public StudentDto createStudent(@Valid @RequestBody StudentDto student, HttpServletResponse response) {
        String jsonBody = ObjectToJson.convert(student);
        logger.info("Received POST request with JSON body: {}", jsonBody);
        StudentDto createdStudent = studentService.createStudent(student);
        response.addHeader("Location", ServletUriComponentsBuilder.fromCurrentRequest()
                .build() + "/" + createdStudent.getId());
        return createdStudent;
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("{id}")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public void deleteStudent(@PathVariable Long id) {
        logger.info("Received DELETE request for ID: {}", id);
        studentService.deleteStudent(id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("{id}")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not found")
    public StudentDto findStudentById(@PathVariable Long id) {
        logger.info("Received GET request for ID: {}", id);
        return studentService.findStudentById(id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping()
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not found")
    public List<StudentDto> findAllStudents() {
        logger.info("Received GET request for all students");
        return studentService.findAllStudents();
    }


    @PatchMapping("{id}/email")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public StudentDto setEmailInStudent(@PathVariable Long id, @RequestBody String email) {
        logger.info("Received PATCH request with JSON body: {}", email);
        return studentService.updateEmail(email, id);
    }

    @PatchMapping("{id}/phonenumber")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public StudentDto setPhoneNumberInStudent(@PathVariable Long id, @RequestBody String phoneNumber) {
        logger.info("Received PATCH request with JSON body: {}", phoneNumber);
        return studentService.updatePhoneNumber(phoneNumber, id);
    }
}
