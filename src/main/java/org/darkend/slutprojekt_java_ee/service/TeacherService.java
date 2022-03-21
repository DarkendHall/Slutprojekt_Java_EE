package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.dto.TeacherDTO;
import org.darkend.slutprojekt_java_ee.entity.TeacherEntity;
import org.darkend.slutprojekt_java_ee.repository.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper mapper;

    public TeacherService(TeacherRepository teacherRepository, ModelMapper mapper) {
        this.teacherRepository = teacherRepository;
        this.mapper = mapper;
    }

    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        var entity = teacherRepository.save(mapper.map(teacherDTO, TeacherEntity.class));
        return mapper.map(entity, TeacherDTO.class);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    public TeacherDTO findTeacherById(Long id) {
        var entityOptional = teacherRepository.findById(id);
        var entity = entityOptional.orElseThrow(() -> new EntityNotFoundException("No course found with ID: " + id));
        return mapper.map(entity, TeacherDTO.class);
    }

    public List<TeacherDTO> findAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(teacher -> mapper.map(teacher, TeacherDTO.class))
                .toList();
    }
}
