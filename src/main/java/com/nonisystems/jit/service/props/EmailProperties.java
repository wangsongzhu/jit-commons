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
public class EmailProperties {

    @Value("${spring.mail.username}")
    private String username;

//    @Value("${mail.username}")
//    private String username;
//
//    @Value("${mail.password}")
//    private String password;
//
//    @Value("${mail.smtp.auth}")
//    private String auth;
//
//    @Value("${mail.smtp.starttls.enable}")
//    private String starttlsEnable;
//
//    @Value("${mail.smtp.starttls.required}")
//    private String starttlsRequired;
//
//    @Value("${mail.smtp.host}")
//    private String host;
//
//    @Value("${mail.smtp.port}")
//    private String port;

}
