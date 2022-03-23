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
}
