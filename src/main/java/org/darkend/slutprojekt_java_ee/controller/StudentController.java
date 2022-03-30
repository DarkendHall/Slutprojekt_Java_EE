package org.darkend.slutprojekt_java_ee.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.darkend.slutprojekt_java_ee.dto.ObjectToJson;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.service.StudentService;
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
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;
    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({@ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "400",
            description = "Bad Request"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public StudentDto createStudent(@RequestBody StudentDto student, HttpServletResponse response) {
        logger.info(String.format("Received POST request with JSON body: %s", ObjectToJson.convert(student)));
        StudentDto createdStudent = studentService.createStudent(student);
        response.addHeader("Location", ServletUriComponentsBuilder.fromCurrentRequest()
                .build() + "/" + createdStudent.getId());
        return createdStudent;
    }

    @DeleteMapping("{id}")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    public void deleteStudent(@PathVariable Long id) {
        logger.info(String.format("Received DELETE request for ID: %d", id));
        studentService.deleteStudent(id);
    }

    @GetMapping("{id}")
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized")})
    public StudentDto findStudentById(@PathVariable Long id) {
        logger.info(String.format("Received GET request for ID: %d", id));
        return studentService.findStudentById(id);
    }

    @GetMapping()
    @ApiResponses({@ApiResponse(responseCode = "404", description = "Not found"), @ApiResponse(responseCode = "401",
            description = "Unauthorized")})
    public List<StudentDto> findAllStudents() {
        logger.info("Received GET request for all students");
        return studentService.findAllStudents();
    }
}
