package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.*;
import com.towers.huellacarbonobackend.service.calculate.CalculoService;
import com.towers.huellacarbonobackend.service.calculate.GraficoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculo")
@RequiredArgsConstructor
public class CalculoController {
    private final CalculoService calculoService;
    private final GraficoService graficoService;

    @GetMapping("/{empresa}/{anio}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'REGISTER')")
    public ResponseEntity<ResponseDto> getCalculos(@PathVariable Long empresa, @PathVariable Integer anio) {
        CalculateDto calculos = calculoService.getCalculos(empresa, anio);
        return new ResponseEntity<>(
                new ResponseDto(
                        calculos
                        , true),
                HttpStatus.OK
        );
    }

    @GetMapping("/statistics/{empresa}/{anio}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'REGISTER')")
    public ResponseEntity<ResponseDto> getStatistics(@PathVariable Long empresa, @PathVariable Integer anio) {
        StatisticsDto grafico = graficoService.getGrafico(empresa, anio);
        return new ResponseEntity<>(
                new ResponseDto(
                        grafico
                        , true),
                HttpStatus.OK
        );
    }
}
