package com.goglotek.fraud_detector.ftpservice.clients;

import com.goglotek.fraud_detector.ftpservice.configuration.Config;
import com.goglotek.fraud_detector.ftpservice.cryptography.Cryptography;
import com.goglotek.fraud_detector.ftpservice.domain.TransactionsFile;
import com.goglotek.fraud_detector.ftpservice.exception.GoglotekException;
import com.google.common.io.ByteStreams;
import com.jcraft.jsch.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Component
public class JshClient implements Client {

    private static final Logger logger = LogManager.getLogger(JshClient.class);

    private JSch jsch;
    @Autowired
    private Cryptography cryptography;

    @Autowired
    private Config config;


    public JshClient() {
        this.jsch = new JSch();
    }

    private Session setupFtpClient() throws JSchException {
        java.util.Properties sessionConfig = new java.util.Properties();
        sessionConfig.put("StrictHostKeyChecking", "no");

        Session jschSession = jsch.getSession(config.getUsername(), config.getRemoteHost(), config.getPort());
        jschSession.setPassword(config.getPassword());
        jschSession.setConfig(sessionConfig);
        jschSession.connect();
        return jschSession;
    }

    public List<TransactionsFile> downloadFiles() throws GoglotekException {
        List<TransactionsFile> ftpFiles = new ArrayList<>();
        Session jschSession = null;
        ChannelSftp channel = null;

        try {
            jschSession = setupFtpClient();
            channel = (ChannelSftp) jschSession.openChannel("sftp");
            channel.connect();

            String fileDirectory = buildLocalPath(config.getLocalRootFolder(), config.getLocalFolder());
            ensureDirectoryExists(fileDirectory);

            Vector<ChannelSftp.LsEntry> fileList = channel.ls(config.getRemoteFolder());
            if (fileList == null || fileList.isEmpty()) {
                logger.info("No files found in remote directory: " + config.getRemoteFolder());
                return ftpFiles;
            }

            for (ChannelSftp.LsEntry fileEntry : fileList) {
                String filename = fileEntry.getFilename();
                if (!isRequiredFile(filename)) {
                    continue;
                }

                File destFile = new File(fileDirectory, filename);
                if (destFile.exists()) {
                    logger.info("File already exists: " + destFile.getAbsolutePath());
                    continue;
                }

                try (InputStream in = channel.get(config.getRemoteFolder() + filename)) {
                    byte[] unencrypted = ByteStreams.toByteArray(in);
                    byte[] encrypted = cryptography.encrypt(unencrypted, config.getEncryptionKey(), config.getInitVector());
                    try (FileOutputStream fout = getFileOutputStream(destFile)) {
                        fout.write(encrypted);
                    }
                }

                TransactionsFile ftpFile = new TransactionsFile();
                ftpFile.setFileName(filename);
                ftpFile.setCreatedOn(new Date());
                ftpFile.setAbsolutePath(destFile.getAbsolutePath());
                ftpFile.setFileId(UUID.randomUUID().toString());
                ftpFiles.add(ftpFile);

                channel.rm(config.getRemoteFolder() + filename);
            }

        } catch (JSchException e) {
            throw new GoglotekException(e, "SFTP connection failed: " + e.getMessage());
        } catch (SftpException e) {
            throw new GoglotekException(e, "SFTP error: " + e.getMessage());
        } catch (Exception e) {
            throw new GoglotekException(e, "Unexpected error: " + e.getMessage());
        } finally {
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
            if (jschSession != null && jschSession.isConnected()) {
                jschSession.disconnect();
            }
        }

        return ftpFiles;
    }

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public Config getConfig() {
        return this.config;
    }

    private boolean isRequiredFile(String filename) {
        return filename != null && filename.toLowerCase(Locale.ENGLISH).contains(config.getFileType().toLowerCase(Locale.ENGLISH));
    }

    private String buildLocalPath(String rootFolder, String subFolder) {
        String separator = FileSystems.getDefault().getSeparator();
        String path = new File(".").getAbsolutePath();
        String absolutePath = path.substring(0, path.lastIndexOf(separator));
        return absolutePath + separator + rootFolder + separator + subFolder;
    }

    private void ensureDirectoryExists(String dirPath) throws GoglotekException {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            try {
                Files.createDirectories(Paths.get(dirPath));
            } catch (IOException e) {
                throw new GoglotekException(e, "Failed to create directory: " + dirPath);
            }
        }
    }

    public void setJsch(JSch jsch) {
        this.jsch = jsch;
    }

    public FileOutputStream getFileOutputStream(File file) throws FileNotFoundException {
        return new FileOutputStream(file);
    }
}
