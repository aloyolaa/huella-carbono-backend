package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.entity.TipoCombustible;
import com.towers.huellacarbonobackend.service.TipoCombustibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tipo-combustible")
@RequiredArgsConstructor
public class TipoCombustibleController {
    private final TipoCombustibleService tipoCombustibleService;

    @GetMapping("/")
    public ResponseEntity<ResponseDto> getAll() {
        List<TipoCombustible> all = tipoCombustibleService.getAll();
        return new ResponseEntity<>(
                new ResponseDto(
                        all,
                        true)
                , HttpStatus.OK
        );
    }
}
