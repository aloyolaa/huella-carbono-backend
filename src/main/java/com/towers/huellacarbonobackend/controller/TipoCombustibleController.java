package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.dto.TipoCombustibleDto;
import com.towers.huellacarbonobackend.service.TipoCombustibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tipo-combustible")
@RequiredArgsConstructor
public class TipoCombustibleController {
    private final TipoCombustibleService tipoCombustibleService;

    @GetMapping("/{archivo}")
    public ResponseEntity<ResponseDto> getAll(@PathVariable Long archivo) {
        List<TipoCombustibleDto> all = tipoCombustibleService.getAllByArchivo(archivo);
        return new ResponseEntity<>(
                new ResponseDto(
                        all,
                        true)
                , HttpStatus.OK
        );
    }
}
