package com.towers.huellacarbonobackend.service.file.ftp;

import com.towers.huellacarbonobackend.exception.FileStorageException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FtpFileStorageService {
    @Value("${ftp.host}")
    private String ftpHost;

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.password}")
    private String ftpPassword;

    @Value("${ftp.remote-dir}")
    private String ftpRemoteDir;

    public byte[] loadFileAsResource(String fileName) {
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
}
