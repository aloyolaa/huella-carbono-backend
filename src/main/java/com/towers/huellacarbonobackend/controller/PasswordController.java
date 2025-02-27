package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.PasswordResetDto;
import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.service.security.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordController {
    private final UsuarioService usuarioService;

    @PostMapping("/reset")
    public ResponseEntity<ResponseDto> resetPassword(@RequestBody PasswordResetDto resetDTO) {
        boolean password = usuarioService.actualizarPassword(
                resetDTO.token(),
                resetDTO.passwordAnterior(),
                resetDTO.passwordNuevo());

        return new ResponseEntity<>(new ResponseDto(
                password, true)
                , HttpStatus.OK
        );
    }
}
