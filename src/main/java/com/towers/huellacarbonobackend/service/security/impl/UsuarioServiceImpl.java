package com.towers.huellacarbonobackend.service.security.impl;

import com.towers.huellacarbonobackend.entity.Usuario;
import com.towers.huellacarbonobackend.exception.DataAccessExceptionImpl;
import com.towers.huellacarbonobackend.repository.UsuarioRepository;
import com.towers.huellacarbonobackend.service.security.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
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
}
