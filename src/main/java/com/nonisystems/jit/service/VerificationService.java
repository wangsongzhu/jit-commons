package com.nonisystems.jit.service;

public interface VerificationService {

    public String generateVerificationCode(String email);
    public String verifyCode(String code);
}
