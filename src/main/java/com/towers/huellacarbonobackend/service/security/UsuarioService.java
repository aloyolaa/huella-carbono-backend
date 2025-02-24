package com.towers.huellacarbonobackend.service.security;

import com.towers.huellacarbonobackend.entity.data.Usuario;

public interface UsuarioService {
    Usuario findByUsername(String username);

    String getUsernameFromSecurityContext();
}
