package com.goglotek.frauddetector.ftpservice.clients;

import com.goglotek.frauddetector.ftpservice.configuration.Config;
import com.goglotek.frauddetector.ftpservice.cryptography.Cryptography;
import com.goglotek.frauddetector.ftpservice.domain.TransactionsFile;
import com.jcraft.jsch.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class JshClientTests {
    @MockBean
    private Config config;

    @SpyBean
    private JshClient jshClient;

    @MockBean
    private JSch jsch;

    @MockBean
    private Cryptography cryptography;

    @MockBean
    private Session session;

    @MockBean
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
        when(channelSftp.get("/remote/transactions.txt")).thenReturn(new ByteArrayInputStream(fileBytes));
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
