package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.data.TipoPFC;
import com.towers.huellacarbonobackend.repository.TipoPFCRepository;
import com.towers.huellacarbonobackend.service.data.TipoPFCService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoPFCServiceImpl implements TipoPFCService {
    private final TipoPFCRepository tipoPFCRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoPFC> getAll() {
        return tipoPFCRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoPFC getByNombre(String nombre) {
        return tipoPFCRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Tipo PFC " + nombre + " no encontrado."));
    }
}
