package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.ResiduoAgricola;
import com.towers.huellacarbonobackend.repository.ResiduoAgricolaRepository;
import com.towers.huellacarbonobackend.service.data.ResiduoAgricolaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResiduoAgricolaServiceImpl implements ResiduoAgricolaService {
    private final ResiduoAgricolaRepository residuoAgricolaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ResiduoAgricola> getAll() {
        return residuoAgricolaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ResiduoAgricola getByNombre(String nombre) {
        return residuoAgricolaRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Residuo agricola " + nombre + " no encontrado."));
    }
}
