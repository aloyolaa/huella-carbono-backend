package com.towers.huellacarbonobackend.service.security.impl;

import com.towers.huellacarbonobackend.dto.UsuarioRegisterDto;
import com.towers.huellacarbonobackend.entity.data.Empresa;
import com.towers.huellacarbonobackend.entity.data.Role;
import com.towers.huellacarbonobackend.entity.data.TokenRestablecimiento;
import com.towers.huellacarbonobackend.entity.data.Usuario;
import com.towers.huellacarbonobackend.exception.DataAccessExceptionImpl;
import com.towers.huellacarbonobackend.mapper.UsuarioMapper;
import com.towers.huellacarbonobackend.repository.TokenRestablecimientoRepository;
import com.towers.huellacarbonobackend.repository.UsuarioRepository;
import com.towers.huellacarbonobackend.service.security.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final TokenRestablecimientoRepository tokenRestablecimientoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        try {
            return usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Username %s no existe en el sistema!", username)));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    public String getUsernameFromSecurityContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        } else {
            return principal.toString();
        }
    }

    @Override
    @Transactional
    public Usuario saveByEmpresa(Empresa empresa, String password) {
        String username = generarUsername(empresa);

        if (usuarioRepository.existsByUsername(username)) {
            throw new DataAccessExceptionImpl("Ya existe un usuario con el username: " + username);
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setEsNuevo(true);
        usuario.setEmpresa(empresa);
        usuario.setEsNuevo(true);

        Role admin = new Role();
        admin.setId(2L);
        usuario.setRole(admin);

        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void save(UsuarioRegisterDto usuarioRegisterDto) {
        Usuario usuario = usuarioMapper.toUsuario(usuarioRegisterDto);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }

    private String generarUsername(Empresa empresa) {
        String letrasEmpresa = empresa.getRazonSocial().substring(0, 2).toUpperCase();
        String letrasRuc = empresa.getRuc().substring(0, 2);
        int anio = Year.now().getValue();
        return letrasEmpresa + letrasRuc + anio;
    }

    @Override
    @Transactional
    public String generateTokenRestablecimiento(Usuario usuario) {
        tokenRestablecimientoRepository.deleteByUsuario(usuario);
        String token = UUID.randomUUID().toString();

        TokenRestablecimiento tokenRestablecimiento = new TokenRestablecimiento();
        tokenRestablecimiento.setToken(token);
        tokenRestablecimiento.setUsuario(usuario);
        tokenRestablecimiento.setFechaExpiracion(LocalDateTime.now().plusHours(24));

        tokenRestablecimientoRepository.save(tokenRestablecimiento);

        return token;
    }

    @Override
    @Transactional
    public boolean updatePassword(String token, String passwordAnterior, String passwordNuevo) {
        Optional<TokenRestablecimiento> tokenOptional = tokenRestablecimientoRepository.findByToken(token);

        if (tokenOptional.isPresent()) {
            TokenRestablecimiento tokenRestablecimiento = tokenOptional.orElseThrow();

            if (tokenRestablecimiento.getFechaExpiracion().isAfter(LocalDateTime.now())) {
                Usuario usuario = tokenRestablecimiento.getUsuario();

                if (!passwordEncoder.matches(passwordAnterior, usuario.getPassword())) {
                    return false;
                }

                usuario.setPassword(passwordEncoder.encode(passwordNuevo));
                usuario.setEsNuevo(false);
                usuarioRepository.save(usuario);

                tokenRestablecimientoRepository.delete(tokenRestablecimiento);

                return true;
            }
        }

        return false;
    }
}
