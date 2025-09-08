package com.nonisystems.jit.domain.repository;

import com.nonisystems.jit.domain.entity.QrCodeEntity;
import org.springframework.data.repository.CrudRepository;

public interface QrCodeRepository extends CrudRepository<QrCodeEntity, Long> {
    QrCodeEntity getQrCodeEntitiesById(Long id);
}
