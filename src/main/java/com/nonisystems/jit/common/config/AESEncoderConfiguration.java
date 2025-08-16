package com.nonisystems.jit.common.config;

import com.nonisystems.jit.common.config.util.AESEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AESEncoderConfiguration {

    @Bean
    public AESEncoder aesEncoder() {
        return new AESEncoder();
    }
}