package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.dto.CourseDto;
import org.darkend.slutprojekt_java_ee.entity.CourseEntity;
import org.darkend.slutprojekt_java_ee.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

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
        var entity = entityOptional.orElseThrow(() -> new EntityNotFoundException("No course found with ID: " + id));
        return mapper.map(entity, CourseDto.class);
    }

    public List<CourseDto> findAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(course -> mapper.map(course, CourseDto.class))
                .toList();
    }
}
