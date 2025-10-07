package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DomainRepository extends JpaRepository<DomainEntity, Long> {

    @Query("SELECT d FROM DomainEntity d WHERE d.userId = :userId OR d.id = 1 ORDER BY d.isActive DESC, d.id DESC")
    Optional<List<DomainEntity>> findByUserIdWithDefault(@Param("userId") String userId);

    Optional<List<DomainEntity>> findByUserId(String userId);
    Optional<DomainEntity> findByUserIdAndDomainUrl(String userId, String domainUrl);
    boolean existsByDomainUrlAndUserId(String domainUrl, String userId);
    boolean existsByDomainUrl(String domainUrl);
    void deleteByUserId(String userId);
}
