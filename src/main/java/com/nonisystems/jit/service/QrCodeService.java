package com.nonisystems.jit.service;

import com.nonisystems.jit.common.dto.QrCode;
import com.nonisystems.jit.common.exception.GeneralException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QrCodeService {
    List<QrCode> findByUserId(String userId) throws GeneralException;

    List<QrCode> findByUrlId(String urlId) throws GeneralException;

    QrCode findById(Long id) throws GeneralException;

    QrCode createQrCode(QrCode qrCode);

    void deleteByUrlId(String urlId);

    void deleteByUserId(String userId);
}
