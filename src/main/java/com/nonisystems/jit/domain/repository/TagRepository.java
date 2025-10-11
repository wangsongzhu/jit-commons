package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.TagEntity;
import com.nonisystems.jit.domain.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    Optional<TagEntity> getTagEntityByUser(UserEntity user);

    Optional<TagEntity> getTagEntityByUserAndName(UserEntity user, @NotNull @Size(max = 256) String name);

    Optional<TagEntity> getTagEntityById(Long id);

    Optional<List<TagEntity>> findAllByUserIdOrderByName(String userId);

    boolean existsByNameAndUser(String name, UserEntity user);

    void deleteById(Long id);

    void deleteByUserId(String userId);
}
