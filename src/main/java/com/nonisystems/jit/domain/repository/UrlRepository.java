package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.UrlEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity, String>, JpaSpecificationExecutor<UrlEntity> {

    /**
     * Find url by id and user id
     *
     * @param id     url id
     * @param userId user id
     * @return Optional<UrlEntity>
     */
    Optional<UrlEntity> findByIdAndUser_Id(String id, String userId);

    /**
     * Find UrlEntity by shortUrl
     *
     * @param shortUrl shortUrl
     * @param userId   user id
     * @return UrlEntity
     */
    Optional<UrlEntity> findByShortUrlAndUser_Id(String shortUrl, String userId);

    /**
     * Find UrlEntity by userId (for deleting)
     *
     * @param userId user id
     * @return List<UrlEntity>
     */
    List<UrlEntity> findAllByUser_Id(String userId);

    /**
     * Find UrlEntity by userId (for pagination)
     *
     * @param userId   user id
     * @param pageable pageable
     * @return Page<UrlEntity>
     */
    Page<UrlEntity> findAllByUser_Id(String userId, Pageable pageable);

    /**
     * Delete UrlEntity by shortUrl
     *
     * @param shortUrl shortUrl
     * @param userId   user id
     */
    void deleteByShortUrlAndUser_Id(String shortUrl, String userId);

    /**
     * Delete UrlEntity by userId
     *
     * @param userId userId
     */
    void deleteAllByUser_Id(String userId);

}