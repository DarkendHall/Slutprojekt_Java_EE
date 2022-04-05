package org.darkend.slutprojekt_java_ee.repository;

import org.darkend.slutprojekt_java_ee.entity.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends JpaRepository<MailEntity, Long> {
}
