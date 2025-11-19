package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.LogoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface LogoRepository extends JpaRepository<LogoEntity, String>, JpaSpecificationExecutor<LogoEntity> {

    /**
     * Find logos by userId (for pagination)
     *
     * @param userId   userId
     * @param pageable pageable
     * @return Page<LogoEntity>
     */
    Page<LogoEntity> findAllByUserId(String userId, Pageable pageable);

    /**
     * Find logos by User ID (for sorting)
     *
     * @param userId User ID
     * @param sort   Sort
     * @return List<LogoEntity>
     */
    List<LogoEntity> findAllByUserId(String userId, Sort sort);

    /**
     * Find logos by User ID (for deleting)
     *
     * @param userId User ID
     * @return List<LogoEntity>
     */
    List<LogoEntity> findAllByUserId(String userId);

    /**
     * Find logo by User ID and URL
     *
     * @param userId User ID
     * @param url    url
     * @return Optional<LogoEntity>
     */
    Optional<LogoEntity> findByUserIdAndUrl(String userId, String url);

    /**
     * Delete logos by User ID
     *
     * @param userId User ID
     */
    void deleteAllByUserId(String userId);
}
