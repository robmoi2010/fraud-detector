package com.goglotek.mpesareconciliation.transactionservice.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goglotek.cypher.encryption.EncryptionDecryption;
import com.goglotek.mpesareconciliation.transactionservice.client.FileClient;
import com.goglotek.mpesareconciliation.transactionservice.exception.UnauthorizedMpesaFileException;
import com.goglotek.mpesareconciliation.transactionservice.model.FileType;
import com.goglotek.mpesareconciliation.transactionservice.model.MpesaFetchedTrans;
import com.goglotek.mpesareconciliation.transactionservice.model.MpesaFileDto;
import com.goglotek.mpesareconciliation.transactionservice.model.MpesaFiles;
import com.goglotek.mpesareconciliation.transactionservice.repository.MpesaFilesRepository;
import com.goglotek.mpesareconciliation.transactionservice.service.MpesaFetchedTransService;
import com.goglotek.mpesareconciliation.transactionservice.service.MpesaFilesService;
import com.goglotek.mpesareconciliation.transactionservice.service.MpesaReconPaybillsService;

@Service
public class MpesaFilesServiceImpl implements MpesaFilesService {
    Logger logger = LogManager.getLogger(MpesaFilesServiceImpl.class);
    @Autowired
    MpesaFilesRepository mpesaFilesRepository;

    @Autowired
    MpesaReconPaybillsService mpesaReconPaybillsService;

    @Autowired
    MpesaFetchedTransService mpesaFetchedTransService;

    @Value("${goglotek.transactions.error_folder}")
    private String errorDir;

    @Value("${goglotek.transactions.staging_folder}")
    private String stagingDir;

    @Value("${goglotek.transactions.processed_folder}")
    private String processedDir;

    @Value("${goglotek.transactions.unauthorized_folder}")
    private String unauthorizedDir;

    @Value("${goglotek.transactions.root_folder}")
    private String rootDir;

    @Value("${goglotek.cypher.encryption_key}")
    private String encryptionKey;

    @Value("${goglotek.cypher.init_vector}")
    private String initVector;

    @Override
    public MpesaFiles save(MpesaFiles file) {
        return mpesaFilesRepository.save(file);
    }

    @Override
    public MpesaFiles findByFileName(String name) {
        return mpesaFilesRepository.findByFileName(name);
    }

    private void saveAndDeleteFile(FileClient fClient, byte[] file, String fileName, FileType type) throws Exception {
        fClient.saveFile(file, fileName, type);
        fClient.deleteFile(fileName, FileType.UNPROCESSED);
    }

    private MpesaFiles extractFileRecord(CSVRecord record) throws Exception {
        MpesaFiles mpesaFile = new MpesaFiles();
        mpesaFile.setShortcode(record.get("STORE_SHORT_CODE"));
        mpesaFile.setAccount(record.get("ACCOUNT_TYPE_NAME"));
        mpesaFile.setAccountHolder(record.get("STORE_NAME"));
        mpesaFile.setCompanyId(0);
        mpesaFile.setCreatedDate(new Date());
        mpesaFile.setGroupId(0);
        mpesaFile.setModifiedDate(new Date());
        mpesaFile.setOrganization(record.get("HEAD_OFFICE_NAME"));
        mpesaFile.setProcessed(false);
        mpesaFile.setSystemId(UUID.randomUUID().toString());
        mpesaFile.setRetrievedByName("Automated System");
        mpesaFile.setUserId(0);
        return mpesaFile;
    }

    private MpesaFetchedTrans extractRecord(CSVRecord record) throws Exception {
        MpesaFetchedTrans trans = new MpesaFetchedTrans();
        Date transTime = null;
        trans.setBalance(Double.parseDouble(record.get("BALANCE")));
        trans.setCompanyId(0);
        transTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(record.get("END_TIMESTAMP"));
        trans.setCompletionTime(transTime);
        trans.setCreatedDate(new Date());
        String details = record.get("TRANSACTION_PARTY_DETAILS");
        trans.setDetails(details);
        trans.setGroupId(0);
        trans.setInitiationTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(record.get("START_TIMESTAMP")));
        trans.setLinkedTransId(record.get("LINKED_TRANSACTION_ID"));
        trans.setModifiedDate(new Date());
        trans.setMsisdn(record.get("PRIMARY_PARTY_IDENTIFIER"));
        trans.setOtherPartyInfo(record.get("TRANSACTION_PARTY_DETAILS"));
        trans.setPaidIn(Double.parseDouble(record.get("CREDIT_AMOUNT")));
        trans.setReasonType(record.get("ACCOUNT_TYPE_NAME"));
        trans.setReceiptNo(record.get("RECEIPT_NUMBER"));
        trans.setShortcode(record.get("STORE_SHORT_CODE"));
        trans.setSystemId(UUID.randomUUID().toString());
        trans.setTransactionStatus(record.get("TRANSACTION_STATUS"));
        trans.setUserId(0);
        trans.setUsername("Automated System");
        trans.setWithdrawn(Double.parseDouble(record.get("DEBIT_AMOUNT")));
        String names = "";
        try {
            names = record.get("TRANSACTION_PARTY_DETAILS").contains("-")
                    ? record.get("TRANSACTION_PARTY_DETAILS").split("-")[1].trim().split("Acc.")[0]
                    : "";
        } catch (Exception e) {
            logger.error(e);
        }
        String firstName = "";
        String middleName = "";
        String lastName = "";
        if (!names.isEmpty()) {
            String[] n = names.trim().split(" ");
            if (n.length < 2) {
                firstName = n[0].trim();
            } else if (n.length == 2) {
                firstName = n[0].trim();
                lastName = n[1].trim();
            } else if (n.length > 2) {
                firstName = n[0].trim();
                middleName = n[1].trim();
                lastName = n[2].trim();
            }
        }
        trans.setFirstname(firstName);
        trans.setMiddlename(middleName);
        trans.setLastname(lastName);
        return trans;
    }

    @Transactional
    private long processMpesaFile(FileClient fClient, byte[] fl, String fileName) throws Exception {
        int totalRecords = 0;
        MpesaFiles mFile = null;
        Date highestDate = null;
        Date lowestDate = null;
        try (ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(fl)) {
            try (InputStreamReader input = new InputStreamReader(byteArrayInput)) {
                Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(input);
                for (CSVRecord record : records) {
                    if (totalRecords == 0) {
                        mFile = extractFileRecord(record);
                        mFile.setFileName(fileName);
                        mFile = mpesaFilesRepository.save(mFile);
                        if (mpesaReconPaybillsService.findByPaybill(mFile.getShortcode()) == null) {
                            throw new UnauthorizedMpesaFileException("File " + fileName + " for shortcode "
                                    + mFile.getShortcode() + " is not authorized for reconciliation");
                        }
                    }
                    MpesaFetchedTrans trans = extractRecord(record);
                    trans.setFileId(mFile.getFileId());
                    if (highestDate == null) {
                        highestDate = trans.getCompletionTime();
                    } else if (trans.getCompletionTime().after(highestDate)) {
                        highestDate = trans.getCompletionTime();
                    }
                    if (lowestDate == null) {
                        lowestDate = trans.getCompletionTime();
                    } else if (trans.getCompletionTime().before(lowestDate)) {
                        lowestDate = trans.getCompletionTime();
                    }
                    mpesaFetchedTransService.save(trans);
                    totalRecords++;
                }
                mFile.setFromDate(lowestDate);
                mFile.setToDate(highestDate);
                mFile.setTransCount(totalRecords);
                mpesaFilesRepository.save(mFile);
                return mFile.getFileId();
            }
        }

    }

    @Override
    public List<MpesaFileDto> stageTransactions(final List<MpesaFileDto> files) {
        List<MpesaFileDto> filesDto = new ArrayList<MpesaFileDto>();
        try {
            String dirSeparator = FileSystems.getDefault().getSeparator();
            String path = new File(".").getAbsolutePath();
            //level above abs path
            String absolutePath = path.substring(0, path.lastIndexOf(dirSeparator));

            String rootDirectory = absolutePath + dirSeparator + rootDir;

            FileClient fClient = new FileClient(rootDirectory + dirSeparator + stagingDir, rootDirectory + dirSeparator + errorDir,
                    rootDirectory + dirSeparator + processedDir, rootDirectory + dirSeparator + unauthorizedDir);
            for (MpesaFileDto file : files) {
                try {
                    byte[] fl = null;
                    fl = fClient.getFile(file.getAbsolutePath());
                    fl = new EncryptionDecryption(encryptionKey, initVector).decrypt(fl);
                    try {
                        MpesaFiles f = findByFileName(file.getFileName());
                        if (f == null) {
                            long fileId = processMpesaFile(fClient, fl, file.getFileName());
                            saveAndDeleteFile(fClient, fl, file.getFileName(), FileType.PROCESSED);
                            file.setFileId(fileId);
                            filesDto.add(file);
                        } else {
                            logger.error(
                                    "File " + file.getFileName() + " already staged at " + f.getCreatedDate() + "");
                            saveAndDeleteFile(fClient, fl, file.getFileName(), FileType.ERROR);
                        }
                    } catch (UnauthorizedMpesaFileException e) {
                        logger.error(e);
                        saveAndDeleteFile(fClient, fl, file.getFileName(), FileType.UNAUTHORIZED);
                    } catch (Exception e) {
                        logger.error(e);
                        saveAndDeleteFile(fClient, fl, file.getFileName(), FileType.ERROR);
                    }
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return filesDto;
    }
}
