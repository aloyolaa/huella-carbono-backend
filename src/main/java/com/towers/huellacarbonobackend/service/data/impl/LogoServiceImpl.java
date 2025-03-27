package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.dto.LogoDto;
import com.towers.huellacarbonobackend.entity.data.Empresa;
import com.towers.huellacarbonobackend.entity.data.Logo;
import com.towers.huellacarbonobackend.exception.DataAccessExceptionImpl;
import com.towers.huellacarbonobackend.repository.LogoRepository;
import com.towers.huellacarbonobackend.service.data.LogoService;
import com.towers.huellacarbonobackend.service.file.ftp.FtpFileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoServiceImpl implements LogoService {
    private final LogoRepository logoRepository;
    private final FtpFileStorageService ftpFileStorageService;

    @Value("${ftp.logos}")
    private String ftpRemoteDir;


    @Override
    @Transactional
    public LogoDto save(MultipartFile logoFile, Long id) {
        Optional<Logo> optionalLogo = logoRepository.findByEmpresa(id);

        Logo logo;
        String logoPath = ftpFileStorageService.storeFile(logoFile, ftpRemoteDir);

        if (optionalLogo.isPresent()) {
            logo = optionalLogo.orElseThrow();
            String previousLogo = logo.getLogoFile();
            logo.setLogoFile(logoPath);
            ftpFileStorageService.deleteFile(previousLogo, ftpRemoteDir);
        } else {
            logo = new Logo();
            logo.setLogoFile(logoPath);
            logo.setEmpresa(new Empresa(id));
        }

        logoRepository.save(logo);

        return getByEmpresa(id);
    }

    @Override
    @Transactional(readOnly = true)
    public LogoDto getByEmpresa(Long id) {
        try {
            Optional<Logo> byEmpresa = logoRepository.findByEmpresa(id);
            if (byEmpresa.isEmpty()) {
                return null;
            }
            String logoFile = Base64.getEncoder().encodeToString(ftpFileStorageService.loadFileAsResource(byEmpresa.orElseThrow().getLogoFile(), ftpRemoteDir));

            return new LogoDto(logoFile);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }
}
