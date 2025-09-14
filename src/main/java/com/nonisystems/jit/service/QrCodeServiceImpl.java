package com.nonisystems.jit.service;

import com.nonisystems.jit.common.converter.QrCodeEntityConverter;
import com.nonisystems.jit.common.dto.QrCode;
import com.nonisystems.jit.common.exception.GeneralException;
import com.nonisystems.jit.domain.entity.QrCodeEntity;
import com.nonisystems.jit.domain.entity.UrlEntity;
import com.nonisystems.jit.domain.entity.UserEntity;
import com.nonisystems.jit.domain.repository.QrCodeRepository;
import com.nonisystems.jit.domain.repository.UrlRepository;
import com.nonisystems.jit.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Validated
public class QrCodeServiceImpl implements QrCodeService {

    private final QrCodeRepository qrCodeRepository;
    private final UserRepository userRepository;
    private final UrlRepository urlRepository;

    private final QrCodeEntityConverter qrCodeEntityConverter;

    public QrCodeServiceImpl(QrCodeRepository qrCodeRepository, UserRepository userRepository, UrlRepository urlRepository, QrCodeEntityConverter qrCodeEntityConverter) {
        this.qrCodeRepository = qrCodeRepository;
        this.userRepository = userRepository;
        this.urlRepository = urlRepository;
        this.qrCodeEntityConverter = qrCodeEntityConverter;
    }

    /**
     * Search QrCode list by user Id
     *
     * @param userId user id
     * @return List of QrCode
     */
    @Override
    public List<QrCode> findByUserId(String userId) throws GeneralException {
        if (StringUtils.isBlank(userId)) {
            throw new GeneralException(400, "validation.user_id.required");
        }
        List<QrCodeEntity> qrCodeEntities = qrCodeRepository.findByUserId(userId);
        return qrCodeEntities.stream().map(qrCodeEntityConverter::convert).toList();
    }

    /**
     * Search QrCode list by urlId
     *
     * @param urlId urlId
     * @return List of QrCode
     */
    @Override
    public List<QrCode> findByUrlId(String urlId) throws GeneralException {
        if (StringUtils.isBlank(urlId)) {
            throw new GeneralException(400, "validation.url_id.required");
        }
        List<QrCodeEntity> qrCodeEntities = qrCodeRepository.findByUrlId(urlId);
        return qrCodeEntities.stream().map(qrCodeEntityConverter::convert).toList();
    }

    /**
     * Search QrCode by id
     *
     * @param id qr code id
     * @return QrCode
     */
    @Override
    public QrCode findById(Long id) throws GeneralException {
        if (id == null) {
            throw new GeneralException(400, "validation.qr_code_id.required");
        }
        Optional<QrCodeEntity> qrCodeOptional = qrCodeRepository.findById(id);
        QrCodeEntity qrCodeEntity = qrCodeOptional.orElseThrow(() ->
                new GeneralException(404, "validation.qr_code.not_found"));
        if (log.isDebugEnabled()) {
            log.debug("Found qrCodeEntity {}", qrCodeEntity);
        }
        // Return qrCode
        QrCode qrCode = this.qrCodeEntityConverter.convert(qrCodeEntity);
        if (log.isDebugEnabled()) {
            log.debug("Found qrCode {}", qrCode);
        }
        return qrCode;
    }

    /**
     * Create QrCode
     *
     * @param qrCode qrCode
     * @return qrCode saved
     */
    @Transactional
    @Override
    public QrCode createQrCode(QrCode qrCode) {
        if (log.isDebugEnabled()) {
            log.debug("qrCode: {}", qrCode);
        }
        QrCodeEntity qrCodeEntity = new QrCodeEntity();
        BeanUtils.copyProperties(qrCode, qrCodeEntity);

        Optional<UserEntity> userOptional = userRepository.findById(qrCode.getUserId());
        UserEntity userEntity = userOptional.orElseThrow(() ->
                new GeneralException(404, "validation.user.not_found"));
        if (log.isDebugEnabled()) {
            log.debug("Found userEntity {}", userEntity);
        }
        qrCodeEntity.setUser(userEntity);

        Optional<UrlEntity> urlOptional = this.urlRepository.findById(qrCode.getUrlId());
        UrlEntity urlEntity = urlOptional.orElseThrow(() ->
                new GeneralException(404, "validation.url.not_found"));
        if (log.isDebugEnabled()) {
            log.debug("Found urlEntity {}", urlEntity);
        }

        QrCodeEntity savedQrCodeEntity = this.qrCodeRepository.save(qrCodeEntity);
        QrCode savedQrCode = this.qrCodeEntityConverter.convert(savedQrCodeEntity);
        if (log.isDebugEnabled()) {
            log.debug("savedQrCode: {}", savedQrCode);
        }
        return savedQrCode;
    }

    /**
     * Delete all QrCodeEntity by urlId
     * @param urlId urlId
     */
    @Transactional
    @Override
    public void deleteByUrlId(String urlId) {
        if (!StringUtils.isBlank(urlId)) {
            qrCodeRepository.deleteByUrlId(urlId);
        }
    }

    /**
     * Delete all QrCodeEntity by user id
     * @param userId user id
     */
    @Transactional
    @Override
    public void deleteByUserId(String userId) {
        if (!StringUtils.isBlank(userId)) {
            qrCodeRepository.deleteByUserId(userId);
        }
    }
}
