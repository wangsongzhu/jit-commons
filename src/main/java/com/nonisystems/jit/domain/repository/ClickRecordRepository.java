package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.ClickRecordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClickRecordRepository extends JpaRepository<ClickRecordEntity, Long> {

    /**
     * Search ClickRecordEntity list by urlId
     *
     * @param urlId urlId
     * @return List of ClickRecordEntity
     */
    List<ClickRecordEntity> findByUrlId(String urlId);

    /**
     * Search Pageable ClickRecordEntity list by urlId
     *
     * @param urlId    urlId
     * @param pageable pageable information
     * @return List of ClickRecordEntity
     */
    Page<ClickRecordEntity> findByUrlId(String urlId, Pageable pageable);

    /**
     * Delete all ClickRecordEntity by urlId
     * @param urlId urlId
     */
    void deleteByUrlId(String urlId);
}
