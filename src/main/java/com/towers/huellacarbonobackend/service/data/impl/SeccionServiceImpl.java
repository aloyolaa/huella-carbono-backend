package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.data.Seccion;
import com.towers.huellacarbonobackend.repository.SeccionRepository;
import com.towers.huellacarbonobackend.service.data.SeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeccionServiceImpl implements SeccionService {
    private final SeccionRepository seccionRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Seccion> getOptionalByNombre(String nombre) {
        return seccionRepository.findByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Seccion> getOptionalByNombreContains(String nombre) {
        return seccionRepository.findByNombreContains(nombre);
    }
}
