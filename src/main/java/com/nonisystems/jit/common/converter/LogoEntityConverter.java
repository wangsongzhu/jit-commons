package com.nonisystems.jit.common.converter;

import com.nonisystems.jit.common.dto.Logo;
import com.nonisystems.jit.domain.entity.LogoEntity;
import com.nonisystems.jit.domain.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class LogoEntityConverter {

    public Logo convert(LogoEntity logoEntity) {
        Logo logo = new Logo();
        BeanUtils.copyProperties(logoEntity, logo);

        UserEntity userEntity = logoEntity.getUser();
        if (userEntity != null) {
            logo.setUserId(userEntity.getId());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        if (logoEntity.getCreated() != null) {
            logo.setCreated(logoEntity.getCreated().toLocalDateTime().format(formatter));
            log.debug("created: {}", logo.getCreated());
        }
        if (logoEntity.getModified() != null) {
            logo.setModified(logoEntity.getModified().toLocalDateTime().format(formatter));
            log.debug("modified: {}", logo.getModified());
        }

        return logo;
    }
}
