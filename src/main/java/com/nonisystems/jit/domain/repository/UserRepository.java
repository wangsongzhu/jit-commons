package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM UserEntity u " +
            "JOIN FETCH u.roles r " +
            "LEFT JOIN FETCH r.permissions " +
            "WHERE u.email = :email")
    Optional<UserEntity> findByEmailWithRolesAndPermissions(@Param("email") String email);
}
