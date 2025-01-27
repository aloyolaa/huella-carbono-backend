package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.Accion;
import com.towers.huellacarbonobackend.repository.AccionRepository;
import com.towers.huellacarbonobackend.service.data.AccionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccionServiceImpl implements AccionService {
    private final AccionRepository accionRepository;

    @Override
    @Transactional(readOnly = true)
    public Accion getByNombre(String nombre) {
        return accionRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Acción " + nombre + " no encontrada."));
    }
}
