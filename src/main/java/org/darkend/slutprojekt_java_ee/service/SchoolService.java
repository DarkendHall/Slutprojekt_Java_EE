package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.dto.SchoolDTO;
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

    public SchoolDTO createSchool(SchoolDTO schoolDTO) {
        var entity = schoolRepository.save(mapper.map(schoolDTO, SchoolEntity.class));
        return mapper.map(entity, SchoolDTO.class);
    }

    public void deleteSchool(Long id) {
        schoolRepository.deleteById(id);
    }

    public SchoolDTO findSchoolById(Long id) {
        var entityOptional = schoolRepository.findById(id);
        var entity = entityOptional.orElseThrow(() -> new EntityNotFoundException("No course found with ID: " + id));
        return mapper.map(entity, SchoolDTO.class);
    }

    public List<SchoolDTO> findAllSchools() {
        return schoolRepository.findAll()
                .stream()
                .map(school -> mapper.map(school, SchoolDTO.class))
                .toList();
    }
}
