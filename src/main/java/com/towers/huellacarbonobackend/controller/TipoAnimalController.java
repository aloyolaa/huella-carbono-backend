package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.entity.data.TipoAnimal;
import com.towers.huellacarbonobackend.service.data.TipoAnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tipo-animal")
@RequiredArgsConstructor
public class TipoAnimalController {
    private final TipoAnimalService tipoAnimalService;

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'REGISTER')")
    public ResponseEntity<ResponseDto> getAll() {
        List<TipoAnimal> all = tipoAnimalService.getAll();
        return new ResponseEntity<>(
                new ResponseDto(all, true)
                , HttpStatus.OK
        );
    }
}
