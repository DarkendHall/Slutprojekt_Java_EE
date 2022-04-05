package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;
    private static final String NO_STUDENT_STRING = "No student found with ID: ";

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
        var entity = entityOptional.orElseThrow(() -> new EntityNotFoundException(NO_STUDENT_STRING + id));
        return mapper.map(entity, StudentDto.class);
    }

    public List<StudentDto> findAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(student -> mapper.map(student, StudentDto.class))
                .toList();
    }

    public StudentDto setEmail(String email, Long id) {
        var studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NO_STUDENT_STRING + id));
        studentEntity.setEmail(email);
        var savedEntity = studentRepository.save(studentEntity);
        return mapper.map(savedEntity, StudentDto.class);
    }

    public StudentDto setPhoneNumber(String phoneNumber, Long id) {
        var studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NO_STUDENT_STRING + id));
        studentEntity.setPhoneNumber(phoneNumber);
        var savedEntity = studentRepository.save(studentEntity);
        return mapper.map(savedEntity, StudentDto.class);
    }
}
