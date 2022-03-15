package org.darkend.slutprojekt_java_ee.repository;

import org.darkend.slutprojekt_java_ee.entity.CourseEntity;
import org.darkend.slutprojekt_java_ee.entity.PrincipalEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrincipalRepository extends CrudRepository<PrincipalEntity, Long> {
}
