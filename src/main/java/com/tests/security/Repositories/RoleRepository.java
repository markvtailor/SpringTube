package com.tests.security.Repositories;

import com.tests.security.Entities.RoleEntity;
import com.tests.security.Entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(UserRole name);
}
