package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.entity.PrincipalEntity;
import org.darkend.slutprojekt_java_ee.repository.PrincipalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrincipalService {

    private final PrincipalRepository principalRepository;

    public PrincipalService(PrincipalRepository principalRepository) {
        this.principalRepository = principalRepository;
    }

    public PrincipalEntity createPrincipal(PrincipalEntity principalEntity) {
        return principalRepository.save(principalEntity);
    }

    public void deletePrincipal(Long id) {
        principalRepository.deleteById(id);
    }

    public Optional<PrincipalEntity> findPrincipalById(Long id) {
        return principalRepository.findById(id);
    }

    public List<PrincipalEntity> findAllPrincipals() {
        return principalRepository.findAll();
    }
}
