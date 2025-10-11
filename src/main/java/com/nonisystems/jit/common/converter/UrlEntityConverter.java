package com.nonisystems.jit.common.converter;

import com.nonisystems.jit.common.dto.QrCode;
import com.nonisystems.jit.common.dto.Tag;
import com.nonisystems.jit.common.dto.Url;
import com.nonisystems.jit.domain.entity.QrCodeEntity;
import com.nonisystems.jit.domain.entity.TagEntity;
import com.nonisystems.jit.domain.entity.UrlEntity;
import com.nonisystems.jit.domain.entity.UrlTagEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        if (urlEntity == null) {
            return null;
        }

        Url url = new Url();
        log.debug("copy properties from url entity to url: {}", urlEntity.getId());
        BeanUtils.copyProperties(urlEntity, url);
        log.debug("complete copying properties from url entity to url");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        if (urlEntity.getExpirationDate() != null) {
            url.setExpirationDate(urlEntity.getExpirationDate().format(formatter));
            log.debug("expirationDate: {}", url.getExpirationDate());
        }
        if (urlEntity.getCreated() != null) {
            url.setCreated(urlEntity.getCreated().toLocalDateTime().format(formatter));
            log.debug("created: {}", url.getCreated());
        }
        if (urlEntity.getModified() != null) {
            url.setModified(urlEntity.getModified().toLocalDateTime().format(formatter));
            log.debug("modified: {}", url.getModified());
        }

        List<Tag> tags = new ArrayList<>();
        log.debug("before getting urlTagEntities size");
        if (urlEntity.getUrlTags() != null && !urlEntity.getUrlTags().isEmpty()) {
            Set<UrlTagEntity> urlTagEntities = urlEntity.getUrlTags();
            log.debug("urlTagEntities size: {}", urlTagEntities.size());
            for (UrlTagEntity urlTagEntity : urlTagEntities) {
                log.debug("urlTagEntity id: {}", urlTagEntity.getId());
                TagEntity tagEntity = urlTagEntity.getTag();
                log.debug("tagEntity id: {}", tagEntity.getId());
                Tag tag = new Tag();
                tag.setId(tagEntity.getId());
                tag.setName(tagEntity.getName());
                tags.add(tag);
            }
        }
        url.setTags(tags);

        List<QrCode> qrCodes = new ArrayList<>();
        List<QrCodeEntity> qrCodeEntities = urlEntity.getQrCodes();
        if (qrCodeEntities != null && !qrCodeEntities.isEmpty()) {
            for (QrCodeEntity qrCodeEntity : qrCodeEntities) {
                log.debug("qrCodeEntity id: {}", qrCodeEntity.getId());
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
