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

    Optional<UserEntity> findBySub( String sub);

    @Query("SELECT u FROM UserEntity u " +
            "JOIN FETCH u.role r " +
            "LEFT JOIN FETCH r.permissions " +
            "WHERE u.sub = :sub")
    Optional<UserEntity> findBySubWithRoleAndPermissions(@Param("sub") String sub);

    @Query("SELECT u FROM UserEntity u " +
            "JOIN FETCH u.role r " +
            "LEFT JOIN FETCH r.permissions " +
            "WHERE u.email = :email")
    Optional<UserEntity> findByEmailWithRoleAndPermissions(@Param("email") String email);
}
