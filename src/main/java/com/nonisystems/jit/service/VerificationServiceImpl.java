package com.nonisystems.jit.service;

import com.nonisystems.jit.common.config.util.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RefreshScope
public class VerificationServiceImpl implements VerificationService {

    @Value("${jit.signup.verify.prefix}")
    private String keyPrefix;

    @Value("${jit.signup.verify.ttl}")
    private String ttl;

    private final StringRedisTemplate redisTemplate;
    private final UUIDGenerator uuidGenerator;

    public VerificationServiceImpl(StringRedisTemplate redisTemplate, UUIDGenerator uuidGenerator) {
        this.redisTemplate = redisTemplate;
        this.uuidGenerator = uuidGenerator;
    }

    /**
     * Generate verification code and save to Redis
     *
     * @param email email
     * @return verification code
     */
    public String generateVerificationCode(String email) {
        String verificationCode = this.uuidGenerator.generateUUID(email);
        log.debug("verificationCode: {}", verificationCode);
        long ttlInSeconds = TimeUnit.MINUTES.toSeconds(Long.parseLong(ttl));
        log.debug("ttlInSeconds: {}", ttlInSeconds);
        redisTemplate.opsForValue().setIfAbsent(keyPrefix + verificationCode, email, ttlInSeconds, TimeUnit.SECONDS);
        return verificationCode;
    }

    /**
     * Verify the verification code
     *
     * @param code verification code
     * @return email if valid otherwise null
     */
    public String verifyCode(String code) {
        if (log.isDebugEnabled()) {
            log.debug("code: {}", code);
        }
        String key = keyPrefix + code;
        String email = redisTemplate.opsForValue().get(key);
        log.debug("email: {}", email);
        if (StringUtils.isNotBlank(email)) {
            redisTemplate.delete(key);
        }
        return email;
    }
}
