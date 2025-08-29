package com.goglotek.frauddetector.dataprocessorservice.cryptography;

import com.goglotek.frauddetector.dataprocessorservice.exception.GoglotekException;
import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;

@Component
public class Cryptography {

    private final String salt = "gogloteksalt123";

    public Cryptography() {

    }

    public byte[] encrypt(byte[] data, String encryptionKey, String initVector) throws GoglotekException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(encryptionKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                    .getEncoded(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(initVector.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secret, ivSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new GoglotekException(e, "Encryption Error: " + e.getMessage());
        }
    }

    public byte[] decrypt(byte[] data, String encryptionKey, String initVector) throws GoglotekException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(encryptionKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                    .getEncoded(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(initVector.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secret, ivSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new GoglotekException(e, "Decryption Error: " + e.getMessage());
        }
    }

}
