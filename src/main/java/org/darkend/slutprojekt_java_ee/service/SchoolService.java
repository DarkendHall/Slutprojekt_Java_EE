package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.dto.SchoolDto;
import org.darkend.slutprojekt_java_ee.entity.SchoolEntity;
import org.darkend.slutprojekt_java_ee.repository.SchoolRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final ModelMapper mapper;

    public SchoolService(SchoolRepository schoolRepository, ModelMapper mapper) {
        this.schoolRepository = schoolRepository;
        this.mapper = mapper;
    }

    public SchoolDto createSchool(SchoolDto schoolDTO) {
        var entity = schoolRepository.save(mapper.map(schoolDTO, SchoolEntity.class));
        return mapper.map(entity, SchoolDto.class);
    }

    public void deleteSchool(Long id) {
        schoolRepository.deleteById(id);
    }

    public SchoolDto findSchoolById(Long id) {
        var entityOptional = schoolRepository.findById(id);
        var entity = entityOptional.orElseThrow(() -> new EntityNotFoundException("No course found with ID: " + id));
        return mapper.map(entity, SchoolDto.class);
    }

    public List<SchoolDto> findAllSchools() {
        return schoolRepository.findAll()
                .stream()
                .map(school -> mapper.map(school, SchoolDto.class))
                .toList();
    }
}
