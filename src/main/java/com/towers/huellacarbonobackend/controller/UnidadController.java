package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.entity.Unidad;
import com.towers.huellacarbonobackend.service.UnidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/unidad")
@RequiredArgsConstructor
public class UnidadController {
    private final UnidadService unidadService;

    @GetMapping("/")
    public ResponseEntity<ResponseDto> getAll() {
        List<Unidad> all = unidadService.getAll();
        return new ResponseEntity<>(
                new ResponseDto(
                        all,
                        true)
                , HttpStatus.OK
        );
    }
}
