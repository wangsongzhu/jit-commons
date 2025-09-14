package com.nonisystems.jit.service;

import com.nonisystems.jit.common.dto.ClickRecord;
import com.nonisystems.jit.common.exception.GeneralException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClickRecordService {

    List<ClickRecord> findByUrlId(String urlId) throws GeneralException;

    Page<ClickRecord> getClickRecordsByUrlId(String urlId, Pageable pageable) throws GeneralException;

    ClickRecord createClickRecord(ClickRecord clickRecord);

    void deleteByUrlId(String urlId);
}
