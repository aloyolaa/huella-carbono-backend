package com.towers.huellacarbonobackend.service.security;

import com.towers.huellacarbonobackend.entity.data.Empresa;
import com.towers.huellacarbonobackend.entity.data.Usuario;

public interface UsuarioService {
    Usuario findByUsername(String username);

    String getUsernameFromSecurityContext();

    Usuario crearUsuarioParaEmpresa(Empresa empresa, String password);

    String generarTokenRestablecimiento(Usuario usuario);

    boolean actualizarPassword(String token, String passwordAnterior, String passwordNuevo);
}
