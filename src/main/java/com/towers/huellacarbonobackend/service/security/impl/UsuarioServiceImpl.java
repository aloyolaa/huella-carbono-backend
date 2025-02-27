package com.towers.huellacarbonobackend.service.security.impl;

import com.towers.huellacarbonobackend.entity.data.Empresa;
import com.towers.huellacarbonobackend.entity.data.TokenRestablecimiento;
import com.towers.huellacarbonobackend.entity.data.Usuario;
import com.towers.huellacarbonobackend.exception.DataAccessExceptionImpl;
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
    public Usuario crearUsuarioParaEmpresa(Empresa empresa, String password) {
        String username = generarUsername(empresa);

        // Creamos el usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setEsNuevo(true);
        usuario.setEmpresa(empresa);

        return usuarioRepository.save(usuario);
    }

    private String generarUsername(Empresa empresa) {
        String letrasEmpresa = empresa.getRazonSocial().substring(0, 2).toUpperCase();
        String letrasRuc = empresa.getRuc().substring(0, 2);
        int anio = Year.now().getValue();
        return letrasEmpresa + letrasRuc + anio;
    }

    @Override
    @Transactional
    public String generarTokenRestablecimiento(Usuario usuario) {
        tokenRestablecimientoRepository.deleteByUsuario(usuario);

        // Creamos un nuevo token
        String token = UUID.randomUUID().toString();

        TokenRestablecimiento tokenRestablecimiento = new TokenRestablecimiento();
        tokenRestablecimiento.setToken(token);
        tokenRestablecimiento.setUsuario(usuario);
        // El token expirará en 24 horas
        tokenRestablecimiento.setFechaExpiracion(LocalDateTime.now().plusHours(24));

        tokenRestablecimientoRepository.save(tokenRestablecimiento);

        return token;
    }

    @Override
    @Transactional
    public boolean actualizarPassword(String token, String passwordAnterior, String passwordNuevo) {
        Optional<TokenRestablecimiento> tokenOptional = tokenRestablecimientoRepository.findByToken(token);

        if (tokenOptional.isPresent()) {
            TokenRestablecimiento tokenRestablecimiento = tokenOptional.orElseThrow();

            // Verificamos que el token no haya expirado
            if (tokenRestablecimiento.getFechaExpiracion().isAfter(LocalDateTime.now())) {
                Usuario usuario = tokenRestablecimiento.getUsuario();

                if (!passwordEncoder.matches(passwordAnterior, usuario.getPassword())) {
                    return false; // Contraseña antigua incorrecta
                }

                // Actualizamos la contraseña
                usuario.setPassword(passwordEncoder.encode(passwordNuevo));
                // Marcamos que el usuario ya no es nuevo
                usuario.setEsNuevo(false);

                usuarioRepository.save(usuario);

                // Eliminamos el token usado
                tokenRestablecimientoRepository.delete(tokenRestablecimiento);

                return true;
            }
        }

        return false;
    }
}
