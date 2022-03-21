package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.dto.PrincipalDTO;
import org.darkend.slutprojekt_java_ee.entity.PrincipalEntity;
import org.darkend.slutprojekt_java_ee.repository.PrincipalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PrincipalService {

    private final PrincipalRepository principalRepository;
    private final ModelMapper mapper;

    public PrincipalService(PrincipalRepository principalRepository, ModelMapper mapper) {
        this.principalRepository = principalRepository;
        this.mapper = mapper;
    }

    public PrincipalDTO createPrincipal(PrincipalDTO principalDTO) {
        var entity = principalRepository.save(mapper.map(principalDTO, PrincipalEntity.class));
        return mapper.map(entity, PrincipalDTO.class);
    }

    public void deletePrincipal(Long id) {
        principalRepository.deleteById(id);
    }

    public PrincipalDTO findPrincipalById(Long id) {
        var entityOptional = principalRepository.findById(id);
        var entity = entityOptional.orElseThrow(() -> new EntityNotFoundException("No course found with ID: " + id));
        return mapper.map(entity, PrincipalDTO.class);
    }

    public List<PrincipalDTO> findAllPrincipals() {
        return principalRepository.findAll()
                .stream()
                .map(principal -> mapper.map(principal, PrincipalDTO.class))
                .toList();
    }
}
