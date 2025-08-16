package com.goglotek.frauddetector.dataextractionservice.cryptography;

import com.goglotek.frauddetector.dataextractionservice.AbstractTests;
import com.goglotek.frauddetector.dataextractionservice.exception.GoglotekException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.BadPaddingException;
import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CryptographyTests extends AbstractTests {
    @Autowired
    private Cryptography cryptography;
    private String encryptTxt = "Encryption Test";
    private String key = "key12345678";
    private String initVector = "vector1234567890";
    private static String fileName = "text.txt";

    @Test
    public void shouldEncryptAndDecryptTextSuccessfully() throws GoglotekException {
        //encrypt
        byte[] encrypted = cryptography.encrypt(encryptTxt.getBytes(), key, initVector);
        assertTrue(encrypted.length > 0);
        assertTrue(new String(encrypted) != encryptTxt);

        //decrypt
        String decryptedTxt = new String(cryptography.decrypt(encrypted, key, initVector));
        assertTrue(decryptedTxt.equals(encryptTxt));
    }

    @Test
    public void shouldEncryptAndDecryptFileSuccessfully() throws GoglotekException, IOException {
        //create file and write text to it
        Path p = Files.createDirectory(Paths.get(rootFolder));
        p = Files.createFile(Paths.get(p.toString() + FileSystems.getDefault().getSeparator() + fileName));
        Files.write(p, encryptTxt.getBytes());

        //encrypt file
        byte[] unencrypted = Files.readAllBytes(p);
        byte[] encrypted = cryptography.encrypt(unencrypted, key, initVector);
        assertTrue(encrypted.length > 0);
        assertTrue(!new String(encrypted).equals(encryptTxt));

        //decrypt file
        byte[] decrypted = cryptography.decrypt(encrypted, key, initVector);
        assertTrue(decrypted.length > 0);
        assertTrue(new String(decrypted).equals(encryptTxt));
    }

    @Test
    public void decryptionWithWrongKeyShouldFail() throws GoglotekException {
        //encrypt
        byte[] encrypted = cryptography.encrypt(encryptTxt.getBytes(), key, initVector);
        assertTrue(encrypted.length > 0);
        assertTrue(new String(encrypted) != encryptTxt);

        //decrypt with wrong key
        try {
            byte[] decrypted = cryptography.decrypt(encrypted, "newwrongkey", initVector);

            //if exception is not thrown then test should fail
            assertTrue(false);
        } catch (GoglotekException e) {
            assertTrue(e.getCause().getClass() == BadPaddingException.class);
        }

        //decrypt with wrong IV
        try {
            byte[] decrypted = cryptography.decrypt(encrypted, key, "wrongiv123456789");

            //if exception is not thrown then test should fail
            assertTrue(false);
        } catch (GoglotekException e) {
            assertTrue(e.getCause().getClass() == BadPaddingException.class);
        }
    }
}
