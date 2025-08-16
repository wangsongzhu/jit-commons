package com.nonisystems.jit.service;

import java.util.Properties;

import com.nonisystems.jit.common.config.util.AESEncoder;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RefreshScope
public class EmailServiceImpl implements EmailService {

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.smtp.auth}")
    private String auth;

    @Value("${mail.smtp.starttls.enable}")
    private String starttlsEnable;

    @Value("${mail.smtp.starttls.required}")
    private String starttlsRequired;

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.port}")
    private String port;

    private final AESEncoder aesEncoder;

    public EmailServiceImpl(AESEncoder aesEncoder) {
        this.aesEncoder = aesEncoder;
    }

    public void sendEmail(String to, String subject, String body) {

        final String fromAddress = this.aesEncoder.decrypt(this.username);
        final String key = this.aesEncoder.decrypt(this.password);

        Properties props = new Properties();
        props.put("mail.smtp.auth", this.auth);
        props.put("mail.smtp.starttls.enable", this.starttlsEnable);
        props.put("mail.smtp.starttls.required", this.starttlsRequired);
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port);

        try {
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromAddress, key);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            log.debug("The email sent successfully to {}", to);
        } catch (MessagingException e) {
            log.error("The email failed to send to {} with error {}", to, e.getMessage());
        }
    }

}
