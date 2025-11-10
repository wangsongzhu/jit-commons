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

public interface UrlRepository extends JpaRepository<UrlEntity, String>, JpaSpecificationExecutor<UrlEntity> {

    /**
     * Find UrlEntity by shortUrl
     *
     * @param shortUrl shortUrl
     * @return UrlEntity
     */
    Optional<UrlEntity> findByShortUrl(@NotNull @Size(max = 25) String shortUrl);

    /**
     * Find UrlEntity by userId (for deleting)
     *
     * @param userId userId
     * @return List<UrlEntity>
     */
    List<UrlEntity> findAllByUserId(String userId);

    /**
     * Find UrlEntity by userId (for pagination)
     *
     * @param userId   userId
     * @param pageable pageable
     * @return Page<UrlEntity>
     */
    Page<UrlEntity> findAllByUserId(String userId, Pageable pageable);

    /**
     * Delete UrlEntity by shortUrl
     *
     * @param shortUrl shortUrl
     */
    void deleteByShortUrl(String shortUrl);

    /**
     * Delete UrlEntity by userId
     *
     * @param userId userId
     */
    void deleteByUserId(String userId);

}