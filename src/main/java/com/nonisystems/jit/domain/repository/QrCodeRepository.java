package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.QrCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QrCodeRepository extends JpaRepository<QrCodeEntity, Long> {

    List<QrCodeEntity> findByUrlId(String urlId);

    List<QrCodeEntity> findByUserId(String userId);

    void deleteByUrlId(String urlId);

    void deleteByUserId(String userId);
}
