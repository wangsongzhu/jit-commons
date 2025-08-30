package com.nonisystems.jit.service;

import com.nonisystems.jit.service.props.RedpandaProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RefreshScope
public class RedpandaServiceImpl implements RedpandaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RedpandaProperties redpandaProperties;

    public RedpandaServiceImpl(KafkaTemplate<String, String> kafkaTemplate, RedpandaProperties redpandaProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.redpandaProperties = redpandaProperties;
    }

    /**
     * Send message to Redpanda topic
     *
     * @param topic   topic
     * @param key     key
     * @param message detailed message
     */
    public void sendMessage(String topic, String key, String message) {
        log.debug("Send message: Topic = {}, Message = {}", topic, message);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, message);
        future.thenAccept(result -> log.debug("Message {} has been sent to topic {}", message, topic)).exceptionally(ex -> {
            log.error("Error happened while sending message {} to topic {}, error: {}", message, topic, ex);
            return null;
        });
    }

    /**
     * Send message to Signup Email Notification topic
     *
     * @param email user email
     */
    public void sendSignupEmailNotification(String email) {
        String message = String.format("{\"email\":\"%s\"}", email);
        log.debug("Send message to Signup Email Notification topic, key = {}, message = {}", email, message);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(this.redpandaProperties.getSignupEmailNotificationsTopic(), email, message);
        future.thenAccept(result -> log.debug("Message has been sent to topic.")).exceptionally(ex -> {
            log.error("Error happened while sending message", ex);
            return null;
        });
    }

    /**
     * Send message to Forget Password Email Notification topic
     *
     * @param email user email
     */
    public void sendForgotPasswordEmailNotification(String email) {
        String message = String.format("{\"email\":\"%s\"}", email);
        log.debug("Send message to Forget Password Email Notification topic, key = {}, message = {}", email, message);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(this.redpandaProperties.getForgotPasswordEmailNotificationsTopic(), email, message);
        future.thenAccept(result -> log.debug("Message has been sent to topic.")).exceptionally(ex -> {
            log.error("Error happened while sending message", ex);
            return null;
        });
    }

    /**
     * Send message to Reset Password Completed Email Notification topic
     *
     * @param email user email
     */
    public void sendResetPasswordCompletedEmailNotification(String email) {
        String message = String.format("{\"email\":\"%s\"}", email);
        log.debug("Send message to Reset Password Completed Email Notification topic, key = {}, message = {}", email, message);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(this.redpandaProperties.getResetPasswordCompletedEmailNotificationsTopic(), email, message);
        future.thenAccept(result -> log.debug("Message has been sent to topic.")).exceptionally(ex -> {
            log.error("Error happened while sending message", ex);
            return null;
        });
    }
}
