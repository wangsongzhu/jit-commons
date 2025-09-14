package com.nonisystems.jit.common.converter;

import com.nonisystems.jit.common.dto.ClickRecord;
import com.nonisystems.jit.domain.entity.ClickRecordEntity;
import com.nonisystems.jit.domain.entity.UrlEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClickRecordEntityConverter {

    public ClickRecord convert(ClickRecordEntity clickRecordEntity) {
        ClickRecord clickRecord = new ClickRecord();
        BeanUtils.copyProperties(clickRecordEntity, clickRecord);

        UrlEntity urlEntity = clickRecordEntity.getUrl();
        if (urlEntity != null) {
            clickRecord.setUrlId(urlEntity.getId());
        }

        return clickRecord;
    }
}
