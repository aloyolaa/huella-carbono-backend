package com.towers.huellacarbonobackend.service.security;

import com.towers.huellacarbonobackend.dto.UsuarioRegisterDto;
import com.towers.huellacarbonobackend.entity.data.Empresa;
import com.towers.huellacarbonobackend.entity.data.Usuario;

public interface UsuarioService {
    Usuario findByUsername(String username);

    String getUsernameFromSecurityContext();

    Usuario saveByEmpresa(Empresa empresa, String password);

    void save(UsuarioRegisterDto usuarioRegisterDto);

    String generateTokenRestablecimiento(Usuario usuario);

    boolean updatePassword(String token, String passwordAnterior, String passwordNuevo);
}
