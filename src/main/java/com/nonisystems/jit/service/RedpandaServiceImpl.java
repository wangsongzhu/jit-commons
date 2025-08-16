package com.nonisystems.jit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RefreshScope
public class RedpandaServiceImpl implements RedpandaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${redpanda.topics.signup-email-notifications}")
    private String topic;

    public RedpandaServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 发送邮件通知消息到 Redpanda
     *
     * @param email 邮件地址
     */
    public void sendEmailNotification(String email) {
        String message = String.format("{\"email\":\"%s\"}", email);
        log.debug("Send message to Redpanda: Topic={}, Message={}", this.topic, message);
        kafkaTemplate.send(this.topic, email, message);
    }
}
