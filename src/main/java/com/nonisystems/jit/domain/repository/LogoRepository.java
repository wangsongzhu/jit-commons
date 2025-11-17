package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.LogoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

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
     * Find logos by User ID (for deleting)
     *
     * @param userId User ID
     * @return List<LogoEntity>
     */
    List<LogoEntity> findAllByUserId(String userId);

    /**
     * Delete logos by User ID
     *
     * @param userId User ID
     */
    void deleteAllByUserId(String userId);
}
