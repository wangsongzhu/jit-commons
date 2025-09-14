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
public class RedpandaProperties {

    /**
     * signup-email-notifications
     */
    @Value("${redpanda.topics.signup-email-notifications}")
    private String signupEmailNotificationsTopic;

    /**
     * forgot-password-email-notifications
     */
    @Value("${redpanda.topics.forgot-password-email-notifications}")
    private String forgotPasswordEmailNotificationsTopic;

    /**
     * reset-password-completed-email-notifications
     */
    @Value("${redpanda.topics.reset-password-completed-email-notifications}")
    private String resetPasswordCompletedEmailNotificationsTopic;
}
