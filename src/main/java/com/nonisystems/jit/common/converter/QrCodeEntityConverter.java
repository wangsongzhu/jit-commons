package com.nonisystems.jit.common.converter;

import com.nonisystems.jit.common.dto.QrCode;
import com.nonisystems.jit.domain.entity.QrCodeEntity;
import com.nonisystems.jit.domain.entity.UrlEntity;
import com.nonisystems.jit.domain.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QrCodeEntityConverter {

    public QrCode convert(QrCodeEntity qrCodeEntity) {
        QrCode qrCode = new QrCode();
        BeanUtils.copyProperties(qrCodeEntity, qrCode);

        UserEntity userEntity = qrCodeEntity.getUser();
        if (userEntity != null) {
            qrCode.setUserId(userEntity.getId());
        }

        UrlEntity urlEntity = qrCodeEntity.getUrl();
        if (urlEntity != null) {
            qrCode.setUrlId(urlEntity.getId());
        }

        return qrCode;
    }
}
