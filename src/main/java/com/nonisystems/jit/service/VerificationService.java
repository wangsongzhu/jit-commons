package com.nonisystems.jit.service;

public interface VerificationService {

    public String generateVerificationCode(String email);
    public boolean isValidCode(String code);
}
