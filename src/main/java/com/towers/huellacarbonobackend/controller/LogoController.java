package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.LogoDto;
import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.service.data.LogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/logo")
@RequiredArgsConstructor
public class LogoController {
    private final LogoService logoService;

    @PostMapping("/save/{empresa}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> save(@RequestParam MultipartFile logoFile, @PathVariable Long empresa) {
        LogoDto save = logoService.save(logoFile, empresa);
        return new ResponseEntity<>(
                new ResponseDto(
                        save,
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/{empresa}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'REGISTER')")
    public ResponseEntity<ResponseDto> getByEmpresa(@PathVariable Long empresa) {
        LogoDto byEmpresa = logoService.getByEmpresa(empresa);
        return new ResponseEntity<>(
                new ResponseDto(
                        byEmpresa,
                        true)
                , HttpStatus.OK);
    }
}
