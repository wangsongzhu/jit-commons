package com.nonisystems.jit.common.config.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class AESEncoder {

    private static final String SECRET_KEY_STRING = "ThisIsMySecretKe"; // 16 bytes
    private static final SecretKey SECRET_KEY;

    static {
        SECRET_KEY = new SecretKeySpec(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8), "AES");
    }

    /**
     * 对原文进行对称加密，用于配置项
     *
     * @param plaintext 原文
     * @return 加密后的密文，用户配置
     */
    public String encrypt(String plaintext) {
        if (log.isDebugEnabled()) {
            log.debug("plaintext: " + plaintext);
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            log.debug("encryptedText: {}", encryptedText);
            return encryptedText;
        } catch (Exception e) {
            log.error("Error happened while encrypting information", e);
            return null;
        }
    }

    /**
     * 对原文进行对称解密，用于配置项
     *
     * @param ciphertext 密文
     * @return 原文
     */
    public String decrypt(String ciphertext) {
        if (log.isDebugEnabled()) {
            log.debug("ciphertext: " + ciphertext);
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY);
            byte[] decodedBytes = Base64.getDecoder().decode(ciphertext);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);
            log.debug("decryptedText: {}", decryptedText);
            return decryptedText;
        } catch (Exception e) {
            log.error("Error happened while decrypting information", e);
            return null;
        }
    }

}
