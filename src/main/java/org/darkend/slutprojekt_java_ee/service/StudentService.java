package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.dto.StudentDTO;
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

    public StudentDTO createStudent(StudentDTO student) {
        var entity = studentRepository.save(mapper.map(student, StudentEntity.class));
        return mapper.map(entity, StudentDTO.class);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public StudentDTO findStudentById(Long id) {
        var entityOptional = studentRepository.findById(id);
        var entity = entityOptional.orElseThrow(() -> new EntityNotFoundException("No student found with ID: " + id));
        return mapper.map(entity, StudentDTO.class);
    }

    public List<StudentDTO> findAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(student -> mapper.map(student, StudentDTO.class))
                .toList();
    }
}
