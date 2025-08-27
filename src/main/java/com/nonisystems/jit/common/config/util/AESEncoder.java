package com.nonisystems.jit.common.config.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class AESEncoder {

    // TODO May need to put this key in environment variable
    private static final String SECRET_KEY_STRING = "ThisIsMySecretKe"; // 16 bytes
    private static final SecretKey SECRET_KEY;

    static {
        SECRET_KEY = new SecretKeySpec(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8), "AES");
    }

    /**
     * Encrypt
     *
     * @param plaintext target plain text
     * @return encrypted text
     */
    public String encrypt(String plaintext) {
        if (log.isDebugEnabled()) {
            log.debug("plaintext: {}", plaintext);
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
     * Decrypt
     *
     * @param ciphertext encrypted text
     * @return plain text
     */
    public String decrypt(String ciphertext) {
        if (log.isDebugEnabled()) {
            log.debug("ciphertext: {}", ciphertext);
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
