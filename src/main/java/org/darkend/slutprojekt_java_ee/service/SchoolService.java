package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.entity.SchoolEntity;
import org.darkend.slutprojekt_java_ee.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public SchoolEntity createSchool(SchoolEntity schoolEntity) {
        return schoolRepository.save(schoolEntity);
    }

    public void deleteSchool(Long id) {
        schoolRepository.deleteById(id);
    }

    public Optional<SchoolEntity> findSchoolById(Long id) {
        return schoolRepository.findById(id);
    }

    public Iterable<SchoolEntity> findAllSchools() {
        return schoolRepository.findAll();
    }
}
