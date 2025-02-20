package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.DataDto;
import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.service.data.DataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {
    private final DataService dataService;

    @PostMapping("/save/{empresa}/{archivo}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getAll(@Valid @RequestBody DataDto dataDto, @PathVariable Long empresa, @PathVariable Long archivo) {
        dataService.save(dataDto, empresa, archivo);
        return new ResponseEntity<>(
                new ResponseDto("Datos guardados correctamente.", true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/{empresa}/{archivo}/{anio}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> get(@PathVariable Long empresa, @PathVariable Long archivo, @PathVariable Integer anio) {
        DataDto byEmpresaAndAnio = dataService.getByEmpresaAndAnio(empresa, archivo, anio);
        return new ResponseEntity<>(
                new ResponseDto(byEmpresaAndAnio, true)
                , HttpStatus.OK
        );
    }
}
