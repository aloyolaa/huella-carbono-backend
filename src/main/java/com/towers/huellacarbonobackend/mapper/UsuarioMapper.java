package com.towers.huellacarbonobackend.mapper;

import com.towers.huellacarbonobackend.dto.UsuarioRegisterDto;
import com.towers.huellacarbonobackend.entity.data.Empresa;
import com.towers.huellacarbonobackend.entity.data.Role;
import com.towers.huellacarbonobackend.entity.data.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UsuarioMapper {
    public Usuario toUsuario(UsuarioRegisterDto usuarioRegisterDto) {
        Role register = new Role();
        register.setId(2L);

        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioRegisterDto.username().trim().toUpperCase());
        usuario.setPassword(usuarioRegisterDto.password().trim());
        usuario.setNombre(usuarioRegisterDto.nombre().trim().toUpperCase());
        usuario.setApellido(usuarioRegisterDto.apellido().trim().toUpperCase());
        usuario.setCorreo(usuarioRegisterDto.correo().trim().toLowerCase());
        usuario.setEsNuevo(false);
        usuario.setRole(register);
        usuario.setEmpresa(new Empresa(usuarioRegisterDto.empresa()));

        return usuario;
    }
}
