package com.nonisystems.jit.service.props;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
@RefreshScope
public class UserSubjectProperties {

    @Value("${jit.user-sub.prefix}")
    private String prefix;
    @Value("${jit.user-sub.ttl}")
    private String ttl;
}
