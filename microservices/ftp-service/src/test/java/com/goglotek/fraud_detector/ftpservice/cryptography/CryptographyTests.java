package com.goglotek.fraud_detector.ftpservice.cryptography;

import com.goglotek.fraud_detector.ftpservice.exception.GoglotekException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.BadPaddingException;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CryptographyTests {
    @Autowired
    private Cryptography cypher;
    private String encryptTxt = "Encryption Test";
    private String key = "key12345678";
    private String initVector = "vector1234567890";
    private static String rootFolder = "root";
    private static String fileName = "text.txt";

    @Test
    public void shouldEncryptAndDecryptTextSuccessfully() throws GoglotekException {
        //encrypt
        byte[] encrypted = cypher.encrypt(encryptTxt.getBytes(), key, initVector);
        assertTrue(encrypted.length > 0);
        assertTrue(new String(encrypted) != encryptTxt);

        //decrypt
        String decryptedTxt = new String(cypher.decrypt(encrypted, key, initVector));
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
        byte[] encrypted = cypher.encrypt(unencrypted, key, initVector);
        assertTrue(encrypted.length > 0);
        assertTrue(!new String(encrypted).equals(encryptTxt));

        //decrypt file
        byte[] decrypted = cypher.decrypt(encrypted, key, initVector);
        assertTrue(decrypted.length > 0);
        assertTrue(new String(decrypted).equals(encryptTxt));
    }

    @Test
    public void decryptionWithWrongKeyShouldFail() throws GoglotekException {
        //encrypt
        byte[] encrypted = cypher.encrypt(encryptTxt.getBytes(), key, initVector);
        assertTrue(encrypted.length > 0);
        assertTrue(new String(encrypted) != encryptTxt);

        //decrypt with wrong key
        try {
            byte[] decrypted = cypher.decrypt(encrypted, "newwrongkey", initVector);

            //if exception is not thrown then test should fail
            assertTrue(false);
        } catch (GoglotekException e) {
            assertTrue(e.getCause().getClass() == BadPaddingException.class);
        }

        //decrypt with wrong IV
        try {
            byte[] decrypted = cypher.decrypt(encrypted, key, "wrongiv123456789");

            //if exception is not thrown then test should fail
            assertTrue(false);
        } catch (GoglotekException e) {
            assertTrue(e.getCause().getClass() == BadPaddingException.class);
        }
    }

    @AfterAll
    public static void tearDown() throws IOException {
        Files.walkFileTree(Paths.get(rootFolder), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException e)
                    throws IOException {
                if (e == null) {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                } else {
                    // directory iteration failed
                    throw e;
                }
            }
        });
    }
}
