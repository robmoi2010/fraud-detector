package com.goglotek.mpesareconciliation.ftpservice.clients;

import com.goglotek.cypher.encryption.EncryptionDecryption;
import com.goglotek.mpesareconciliation.ftpservice.domain.MpesaFileDto;
import com.google.common.io.ByteStreams;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class FtpClient {
    static final Logger logger = LogManager.getLogger(FtpClient.class);
    private final String remoteHost;
    private final String username;
    private final String password;
    private final int port;
    private final String remoteFolder;
    private final String localFolder;
    private final String encryptionKey;
    private final String initVector;
    private final String localRootFolder;

    public FtpClient(final String remoteHost, final String username, final String password, final int port,
                     final String remoteFolder, final String localRootFolder, final String localFolder, final String encryptionKey, final String initVector) {
        super();
        this.remoteHost = remoteHost;
        this.username = username;
        this.password = password;
        this.port = port;
        this.remoteFolder = remoteFolder;
        this.localFolder = localFolder;
        this.encryptionKey = encryptionKey;
        this.initVector = initVector;
        this.localRootFolder = localRootFolder;
    }

    private Session setupFtpClient() throws JSchException {
        JSch jsch = new JSch();
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        Session jschSession = jsch.getSession(username, remoteHost, port);
        jschSession.setPassword(password);
        jschSession.setConfig(config);
        jschSession.connect();
        return jschSession;
    }

    public List<MpesaFileDto> downloadFiles() throws Exception {
        ChannelSftp channel = null;
        Session jschSession = null;
        List<MpesaFileDto> ftpFiles = new ArrayList<MpesaFileDto>();
        try {
            jschSession = setupFtpClient();
            channel = (ChannelSftp) jschSession.openChannel("sftp");
            channel.connect();
            String separator = FileSystems.getDefault().getSeparator();
            String path = new File(".").getAbsolutePath();
            //level above abs path
            String absolutePath = path.substring(0, path.lastIndexOf(separator));

            String fileDirectory = absolutePath + separator + localRootFolder + separator + localFolder;
            File local = new File(fileDirectory);
            if (!local.exists()) {
                Path p = Files.createDirectory(Paths.get(fileDirectory));
            }
            Vector<ChannelSftp.LsEntry> fileList = channel.ls(remoteFolder);
            File destFile;
            for (ChannelSftp.LsEntry file : fileList) {
                InputStream in = null;
                FileOutputStream fout = null;
                try {
                    if (isRequiredFile(file.getFilename())) {
                        destFile = new File(fileDirectory, file.getFilename());
                        if (destFile.exists()) {
                            logger.info("file already exist " + destFile.getAbsolutePath());
                        } else {
                            in = channel.get(remoteFolder + file.getFilename());
                            byte[] unencrypted = ByteStreams.toByteArray(in);
                            byte[] encrypted = new EncryptionDecryption(encryptionKey, initVector)
                                    .encrypt(unencrypted);
                            fout = new FileOutputStream(destFile);
                            fout.write(encrypted);
                            MpesaFileDto ftpFile = new MpesaFileDto();
                            ftpFile.setFileName(destFile.getName());
                            ftpFile.setCreatedOn(new Date());
                            ftpFile.setAbsolutePath(destFile.getAbsolutePath());
                            ftpFiles.add(ftpFile);
                        }
                        channel.rm(remoteFolder + file.getFilename());// for when server does not
                        // move accessed
                        // files. comment if server moves accessed
                        // files to different directory
                    }
                } catch (Exception e) {
                    logger.error(e);
                } finally {
                    if (in != null) {
                        in.close();
                    }
                    if (fout != null) {
                        fout.close();
                    }
                }
            }

        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }
        return ftpFiles;
    }

    private boolean isRequiredFile(String filename) {
        if (filename.contains(".csv")) {
            return true;
        }
        return false;
    }

    public String getRemoteFolder() {
        return remoteFolder;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
