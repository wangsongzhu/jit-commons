package com.nonisystems.jit.common.converter;

import com.nonisystems.jit.common.dto.Tag;
import com.nonisystems.jit.domain.entity.TagEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TagEntityConverter {

    public Tag convert(TagEntity tagEntity) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagEntity, tag);
        return tag;
    }
}
