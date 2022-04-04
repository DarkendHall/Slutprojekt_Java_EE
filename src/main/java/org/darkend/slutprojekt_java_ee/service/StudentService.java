package org.darkend.slutprojekt_java_ee.service;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.darkend.slutprojekt_java_ee.dto.CourseDto;
import org.darkend.slutprojekt_java_ee.dto.ObjectToJson;
import org.darkend.slutprojekt_java_ee.dto.PrincipalDto;
import org.darkend.slutprojekt_java_ee.dto.SchoolDto;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.entity.PrincipalEntity;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    public StudentService(StudentRepository studentRepository, ModelMapper mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    public StudentDto createStudent(StudentDto studentDto) {
        var entity = studentRepository.save(mapper.map(studentDto, StudentEntity.class));
        return mapper.map(entity, StudentDto.class);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public StudentDto findStudentById(Long id) {
        var entityOptional = studentRepository.findById(id);
        var entity = entityOptional.orElseThrow(() -> new EntityNotFoundException("No student found with ID: " + id));
        return mapper.map(entity, StudentDto.class);
    }

    public List<StudentDto> findAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(student -> mapper.map(student, StudentDto.class))
                .toList();
    }

    public StudentDto updateEmail(String email, Long id) {
        var studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No student found with ID: " + id));
        studentEntity.setEmail(email);
        var savedEntity = studentRepository.save(studentEntity);
        return mapper.map(savedEntity, StudentDto.class);
    }

    public StudentDto updatePhoneNumber(String phoneNumber, Long id) {
        var studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No student found with ID: " + id));
        studentEntity.setPhoneNumber(phoneNumber);
        var savedEntity = studentRepository.save(studentEntity);
        return mapper.map(savedEntity, StudentDto.class);
    }
}
