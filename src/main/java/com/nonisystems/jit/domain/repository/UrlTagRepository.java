package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.UrlTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UrlTagRepository extends JpaRepository<UrlTagEntity, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM UrlTagEntity utm WHERE utm.url.id = :urlId")
    void deleteAllByUrlId(@Param("urlId") String urlId);

    Optional<List<UrlTagEntity>> findAllByUrlId(String urlId);

    Optional<List<UrlTagEntity>> findAllByTagId(Long tagId);
}
