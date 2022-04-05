package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.beans.ModelMapperConfig;
import org.darkend.slutprojekt_java_ee.dto.MailDto;
import org.darkend.slutprojekt_java_ee.entity.CourseEntity;
import org.darkend.slutprojekt_java_ee.entity.MailEntity;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.jms.sender.Sender;
import org.darkend.slutprojekt_java_ee.repository.CourseRepository;
import org.darkend.slutprojekt_java_ee.repository.MailRepository;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MailServiceTest {

    private MailService service;

    private final MailDto mailDto = new MailDto().setId(1L)
            .setMsg("msg");

    private final MailEntity mailEntity = new MailEntity().setId(1L)
            .setMsg("msg");

    private final CourseEntity course = new CourseEntity().setId(2L)
            .setStudents(List.of(new StudentEntity().setId(3L)
                    .setEmail("email@email.com")
                    .setFirstName("Student")
                    .setLastName("Name")
                    .setPhoneNumber("N/A")));

    private MailRepository mailRepository;

    @BeforeEach
    void setUp() {
        mailRepository = mock(MailRepository.class);
        CourseRepository courseRepository = mock(CourseRepository.class);
        ModelMapper mapper = ModelMapperConfig.getModelMapper(mock(RoleRepository.class));
        Sender sender = mock(Sender.class);

        service = new MailService(mailRepository, courseRepository, mapper, sender);

        when(courseRepository.findById(2L)).thenReturn(Optional.of(course));
        when(mailRepository.findById(1L)).thenReturn(Optional.of(mailEntity));
        when(mailRepository.findById(2L)).thenReturn(Optional.empty());

        doThrow(EmptyResultDataAccessException.class).when(mailRepository)
                .deleteById(2L);
    }

    @Test
    void newMailShouldReturnCreatedMail() {
        var result = service.newMail(mailDto, 2L);

        assertThat(result).isEqualTo(mailDto);
    }

    @Test
    void newMailWithInvalidCourseIdShouldThrowException() {
        assertThatThrownBy(() -> service.newMail(mailDto, 99L)).isExactlyInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void findMailByIdWithValidIdOne() {
        var result = service.findMailById(1L);

        assertThat(result).isEqualTo(mailDto);
    }

    @Test
    void findMailByIdWithInvalidIdTwo() {
        assertThatThrownBy(() -> service.findMailById(2L)).isExactlyInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deleteMailShouldCallDeleteInRepository() {
        assertThatNoException().isThrownBy(() -> service.deleteMail(1L));
        verify(mailRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteMailWithInvalidIdShouldThrowException() {
        assertThatThrownBy(() -> service.deleteMail(2L)).isExactlyInstanceOf(EmptyResultDataAccessException.class);
    }
}
