package org.darkend.slutprojekt_java_ee.repository;

import org.darkend.slutprojekt_java_ee.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
