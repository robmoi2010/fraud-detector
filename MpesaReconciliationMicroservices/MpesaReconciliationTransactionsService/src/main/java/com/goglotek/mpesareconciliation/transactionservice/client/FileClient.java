package com.goglotek.mpesareconciliation.transactionservice.client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.goglotek.mpesareconciliation.transactionservice.model.FileType;

public class FileClient {
	private final String stagingDir;
	private final String errorDir;
	private final String processedDir;
	private final String dirSeparator;
	private final String unauthorizedDir;

	public FileClient(String stagingDir, String errorDir, String processedDir, String unauthorizedDir) {
		this.stagingDir = stagingDir;
		this.errorDir = errorDir;
		this.processedDir = processedDir;
		this.unauthorizedDir = unauthorizedDir;
		this.dirSeparator = FileSystems.getDefault().getSeparator();
	}

	public byte[] getFile(final String filePath) throws Exception {
		Path path = Paths.get(filePath);
		byte[] encypted = Files.readAllBytes(path);
		return encypted;
	}

	public void saveFile(final byte[] file, final String filename, final FileType type) throws Exception {
		InputStream input = null;
		try {
			String path = "";
			if (type == FileType.ERROR) {
				path = errorDir + dirSeparator + filename;
			} else if (type == FileType.PROCESSED) {
				path = processedDir + dirSeparator + filename;
			} else if (type == FileType.UNAUTHORIZED) {
				path = unauthorizedDir + dirSeparator + filename;
			} else if (type == FileType.UNPROCESSED) {
				path = stagingDir + dirSeparator + filename;
			}
			Path p = Paths.get(path);
			input = new ByteArrayInputStream(file);
			Files.copy(input, p, StandardCopyOption.REPLACE_EXISTING);
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}

	public void deleteFile(final String filename, final FileType type) throws Exception {
		Path p = null;
		if (type == FileType.ERROR) {
			p = Paths.get(errorDir + dirSeparator + filename);
		} else if (type == FileType.PROCESSED) {
			p = Paths.get(processedDir + dirSeparator + filename);
		} else if (type == FileType.UNAUTHORIZED) {
			p = Paths.get(unauthorizedDir + dirSeparator + filename);
		} else if (type == FileType.UNPROCESSED) {
			p = Paths.get(stagingDir + dirSeparator + filename);
		}
		Files.deleteIfExists(p);
	}

	public String getStagingDir() {
		return stagingDir;
	}

	public String getErrorDir() {
		return errorDir;
	}

	public String getProcessedDir() {
		return processedDir;
	}

	public String getDirSeparator() {
		return dirSeparator;
	}

	public String getUnauthorizedDir() {
		return unauthorizedDir;
	}

}
