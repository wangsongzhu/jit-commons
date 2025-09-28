package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.UrlEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends CrudRepository<UrlEntity, Long> {

    Optional<UrlEntity> findById(@Size(max = 64) String id);

    Optional<UrlEntity> findByShortUrl(@NotNull @Size(max = 25) String shortUrl);

    List<UrlEntity> findAllByUserId(String userId);

    void deleteById(String id);

    void deleteByShortUrl(String shortUrl);

    void deleteByUserId(String userId);
}