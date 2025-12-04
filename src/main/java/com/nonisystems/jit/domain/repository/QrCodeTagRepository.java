package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.QrCodeTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface QrCodeTagRepository extends JpaRepository<QrCodeTagEntity, String> {

    @Modifying
    @Transactional
    @Query("DELETE FROM QrCodeTagEntity qt WHERE qt.qrCode.id = :qrCodeId")
    void deleteAllByQrCodeId(@Param("qrCodeId") String qrCodeId);

    Optional<List<QrCodeTagEntity>> findAllByQrCodeId(String qrCodeId);

    Optional<List<QrCodeTagEntity>> findAllByTagId(String tagId);
}
