package com.goglotek.mpesareconciliation.ftpservice.schedule;

import com.goglotek.mpesareconciliation.ftpservice.clients.FtpClient;
import com.goglotek.mpesareconciliation.ftpservice.service.FtpService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class FtpSchedule {
    static final Logger logger = LogManager.getLogger(FtpClient.class);
    @Autowired
    FtpService ftpService;

    @Value("${goglotek.ftp.remote_host}")
    private String remoteHost;
    @Value("${goglotek.ftp.username}")
    private String username;
    @Value("${goglotek.ftp.password}")
    private String password;
    @Value("${goglotek.ftp.port}")
    private int port;
    @Value("${goglotek.ftp.remote_folder}")
    private String remoteFolder;
    @Value("${goglotek.ftp.local_folder}")
    private String localFolder;
    @Value("${goglotek.ftp.local_root_folder}")
    private String localRootFolder;
    @Value("${goglotek.cypher.encryption_key}")
    private String encryptionKey;
    @Value("${goglotek.cypher.init_vector}")
    private String initVector;

    @Scheduled(cron = "${goglotek.scheduler.ftp_cron}")
    public void ftp() {
        try {
            ftpService.downloadFiles(remoteHost, username, password, port, remoteFolder, localRootFolder, localFolder, encryptionKey,
                    initVector);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
