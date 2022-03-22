package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
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

    public TeacherDto createTeacher(TeacherDto teacherDTO) {
        var entity = teacherRepository.save(mapper.map(teacherDTO, TeacherEntity.class));
        return mapper.map(entity, TeacherDto.class);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    public TeacherDto findTeacherById(Long id) {
        var entityOptional = teacherRepository.findById(id);
        var entity = entityOptional.orElseThrow(() -> new EntityNotFoundException("No course found with ID: " + id));
        return mapper.map(entity, TeacherDto.class);
    }

    public List<TeacherDto> findAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(teacher -> mapper.map(teacher, TeacherDto.class))
                .toList();
    }
}
