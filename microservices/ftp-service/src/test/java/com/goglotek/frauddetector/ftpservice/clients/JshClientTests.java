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

package com.goglotek.frauddetector.ftpservice.clients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.goglotek.frauddetector.ftpservice.configuration.Config;
import com.goglotek.frauddetector.ftpservice.cryptography.Cryptography;
import com.goglotek.frauddetector.ftpservice.domain.TransactionsFile;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Vector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class JshClientTests {

  @MockitoBean
  private Config config;

  @MockitoSpyBean
  private JshClient jshClient;

  @MockitoBean
  private JSch jsch;

  @MockitoBean
  private Cryptography cryptography;

  @MockitoBean
  private Session session;

  @MockitoBean
  private ChannelSftp channelSftp;

  private static String rootTestFolder = "localRoot";

  @BeforeEach
  public void setUp() throws Exception {
    // Mock config values
    when(config.getUsername()).thenReturn("testUser");
    when(config.getRemoteHost()).thenReturn("localhost");
    when(config.getPort()).thenReturn(22);
    when(config.getPassword()).thenReturn("secret");
    when(config.getLocalRootFolder()).thenReturn(rootTestFolder);
    when(config.getLocalFolder()).thenReturn("localFolder");
    when(config.getRemoteFolder()).thenReturn("/remote/");
    when(config.getEncryptionKey()).thenReturn("key123");
    when(config.getInitVector()).thenReturn("iv123");
    when(config.getFileType()).thenReturn("txt");
    jshClient.setJsch(jsch);

    // Mock JSch session
    when(jsch.getSession("testUser", "localhost", 22)).thenReturn(session);
    doNothing().when(session).setPassword("secret");
    doNothing().when(session).setConfig(any());
    doNothing().when(session).connect();
    when(session.openChannel("sftp")).thenReturn(channelSftp);

    // Mock SFTP channel
    doNothing().when(channelSftp).connect();
    Vector<ChannelSftp.LsEntry> fileList = new Vector<>();
    ChannelSftp.LsEntry mockEntry = mock(ChannelSftp.LsEntry.class);
    when(mockEntry.getFilename()).thenReturn("transactions.txt");
    fileList.add(mockEntry);
    when(channelSftp.ls("/remote/")).thenReturn(fileList);

    byte[] fileBytes = "test data".getBytes();
    when(channelSftp.get("/remote/transactions.txt")).thenReturn(
        new ByteArrayInputStream(fileBytes));
    when(cryptography.encrypt(any(byte[].class), anyString(), anyString())).thenReturn(fileBytes);

    doNothing().when(channelSftp).rm("/remote/transactions.txt");

    FileOutputStream mockStream = Mockito.mock(FileOutputStream.class);
    Mockito.doReturn(mockStream)
        .when(jshClient)
        .getFileOutputStream(Mockito.any(File.class));
    doNothing().when(mockStream).write(Mockito.any(byte[].class));
  }

  @Test
  public void testDownloadFiles() throws Exception {
    List<TransactionsFile> result = jshClient.downloadFiles();

    assertEquals(1, result.size());
    assertEquals("transactions.txt", result.get(0).getFileName());

    verify(channelSftp, times(1)).get("/remote/transactions.txt");
    verify(channelSftp, times(1)).rm("/remote/transactions.txt");
    verify(cryptography, times(1)).encrypt(any(byte[].class), anyString(), anyString());
  }

  @AfterAll
  public static void tearDown() throws IOException {
    //delete temp test folder
    Files.walkFileTree(Paths.get(rootTestFolder), new SimpleFileVisitor<Path>() {
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
