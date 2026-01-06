package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.DomainEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DomainRepository extends JpaRepository<DomainEntity, String> {

    /**
     * Find domain by id and user id
     *
     * @param id     domain id
     * @param userId user id
     * @return Optional<DomainEntity>
     */
    Optional<DomainEntity> findByIdAndUserId(String id, String userId);

    /**
     * Get all the domains with default domain by userId
     *
     * @param userId user id
     * @return List<DomainEntity>
     */
    @Query("SELECT d FROM DomainEntity d WHERE d.userId = :userId OR d.id = '0' ORDER BY d.isActive DESC, d.id DESC")
    List<DomainEntity> findByUserIdWithDefault(@Param("userId") String userId, Sort sort);

    /**
     * Find all the domains by User ID (for sorting)
     *
     * @param userId User ID
     * @param sort   Sort
     * @return List<DomainEntity>
     */
    List<DomainEntity> findAllByUserId(String userId, Sort sort);

    /**
     * Get all the domains by userId
     *
     * @param userId user id
     * @return List<DomainEntity>
     */
    List<DomainEntity> findAllByUserId(String userId);

    /**
     * Get the domain by id and userId
     *
     * @param userId    user id
     * @param domainUrl domain url
     * @return Optional<DomainEntity>
     */
    Optional<DomainEntity> findByUserIdAndDomainUrl(String userId, String domainUrl);

    /**
     * Check if the domain exists by userId and domainUrl
     *
     * @param userId    user id
     * @param domainUrl domain url
     * @return boolean
     */
    boolean existsByUserIdAndDomainUrl(String userId, String domainUrl);

    /**
     * Check if the domain exists by domainUrl
     *
     * @param domainUrl domain url
     * @return boolean
     */
    boolean existsByDomainUrl(String domainUrl);

    /**
     * Delete all the domains by userId
     *
     * @param userId user id
     */
    void deleteAllByUserId(String userId);
}
