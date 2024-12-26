package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.entity.CategoriaInstitucion;
import com.towers.huellacarbonobackend.service.CategoriaInstitucionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categoria-institución")
@RequiredArgsConstructor
public class CategoriaInstitucionController {
    private final CategoriaInstitucionService categoriaInstitucionService;

    @GetMapping("/")
    public ResponseEntity<ResponseDto> getAll() {
        List<CategoriaInstitucion> all = categoriaInstitucionService.getAll();
        return new ResponseEntity<>(
                new ResponseDto(
                        all,
                        true)
                , HttpStatus.OK
        );
    }
}