package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.AccesosDto;
import com.towers.huellacarbonobackend.dto.ArchivoDto;
import com.towers.huellacarbonobackend.dto.CalculateDto;
import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.service.calculate.CalculoService;
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
@RequestMapping("/calculo")
@RequiredArgsConstructor
public class CalculoController {
    private final CalculoService calculoService;

    @GetMapping("/{empresa}/{anio}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getCalculos(@PathVariable Long empresa, @PathVariable Integer anio) {
        CalculateDto calculos = calculoService.getCalculos(empresa, anio);
        return new ResponseEntity<>(
                new ResponseDto(
                        calculos
                        , true),
                HttpStatus.OK
        );
    }
}
