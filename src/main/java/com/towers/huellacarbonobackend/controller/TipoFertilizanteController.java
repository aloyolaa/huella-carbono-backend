package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.entity.data.TipoFertilizante;
import com.towers.huellacarbonobackend.service.data.TipoFertilizanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tipo-fertilizante")
@RequiredArgsConstructor
public class TipoFertilizanteController {
    private final TipoFertilizanteService tipoFertilizanteService;

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'REGISTER')")
    public ResponseEntity<ResponseDto> getAll() {
        List<TipoFertilizante> all = tipoFertilizanteService.getAll();
        return new ResponseEntity<>(
                new ResponseDto(all, true)
                , HttpStatus.OK
        );
    }
}
