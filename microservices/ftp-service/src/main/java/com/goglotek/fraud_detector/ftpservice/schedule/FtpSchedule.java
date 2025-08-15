package com.goglotek.fraud_detector.ftpservice.schedule;

import com.goglotek.fraud_detector.ftpservice.service.FtpService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class FtpSchedule {
    static final Logger logger = LogManager.getLogger(FtpSchedule.class);
    @Autowired
    FtpService ftpService;

    @Scheduled(cron = "${goglotek.scheduler.ftp_cron}")
    public void ftp() {
        try {
            ftpService.downloadFiles();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
