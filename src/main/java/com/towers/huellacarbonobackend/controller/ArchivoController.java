package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.ArchivoDto;
import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.service.data.ArchivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/archivo")
@RequiredArgsConstructor
public class ArchivoController {
    private final ArchivoService archivoService;

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'REGISTER')")
    public ResponseEntity<ResponseDto> getAll() {
        List<ArchivoDto> archivos = archivoService.getAll();
        return new ResponseEntity<>(
                new ResponseDto(archivos, true),
                HttpStatus.OK
        );
    }
}
