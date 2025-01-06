package com.towers.huellacarbonobackend.service.security.impl;

import com.towers.huellacarbonobackend.entity.Usuario;
import com.towers.huellacarbonobackend.service.security.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByUsername(username);

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getHabilitado(),
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority(usuario.getRole().getNombre()))
        );
    }
}
