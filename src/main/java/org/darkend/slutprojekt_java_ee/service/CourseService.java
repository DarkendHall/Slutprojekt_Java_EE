package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.dto.CourseDto;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.entity.CourseEntity;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.entity.TeacherEntity;
import org.darkend.slutprojekt_java_ee.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper mapper;
    private final static String noCourseString = "No course found with ID: ";

    public CourseService(CourseRepository courseRepository, ModelMapper mapper) {
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    public CourseDto createCourse(CourseDto courseDto) {
        var entity = courseRepository.save(mapper.map(courseDto, CourseEntity.class));
        return mapper.map(entity, CourseDto.class);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public CourseDto findCourseById(Long id) {
        var entityOptional = courseRepository.findById(id);
        var entity = entityOptional.orElseThrow(() -> new EntityNotFoundException(noCourseString + id));
        return mapper.map(entity, CourseDto.class);
    }

    public List<CourseDto> findAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(course -> mapper.map(course, CourseDto.class))
                .toList();
    }

    public CourseDto updateStudentsInCourse(List<StudentDto> students, Long id) {
        var courseEntity = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(noCourseString + id));
        var entitiesFromDtos = students.stream()
                .map(student -> mapper.map(student, StudentEntity.class))
                .toList();
        courseEntity.setStudents(entitiesFromDtos);
        var savedEntity = courseRepository.save(courseEntity);
        return mapper.map(savedEntity, CourseDto.class);
    }

    public CourseDto updateTeacherInCourse(TeacherDto teacher, Long id) {
        var courseEntity = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(noCourseString + id));
        var entityFromDto = mapper.map(teacher, TeacherEntity.class);
        courseEntity.setTeacher(entityFromDto);
        var savedEntity = courseRepository.save(courseEntity);
        return mapper.map(savedEntity, CourseDto.class);
    }
}
