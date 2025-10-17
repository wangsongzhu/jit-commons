package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.UrlEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity, Long>, JpaSpecificationExecutor<UrlEntity> {

    Optional<UrlEntity> findById(@Size(max = 64) String id);

    Optional<UrlEntity> findByShortUrl(@NotNull @Size(max = 25) String shortUrl);

    // TODO to be deleted
    List<UrlEntity> findAllByUserId(String userId);

    Page<UrlEntity> findAllByUserId(String userId, Pageable pageable);

    void deleteById(String id);

    void deleteByShortUrl(String shortUrl);

    void deleteByUserId(String userId);

}