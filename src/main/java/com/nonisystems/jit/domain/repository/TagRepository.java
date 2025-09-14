package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.TagEntity;
import com.nonisystems.jit.domain.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<TagEntity, Long> {

    Optional<TagEntity> getTagEntityByUser(UserEntity user);

    Optional<TagEntity> getTagEntityByUserAndName(UserEntity user, @NotNull @Size(max = 256) String name);

    Optional<TagEntity> getTagEntityById(Long id);
}
