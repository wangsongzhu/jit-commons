package com.nonisystems.jit.service;

import com.nonisystems.jit.common.config.util.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
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
     * 生成并保存验证码到 Redis，并返回验证码。
     *
     * @param email 用户的email
     * @return 生成的验证码
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
     * 验证用户提供的验证码是否有效。
     *
     * @param code 用户提供的验证码
     * @return 验证成功返回true，否则返回false
     */
    public boolean isValidCode(String code) {
        if (log.isDebugEnabled()) {
            log.debug("code: {}", code);
        }
        String key = keyPrefix + code;
        String email = redisTemplate.opsForValue().get(key);
        log.debug("email: {}", email);
        if (email != null) {
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }
}
