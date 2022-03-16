package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.entity.TeacherEntity;
import org.darkend.slutprojekt_java_ee.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public TeacherEntity createTeacher(TeacherEntity teacherEntity) {
        return teacherRepository.save(teacherEntity);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    public Optional<TeacherEntity> findTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    public List<TeacherEntity> findAllTeachers() {
        return teacherRepository.findAll();
    }
}
