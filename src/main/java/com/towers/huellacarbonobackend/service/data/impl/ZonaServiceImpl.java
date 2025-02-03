package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.Zona;
import com.towers.huellacarbonobackend.repository.ZonaRepository;
import com.towers.huellacarbonobackend.service.data.ZonaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZonaServiceImpl implements ZonaService {
    private final ZonaRepository zonaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Zona> getAll() {
        return zonaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Zona getByNombre(String nombre) {
        return zonaRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Zona " + nombre + " no encontrada."));
    }
}
