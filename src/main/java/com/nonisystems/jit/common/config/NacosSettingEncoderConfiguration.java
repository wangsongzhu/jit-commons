package com.nonisystems.jit.common.config;

import com.nonisystems.jit.common.config.util.AESEncoder;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NacosSettingEncoderConfiguration {

    @Bean
    public AESEncoder aesEncoder() {
        return new AESEncoder();
    }

    @Bean("jasyptStringEncryptor")
    public StandardPBEStringEncryptor jasyptStringEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("ThisIsMySecretKey");
        encryptor.setAlgorithm("PBEWithSHA1AndRC4_128");
        encryptor.setKeyObtentionIterations(10000);
        return encryptor;
    }
}