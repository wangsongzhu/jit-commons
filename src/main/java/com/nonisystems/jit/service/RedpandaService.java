package com.nonisystems.jit.service;

public interface RedpandaService {

    public void sendMessage(String topic, String key, String message);
    public void sendSignupEmailNotification(String email);
    public void sendForgotPasswordEmailNotification(String email);
    public void sendResetPasswordCompletedEmailNotification(String email);
}
