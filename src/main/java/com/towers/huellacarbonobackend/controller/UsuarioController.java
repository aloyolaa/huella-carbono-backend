package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.AccesosDto;
import com.towers.huellacarbonobackend.dto.ArchivoDto;
import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.dto.UsuarioRegisterDto;
import com.towers.huellacarbonobackend.service.security.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> save(@RequestBody UsuarioRegisterDto usuarioRegisterDto) {
        usuarioService.save(usuarioRegisterDto);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Usuario registrado correctamente."
                        , true),
                HttpStatus.OK
        );
    }
}
