package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.QrCodeEntity;
import com.nonisystems.jit.domain.entity.UrlEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface QrCodeRepository extends JpaRepository<QrCodeEntity, String>, JpaSpecificationExecutor<QrCodeEntity> {

    /**
     * Find QR Code by Url ID
     *
     * @param urlId Url ID
     * @return Optional<QrCodeEntity>
     */
    Optional<QrCodeEntity> findByUrlId(String urlId);

    /**
     * Find QR Codes by User ID
     *
     * @param userId User ID
     * @return List<QrCodeEntity>
     */
    Page<UrlEntity> findByUserId(String userId, Pageable pageable);
    //List<QrCodeEntity> findByUserId(String userId);

    /**
     * Delete QR Code by Url ID
     *
     * @param urlId Url ID
     */
    void deleteByUrlId(String urlId);

    /**
     * Delete QR Codes by User ID
     *
     * @param userId User ID
     */
    void deleteAllByUserId(String userId);
}
