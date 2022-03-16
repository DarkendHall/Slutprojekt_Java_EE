package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.entity.SubjectEntity;
import org.darkend.slutprojekt_java_ee.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public SubjectEntity createSubject(SubjectEntity subjectEntity) {
        return subjectRepository.save(subjectEntity);
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }

    public Optional<SubjectEntity> findSubjectById(Long id) {
        return subjectRepository.findById(id);
    }

    public List<SubjectEntity> findAllSubjects() {
        return subjectRepository.findAll();
    }
}
