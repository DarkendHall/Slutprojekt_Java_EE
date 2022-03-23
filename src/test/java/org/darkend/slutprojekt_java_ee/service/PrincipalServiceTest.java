package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.beans.ModelMapperConfig;
import org.darkend.slutprojekt_java_ee.dto.PrincipalDto;
import org.darkend.slutprojekt_java_ee.entity.PrincipalEntity;
import org.darkend.slutprojekt_java_ee.repository.PrincipalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrincipalServiceTest {

    private PrincipalService service;
    private PrincipalRepository repository;

    private final PrincipalDto principalDto = new PrincipalDto().setId(1L)
            .setFullName("Principal Name");

    private final PrincipalEntity principalEntity = new PrincipalEntity().setId(1L)
            .setFirstName("Principal")
            .setLastName("Name");


    @BeforeEach
    void setUp() {
        repository = mock(PrincipalRepository.class);
        ModelMapper mapper = ModelMapperConfig.getModelMapper();
        service = new PrincipalService(repository, mapper);
        when(repository.save(principalEntity)).thenReturn(principalEntity);
        when(repository.findById(1L)).thenReturn(Optional.of(principalEntity));
        when(repository.findById(2L)).thenReturn(Optional.empty());
        when(repository.findAll()).thenReturn(List.of(principalEntity));
    }

    @Test
    void createPrincipalWithValidPrincipalShouldReturnCreatedPrincipalAsDto() {
        var result = service.createPrincipal(principalDto);

        assertThat(result).isEqualTo(principalDto);
    }

    @Test
    void createPrincipalWithInvalidPrincipalShouldThrowException() {
        var invalidPrincipalDto = new PrincipalDto().setId(1L)
                .setFullName(null);

        var invalidPrincipalEntity = new PrincipalEntity().setId(1L)
                .setFirstName(null)
                .setLastName(null);

        doThrow(new ConstraintViolationException(null, null)).when(repository)
                .save(invalidPrincipalEntity);

        assertThatThrownBy(() -> service.createPrincipal(invalidPrincipalDto)).isInstanceOfAny(
                ConstraintViolationException.class, MappingException.class);
    }

    @Test
    void deletePrincipalShouldCallDeletePrincipalInRepository() {
        service.deletePrincipal(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deletePrincipalWithInvalidIdShouldThrowException() {
        doThrow(new EmptyResultDataAccessException(1)).when(repository)
                .deleteById(2L);

        assertThatThrownBy(() -> service.deletePrincipal(2L));
    }

    @Test
    void findPrincipalByIdWithValidIdOne() {
        var result = service.findPrincipalById(1L);

        assertThat(result).isEqualTo(principalDto.setId(1L));
    }

    @Test
    void findPrincipalByIdWithInvalidIdTwo() {
        assertThatThrownBy(() -> service.findPrincipalById(2L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void findAllPrincipalsShouldReturnAllPrincipals() {
        var result = service.findAllPrincipals();

        assertThat(result).isEqualTo(List.of(principalDto));
    }
}
