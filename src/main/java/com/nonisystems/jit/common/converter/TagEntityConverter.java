package com.nonisystems.jit.common.converter;

import com.nonisystems.jit.common.dto.Tag;
import com.nonisystems.jit.domain.entity.TagEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class TagEntityConverter {

    public Tag convert(TagEntity tagEntity) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagEntity, tag);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        if (tagEntity.getCreated() != null) {
            tag.setCreated(tagEntity.getCreated().toLocalDateTime().format(formatter));
            log.debug("created: {}", tag.getCreated());
        }
        if (tagEntity.getModified() != null) {
            tag.setModified(tagEntity.getModified().toLocalDateTime().format(formatter));
            log.debug("modified: {}", tag.getModified());
        }

        return tag;
    }
}
