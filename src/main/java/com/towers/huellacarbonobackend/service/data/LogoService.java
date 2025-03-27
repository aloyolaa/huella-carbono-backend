package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.dto.LogoDto;
import org.springframework.web.multipart.MultipartFile;

public interface LogoService {
    LogoDto save(MultipartFile signatureFile, Long id);

    LogoDto getByEmpresa(Long id);
}
