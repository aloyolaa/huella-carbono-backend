package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.entity.TipoRefrigerante;
import com.towers.huellacarbonobackend.service.TipoRefrigeranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tipo-refrigerante")
@RequiredArgsConstructor
public class TipoRefrigeranteController {
    private final TipoRefrigeranteService tipoRefrigeranteService;

    @GetMapping("/")
    public ResponseEntity<ResponseDto> getAll() {
        List<TipoRefrigerante> all = tipoRefrigeranteService.getAll();
        return new ResponseEntity<>(
                new ResponseDto(
                        all,
                        true)
                , HttpStatus.OK
        );
    }
}
