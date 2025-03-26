package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.AccesosDto;
import com.towers.huellacarbonobackend.dto.ArchivoDto;
import com.towers.huellacarbonobackend.dto.EmpresaDto;
import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.service.data.EmpresaArchivoService;
import com.towers.huellacarbonobackend.service.data.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
@RequiredArgsConstructor
public class EmpresaController {
    private final EmpresaService empresaService;
    private final EmpresaArchivoService empresaArchivoService;

    @GetMapping("/archivo/{id}/{anio}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'REGISTER')")
    public ResponseEntity<ResponseDto> getArchivos(@PathVariable Long id, @PathVariable Integer anio) {
        List<ArchivoDto> archivos = empresaArchivoService.getArchivosByAnio(id, anio);
        boolean accesos = !archivos.isEmpty();
        return new ResponseEntity<>(
                new ResponseDto(
                        new AccesosDto(accesos, archivos)
                        , true),
                HttpStatus.OK
        );
    }

    @PostMapping("/archivo/save/{id}/{anio}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> saveArchivos(@RequestBody List<Integer> archivos, @PathVariable Long id, @PathVariable Integer anio) {
        empresaArchivoService.save(archivos, id, anio);
        return new ResponseEntity<>(
                new ResponseDto("Accesos guardados correctamente.", true)
                , HttpStatus.OK
        );
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> save(@RequestBody EmpresaDto empresaDto) {
        empresaService.registrarEmpresa(empresaDto);
        return new ResponseEntity<>(
                new ResponseDto("Empresa registrada correctamente. Se ha enviado un correo para configurar la contraseña.", true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/exists/{id}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> existsByEmpresa(@PathVariable Long id) {
        boolean existsArchivos = empresaArchivoService.existsByEmpresa(id);
        return new ResponseEntity<>(
                new ResponseDto(
                        existsArchivos
                        , true),
                HttpStatus.OK
        );
    }

    @GetMapping("/anios/{id}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getAniosByEmpresa(@PathVariable Long id) {
        List<Integer> aniosByEmpresa = empresaArchivoService.getAniosByEmpresa(id);
        return new ResponseEntity<>(
                new ResponseDto(
                        aniosByEmpresa
                        , true),
                HttpStatus.OK
        );
    }
}
