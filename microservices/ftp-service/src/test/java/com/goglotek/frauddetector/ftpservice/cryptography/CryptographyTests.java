/*
 *
 *  * Copyright (C) 2025 Robert Moi, Goglotek LTD
 *  *
 *  * This file is part of the Fraud Detector System.
 *  *
 *  * The Fraud Detector System is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * The Fraud Detector System is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with the Fraud Detector System. If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package com.goglotek.frauddetector.ftpservice.cryptography;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.goglotek.frauddetector.ftpservice.exception.GoglotekException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import javax.crypto.BadPaddingException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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
    assertTrue(!new String(encrypted).equals(encryptTxt));

    //decrypt
    String decryptedTxt = new String(cypher.decrypt(encrypted, key, initVector));
    assertTrue(decryptedTxt.equals(encryptTxt));
  }

  @Test
  public void shouldEncryptAndDecryptFileSuccessfully() throws GoglotekException, IOException {
    //create file and write text to it
    Path p = Files.createDirectory(Paths.get(rootFolder));
    p = Files.createFile(
        Paths.get(p.toString() + FileSystems.getDefault().getSeparator() + fileName));
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
    assertTrue(!new String(encrypted).equals(encryptTxt));

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
