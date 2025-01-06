package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.entity.TipoEquipo;
import com.towers.huellacarbonobackend.service.TipoEquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tipo-equipo")
@RequiredArgsConstructor
public class TipoEquipoController {
    private final TipoEquipoService tipoEquipoService;

    @GetMapping("/")
    public ResponseEntity<ResponseDto> getAll() {
        List<TipoEquipo> all = tipoEquipoService.getAll();
        return new ResponseEntity<>(
                new ResponseDto(
                        all,
                        true)
                , HttpStatus.OK
        );
    }
}
