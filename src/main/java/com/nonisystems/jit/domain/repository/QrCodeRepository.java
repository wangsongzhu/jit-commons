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
     * Find QR Code by id and user id
     *
     * @param id     url id
     * @param userId user id
     * @return Optional<QrCodeEntity>
     */
    Optional<QrCodeEntity> findByIdAndUser_Id(String id, String userId);

    /**
     * Find QR Code by userId (for pagination)
     *
     * @param userId   userId
     * @param pageable pageable
     * @return Page<QrCodeEntity>
     */
    Page<QrCodeEntity> findAllByUser_Id(String userId, Pageable pageable);

    /**
     * Find QR Codes by User ID (for deleting)
     *
     * @param userId User ID
     * @return List<QrCodeEntity>
     */
    List<QrCodeEntity> findAllByUser_Id(String userId);

    /**
     * Find QR Code by Url ID
     *
     * @param urlId Url ID
     * @param userId User ID
     * @return Optional<QrCodeEntity>
     */
    Optional<QrCodeEntity> findByUrl_IdAndUser_Id(String urlId, String userId);

    /**
     * Delete QR Codes by User ID
     *
     * @param userId User ID
     */
    void deleteAllByUser_Id(String userId);

    /**
     * Delete QR Code by Url ID
     *
     * @param urlId Url ID
     * @param userId User ID
     */
    void deleteByUrl_IdAndUser_Id(String urlId, String userId);
}
