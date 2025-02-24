package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.data.CondicionSEDS;
import com.towers.huellacarbonobackend.repository.CondicionSEDSRepository;
import com.towers.huellacarbonobackend.service.data.CondicionSEDSService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CondicionSEDSServiceImpl implements CondicionSEDSService {
    private final CondicionSEDSRepository condicionSEDSRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CondicionSEDS> getAll() {
        return condicionSEDSRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CondicionSEDS getByNombre(String nombre) {
        return condicionSEDSRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Condición SEDS " + nombre + " no encontrada."));
    }
}
