package com.nonisystems.jit.common.converter;

import com.nonisystems.jit.common.dto.QrCode;
import com.nonisystems.jit.common.dto.Tag;
import com.nonisystems.jit.common.dto.Url;
import com.nonisystems.jit.domain.entity.QrCodeEntity;
import com.nonisystems.jit.domain.entity.TagEntity;
import com.nonisystems.jit.domain.entity.UrlEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class UrlEntityConverter {

    /**
     * Convert UrlEntity to Url
     *
     * @param urlEntity UrlEntity
     * @return Url
     */
    public Url convert(UrlEntity urlEntity) {

        Url url = new Url();
        BeanUtils.copyProperties(urlEntity, url);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        if (urlEntity.getExpirationDate() != null) {
            url.setExpirationDate(urlEntity.getExpirationDate().format(formatter));
        }
        if (urlEntity.getCreated() != null) {
            url.setCreated(urlEntity.getCreated().toLocalDateTime().format(formatter));
        }
        if (urlEntity.getModified() != null) {
            url.setModified(urlEntity.getModified().toLocalDateTime().format(formatter));
        }

        List<Tag> tags = new ArrayList<>();
        Set<TagEntity> tagEntities = urlEntity.getTags();
        if (tagEntities != null && !tagEntities.isEmpty()) {
            for (TagEntity tagEntity : tagEntities) {
                Tag tag = new Tag();
                tag.setId(tagEntity.getId());
                tag.setName(tagEntity.getName());
                tags.add(tag);
            }
            url.setTags(tags);
        }

        List<QrCode> qrCodes = new ArrayList<>();
        List<QrCodeEntity> qrCodeEntities = urlEntity.getQrCodes();
        if (qrCodeEntities != null && !qrCodeEntities.isEmpty()) {
            for (QrCodeEntity qrCodeEntity : qrCodeEntities) {
                QrCode qrCode = new QrCode();
                BeanUtils.copyProperties(qrCodeEntity, qrCode);
                if (qrCodeEntity.getCreated() != null) {
                    qrCode.setCreated(qrCodeEntity.getCreated().toLocalDateTime().format(formatter));
                }
                if (qrCodeEntity.getModified() != null) {
                    qrCode.setModified(qrCodeEntity.getModified().toLocalDateTime().format(formatter));
                }
                qrCodes.add(qrCode);
            }
            url.setQrCodes(qrCodes);
        }

        return url;
    }
}
