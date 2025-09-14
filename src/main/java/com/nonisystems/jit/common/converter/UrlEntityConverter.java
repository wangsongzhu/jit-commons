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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class UrlEntityConverter {

    public Url convert(UrlEntity urlEntity) {
        Url url = new Url();
        BeanUtils.copyProperties(urlEntity, url);

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
                qrCodes.add(qrCode);
            }
            url.setQrCodes(qrCodes);
        }

        return url;
    }
}
