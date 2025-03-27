package com.towers.huellacarbonobackend.service.file.ftp;

import com.towers.huellacarbonobackend.exception.FileStorageException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FtpFileStorageService {
    @Value("${ftp.host}")
    private String ftpHost;

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.password}")
    private String ftpPassword;

    public byte[] loadFileAsResource(String fileName, String ftpRemoteDir) {
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(ftpHost);
            ftpClient.login(ftpUsername, ftpPassword);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpRemoteDir);

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ftpClient.retrieveFile(fileName, outputStream);
                return outputStream.toByteArray();
            } finally {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            throw new FileStorageException("Error leyendo el archivo: " + fileName);
        }
    }

    public String storeFile(MultipartFile file, String ftpRemoteDir) {
        String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(ftpHost);
            ftpClient.login(ftpUsername, ftpPassword);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpRemoteDir);
            try (InputStream inputStream = file.getInputStream()) {
                ftpClient.storeFile(fileName, inputStream);
            }
            ftpClient.disconnect();
            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Error guardando el archivo. " + e.getMessage());
        }
    }

    public void deleteFile(String fileName, String ftpRemoteDir) {
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(ftpHost);
            ftpClient.login(ftpUsername, ftpPassword);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpRemoteDir);
            ftpClient.deleteFile(fileName);
            ftpClient.disconnect();
        } catch (IOException e) {
            throw new FileStorageException("Error al eliminar el archivo: " + fileName);
        }
    }
}
