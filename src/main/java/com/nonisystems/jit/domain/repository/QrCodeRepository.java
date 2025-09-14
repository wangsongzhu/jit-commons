package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.QrCodeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QrCodeRepository extends CrudRepository<QrCodeEntity, Long> {

    List<QrCodeEntity> findByUrlId(String urlId);

    List<QrCodeEntity> findByUserId(String userId);

    void deleteByUrlId(String urlId);

    void deleteByUserId(String userId);
}
