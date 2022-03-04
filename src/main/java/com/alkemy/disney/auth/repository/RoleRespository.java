package com.alkemy.disney.auth.repository;

import com.alkemy.disney.auth.entity.RoleEntity;
import com.alkemy.disney.auth.entity.enumrole.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRespository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(ERole name);
}
