package com.nonisystems.jit.common.converter;

import com.nonisystems.jit.common.dto.QrCode;
import com.nonisystems.jit.common.dto.Tag;
import com.nonisystems.jit.domain.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            qrCode.setFullShortUrl(urlEntity.getFullShortUrl());
            qrCode.setData(urlEntity.getOriginalUrl());
        }

        LogoEntity logoEntity = qrCodeEntity.getLogo();
        if (logoEntity != null) {
            qrCode.setLogoId(logoEntity.getId());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        if (qrCodeEntity.getCreated() != null) {
            qrCode.setCreated(qrCodeEntity.getCreated().toLocalDateTime().format(formatter));
            log.debug("created: {}", qrCode.getCreated());
        }
        if (qrCodeEntity.getModified() != null) {
            qrCode.setModified(qrCodeEntity.getModified().toLocalDateTime().format(formatter));
            log.debug("modified: {}", qrCode.getModified());
        }

        List<Tag> tags = new ArrayList<>();
        log.debug("before getting qrCodeTagEntities size");
        if (qrCodeEntity.getQrCodeTags() != null && !qrCodeEntity.getQrCodeTags().isEmpty()) {
            Set<QrCodeTagEntity> qrCodeTagEntities = qrCodeEntity.getQrCodeTags();
            log.debug("qrCodeTagEntities size: {}", qrCodeTagEntities.size());
            for (QrCodeTagEntity qrCodeTagEntity : qrCodeTagEntities) {
                log.debug("qrCodeTagEntity id: {}", qrCodeTagEntity.getId());
                TagEntity tagEntity = qrCodeTagEntity.getTag();
                log.debug("tagEntity id: {}", tagEntity.getId());
                Tag tag = new Tag();
                tag.setId(tagEntity.getId());
                tag.setName(tagEntity.getName());
                tags.add(tag);
            }
        }
        qrCode.setTags(tags);

        return qrCode;
    }
}
