package com.nonisystems.jit.common.converter;

import com.nonisystems.jit.common.dto.Domain;
import com.nonisystems.jit.domain.entity.DomainEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DomainEntityConverter {

    public Domain convert(DomainEntity domainEntity) {
        Domain domain = new Domain();
        BeanUtils.copyProperties(domainEntity, domain);
        return domain;
    }
}
