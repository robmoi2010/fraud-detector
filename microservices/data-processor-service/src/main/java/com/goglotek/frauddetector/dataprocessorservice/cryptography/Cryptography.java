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

package com.goglotek.frauddetector.dataprocessorservice.cryptography;

import com.goglotek.frauddetector.dataprocessorservice.exception.GoglotekException;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class Cryptography {

  private final String salt = "gogloteksalt123";

  public Cryptography() {

  }

  public byte[] encrypt(byte[] data, String encryptionKey, String initVector)
      throws GoglotekException {
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

  public byte[] decrypt(byte[] data, String encryptionKey, String initVector)
      throws GoglotekException {
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
