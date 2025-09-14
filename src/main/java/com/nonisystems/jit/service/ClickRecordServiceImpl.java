package com.nonisystems.jit.service;

import com.nonisystems.jit.common.converter.ClickRecordEntityConverter;
import com.nonisystems.jit.common.dto.ClickRecord;
import com.nonisystems.jit.common.exception.GeneralException;
import com.nonisystems.jit.domain.entity.ClickRecordEntity;
import com.nonisystems.jit.domain.entity.UrlEntity;
import com.nonisystems.jit.domain.repository.ClickRecordRepository;
import com.nonisystems.jit.domain.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Validated
public class ClickRecordServiceImpl implements ClickRecordService {

    private final ClickRecordRepository clickRecordRepository;
    private final UrlRepository urlRepository;
    private final ClickRecordEntityConverter clickRecordEntityConverter;

    public ClickRecordServiceImpl(ClickRecordRepository clickRecordRepository, UrlRepository urlRepository, ClickRecordEntityConverter clickRecordEntityConverter) {
        this.clickRecordRepository = clickRecordRepository;
        this.urlRepository = urlRepository;
        this.clickRecordEntityConverter = clickRecordEntityConverter;
    }

    /**
     * Search ClickRecord list by urlId
     *
     * @param urlId urlId
     * @return List of ClickRecord
     */
    @Override
    public List<ClickRecord> findByUrlId(String urlId) throws GeneralException {
        if (StringUtils.isBlank(urlId)) {
            throw new GeneralException(400, "validation.url_id.required");
        }
        List<ClickRecordEntity> clickRecordEntities = clickRecordRepository.findByUrlId(urlId);
        return clickRecordEntities.stream().map(clickRecordEntityConverter::convert).toList();
    }

    /**
     * Get pageable ClickRecord list by urlId
     *
     * @param urlId URL ID
     * @param pageable Paging information
     * @return ClickRecord list with paging information
     */
    @Override
    public Page<ClickRecord> getClickRecordsByUrlId(String urlId, Pageable pageable) throws GeneralException {
        if (StringUtils.isBlank(urlId)) {
            throw new GeneralException(400, "validation.url_id.required");
        }

        Page<ClickRecordEntity> clickRecordEntities = clickRecordRepository.findByUrlId(urlId, pageable);
        return clickRecordEntities.map(clickRecordEntityConverter::convert);
    }

    /**
     * Create ClickRecord
     *
     * @param clickRecord ClickRecord
     * @return ClickRecord saved
     */
    @Override
    public ClickRecord createClickRecord(ClickRecord clickRecord) {
        if (log.isDebugEnabled()) {
            log.debug("clickRecord: {}", clickRecord);
        }
        ClickRecordEntity clickRecordEntity = new ClickRecordEntity();
        BeanUtils.copyProperties(clickRecord, clickRecordEntity);

        Optional<UrlEntity> urlOptional = urlRepository.findById(clickRecord.getUrlId());
        UrlEntity urlEntity = urlOptional.orElseThrow(() ->
                new GeneralException(404, "validation.url.not_found"));
        clickRecordEntity.setUrl(urlEntity);
        /*
        clickRecordEntity.setClickTime(clickRecord.getClickTime());
        clickRecordEntity.setIp(clickRecord.getIp());
        clickRecordEntity.setBrowser(clickRecord.getBrowser());
        clickRecordEntity.setBrowserType(clickRecord.getBrowserType());
        clickRecordEntity.setBrowserMajorVersion(clickRecord.getBrowserMajorVersion());
        clickRecordEntity.setDeviceType(clickRecord.getDeviceType());
        clickRecordEntity.setPlatform(clickRecord.getPlatform());
        clickRecordEntity.setPlatformVersion(clickRecord.getPlatformVersion());
        clickRecordEntity.setPlatformMaker(clickRecord.getPlatformMaker());
        clickRecordEntity.setRenderingEngineMaker(clickRecord.getRenderingEngineMaker());
        clickRecordEntity.setLanguage(clickRecord.getLanguage());
        clickRecordEntity.setGeolocation(clickRecord.getGeolocation());
        */
        ClickRecordEntity savedClickRecordEntity = this.clickRecordRepository.save(clickRecordEntity);
        ClickRecord savedClickRecord = this.clickRecordEntityConverter.convert(savedClickRecordEntity);
        if (log.isDebugEnabled()) {
            log.debug("savedClickRecord: {}", savedClickRecord);
        }
        return savedClickRecord;
    }

    /**
     * Delete all ClickRecordEntity by urlId
     * @param urlId urlId
     */
    @Transactional
    @Override
    public void deleteByUrlId(String urlId) {
        if (!StringUtils.isBlank(urlId)) {
            clickRecordRepository.deleteByUrlId(urlId);
        }
    }

}
