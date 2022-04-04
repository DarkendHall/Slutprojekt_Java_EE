package org.darkend.slutprojekt_java_ee.service;

import org.darkend.slutprojekt_java_ee.dto.MailDto;
import org.darkend.slutprojekt_java_ee.entity.MailEntity;
import org.darkend.slutprojekt_java_ee.entity.RecipientEntity;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.jms.sender.Sender;
import org.darkend.slutprojekt_java_ee.repository.CourseRepository;
import org.darkend.slutprojekt_java_ee.repository.MailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class MailService {

    private final MailRepository mailRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper mapper;
    private final Sender sender;

    public MailService(MailRepository mailRepository, CourseRepository courseRepository, ModelMapper mapper, Sender sender) {
        this.mailRepository = mailRepository;
        this.courseRepository = courseRepository;
        this.mapper = mapper;
        this.sender = sender;
    }

    public MailDto newMail(MailDto mailDto, Long courseId) {
        var courseOptional = courseRepository.findById(courseId);
        var courseEntity = courseOptional.orElseThrow(
                () -> new EntityNotFoundException("No course found with ID: " + courseId));

        var recipients = courseEntity.getStudents()
                .stream()
                .map(StudentEntity::getEmail)
                .map(email -> new RecipientEntity().setEmail(email))
                .toList();

        var mailEntity = mapper.map(mailDto, MailEntity.class)
                .setRecipients(recipients);
        mailRepository.save(mailEntity);

        sendMail(mailEntity);
        return mapper.map(mailEntity, MailDto.class);
    }

    private void sendMail(MailEntity mail) {
        sender.sendMessage(mail);
    }

    public void deleteMail(Long id) {
        mailRepository.deleteById(id);
    }

    public MailDto findMailById(Long id) {
        var entityOptional = mailRepository.findById(id);
        var entity = entityOptional.orElseThrow(() -> new EntityNotFoundException("No mail found with ID: " + id));
        return mapper.map(entity, MailDto.class);
    }
}
