package com.goglotek.frauddetector.datastoreservice.cryptography;

import com.goglotek.frauddetector.datastoreservice.exception.GoglotekException;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

@Component
public class Cryptography {

    // AES-GCM parameters
    private static final int GCM_TAG_LENGTH = 128;   // bits
    private static final int GCM_IV_LENGTH = 12;     // bytes (96 bits, recommended)
    private static final String SALT = "gogloteksalt1234"; // fixed salt

    private final SecureRandom secureRandom = new SecureRandom();

    public byte[] encrypt(byte[] data, String encryptionKey) throws GoglotekException {
        try {
            // Derive key from password
            SecretKey secretKey = deriveKey(encryptionKey);

            // Generate random IV
            byte[] iv = new byte[GCM_IV_LENGTH];
            secureRandom.nextBytes(iv);

            // Initialize cipher
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec);

            // Encrypt
            byte[] cipherText = cipher.doFinal(data);

            // Combine IV + CipherText
            byte[] encrypted = new byte[iv.length + cipherText.length];
            System.arraycopy(iv, 0, encrypted, 0, iv.length);
            System.arraycopy(cipherText, 0, encrypted, iv.length, cipherText.length);

            return encrypted;
        } catch (Exception e) {
            throw new GoglotekException(e, "Encryption Error: " + e.getMessage());
        }
    }

    public byte[] decrypt(byte[] encryptedData, String encryptionKey) throws GoglotekException {
        try {
            // Derive key from password
            SecretKey secretKey = deriveKey(encryptionKey);

            // Extract IV
            byte[] iv = Arrays.copyOfRange(encryptedData, 0, GCM_IV_LENGTH);
            byte[] cipherText = Arrays.copyOfRange(encryptedData, GCM_IV_LENGTH, encryptedData.length);

            // Initialize cipher
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec);

            // Decrypt
            return cipher.doFinal(cipherText);
        } catch (Exception e) {
            throw new GoglotekException(e, "Decryption Error: " + e.getMessage());
        }
    }

    private SecretKey deriveKey(String password) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }
}
