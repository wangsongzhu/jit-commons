package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, String>, JpaSpecificationExecutor<TagEntity> {

    /**
     * Find tag by id and user id
     *
     * @param id     tag id
     * @param userId user id
     * @return Optional<TagEntity>
     */
    Optional<TagEntity> findByIdAndUser_Id(String id, String userId);

    /**
     * Find tags by userId (for pagination)
     *
     * @param userId   userId
     * @param pageable pageable
     * @return Page<TagEntity>
     */
    Page<TagEntity> findAllByUser_Id(String userId, Pageable pageable);

    /**
     * Find tags by User ID (for sorting)
     *
     * @param userId User ID
     * @param sort   Sort
     * @return List<TagEntity>
     */
    List<TagEntity> findAllByUser_Id(String userId, Sort sort);

    /**
     * Find tags by User ID
     *
     * @param userId User ID
     * @return List<TagEntity>
     */
    List<TagEntity> findAllByUser_Id(String userId);

    /**
     * Find tag by User ID and tag name
     *
     * @param userId User ID
     * @param name   tag name
     * @return Optional<TagEntity>
     */
    Optional<TagEntity> findByUser_IdAndName(String userId, String name);

    /**
     * Delete tags by User ID
     *
     * @param userId User ID
     */
    void deleteAllByUser_Id(String userId);

    /**
     * Check if tag exists by User ID and name
     *
     * @param userId User ID
     * @param name   tag name
     * @return true if exists, false otherwise
     */
    boolean existsByUser_IdAndName(String userId, String name);
}
