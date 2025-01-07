package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.ArchivoDto;
import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.service.data.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/empresa")
@RequiredArgsConstructor
public class EmpresaController {
    private final EmpresaService empresaService;

    @GetMapping("/archivo/{id}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getArchivos(@PathVariable Long id) {
        List<ArchivoDto> archivos = empresaService.getArchivos(id);
        return new ResponseEntity<>(
                new ResponseDto(archivos, true),
                HttpStatus.OK
        );
    }
}
