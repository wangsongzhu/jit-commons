package com.nonisystems.jit.common.config.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptEncryptor {

    public static void main(String[] args) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("ThisIsMySecretKey");
        encryptor.setAlgorithm("PBEWithSHA1AndRC4_128");
        encryptor.setKeyObtentionIterations(10000);

        String email = encryptor.encrypt("scott007.wung@gmail.com");
        System.out.println("Encrypted email: " + email);
        String encryptedPassword = encryptor.encrypt("jtbxlpyefrddxlsf");
        System.out.println("Encrypted Password: " + encryptedPassword);

        String decryptedEmail = encryptor.decrypt(email);
        System.out.println("Decrypted email: " + decryptedEmail);
        String decryptedPassword = encryptor.decrypt(encryptedPassword);
        System.out.println("Decrypted Password: " + decryptedPassword);
    }

}
