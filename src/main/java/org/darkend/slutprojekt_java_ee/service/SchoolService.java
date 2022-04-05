package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.dto.CourseDto;
import org.darkend.slutprojekt_java_ee.dto.PrincipalDto;
import org.darkend.slutprojekt_java_ee.dto.SchoolDto;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.entity.CourseEntity;
import org.darkend.slutprojekt_java_ee.entity.PrincipalEntity;
import org.darkend.slutprojekt_java_ee.entity.SchoolEntity;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.entity.TeacherEntity;
import org.darkend.slutprojekt_java_ee.repository.SchoolRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final ModelMapper mapper;
    private static final String NO_SCHOOL_STRING = "No school found with ID: ";
    private static final String NO_COURSE_STRING = "No course found with ID: ";


    public SchoolService(SchoolRepository schoolRepository, ModelMapper mapper) {
        this.schoolRepository = schoolRepository;
        this.mapper = mapper;
    }

    public SchoolDto createSchool(SchoolDto schoolDto) {
        var entity = schoolRepository.save(mapper.map(schoolDto, SchoolEntity.class));
        return mapper.map(entity, SchoolDto.class);
    }

    public void deleteSchool(Long id) {
        schoolRepository.deleteById(id);
    }

    public SchoolDto findSchoolById(Long id) {
        var entityOptional = schoolRepository.findById(id);
        var entity = entityOptional.orElseThrow(() -> new EntityNotFoundException(NO_COURSE_STRING + id));
        return mapper.map(entity, SchoolDto.class);
    }

    public List<SchoolDto> findAllSchools() {
        return schoolRepository.findAll()
                .stream()
                .map(school -> mapper.map(school, SchoolDto.class))
                .toList();
    }

    public SchoolDto setCoursesInSchool(List<CourseDto> courses, Long id) {
        var schoolEntity = schoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NO_SCHOOL_STRING + id));
        var entitiesFromDtos = courses.stream()
                .map(course -> mapper.map(course, CourseEntity.class))
                .toList();
        schoolEntity.setCourses(entitiesFromDtos);
        var savedEntity = schoolRepository.save(schoolEntity);
        return mapper.map(savedEntity, SchoolDto.class);
    }

    public SchoolDto setStudentsInSchool(List<StudentDto> students, Long id) {
        var schoolEntity = schoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NO_SCHOOL_STRING + id));
        var entitiesFromDtos = students.stream()
                .map(student -> mapper.map(student, StudentEntity.class))
                .toList();
        schoolEntity.setStudents(entitiesFromDtos);
        var savedEntity = schoolRepository.save(schoolEntity);
        return mapper.map(savedEntity, SchoolDto.class);
    }

    public SchoolDto setTeachersInSchool(List<TeacherDto> teachers, Long id) {
        var schoolEntity = schoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NO_SCHOOL_STRING + id));
        var entitiesFromDtos = teachers.stream()
                .map(teacher -> mapper.map(teacher, TeacherEntity.class))
                .toList();
        schoolEntity.setTeachers(entitiesFromDtos);
        var savedEntity = schoolRepository.save(schoolEntity);
        return mapper.map(savedEntity, SchoolDto.class);
    }

    public SchoolDto setPrincipalInSchool(PrincipalDto principal, Long id) {
        var schoolEntity = schoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NO_COURSE_STRING + id));
        var entityFromDto = mapper.map(principal, PrincipalEntity.class);
        schoolEntity.setPrincipal(entityFromDto);
        var savedEntity = schoolRepository.save(schoolEntity);
        return mapper.map(savedEntity, SchoolDto.class);
    }
}
