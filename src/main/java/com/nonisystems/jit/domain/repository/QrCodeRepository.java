package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.QrCodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface QrCodeRepository extends JpaRepository<QrCodeEntity, String>, JpaSpecificationExecutor<QrCodeEntity> {

    /**
     * Find QR Code by userId (for pagination)
     *
     * @param userId   userId
     * @param pageable pageable
     * @return Page<QrCodeEntity>
     */
    Page<QrCodeEntity> findAllByUserId(String userId, Pageable pageable);

    /**
     * Find QR Codes by User ID (for deleting)
     *
     * @param userId User ID
     * @return List<QrCodeEntity>
     */
    List<QrCodeEntity> findAllByUserId(String userId);

    /**
     * Find QR Code by Url ID
     *
     * @param urlId Url ID
     * @return Optional<QrCodeEntity>
     */
    Optional<QrCodeEntity> findByUrlId(String urlId);

    /**
     * Delete QR Codes by User ID
     *
     * @param userId User ID
     */
    void deleteAllByUserId(String userId);

    /**
     * Delete QR Code by Url ID
     *
     * @param urlId Url ID
     */
    void deleteByUrlId(String urlId);
}
