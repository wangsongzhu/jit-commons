package com.nonisystems.jit.service;

import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface EmailService {

//    public void sendEmail(String to, String subject, String body);
    public void sendEmail(String to, String subject, String templateName, Context context, boolean isHtml) throws MessagingException;
}
