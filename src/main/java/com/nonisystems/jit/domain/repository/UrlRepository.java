package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.UrlEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

    Optional<UrlEntity> findById(@Size(max = 64) String id);

    Optional<UrlEntity> findByShortUrl(@NotNull @Size(max = 25) String shortUrl);

    List<UrlEntity> findAllByUserId(String userId);

    List<UrlEntity> findAllByUserIdOrderByCreatedDesc(String userId);

    void deleteById(String id);

    void deleteByShortUrl(String shortUrl);

    void deleteByUserId(String userId);

}