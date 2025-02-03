package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.TipoCultivo;
import com.towers.huellacarbonobackend.repository.TipoCultivoRepository;
import com.towers.huellacarbonobackend.service.data.TipoCultivoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoCultivoServiceImpl implements TipoCultivoService {
    private final TipoCultivoRepository tipoCultivoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoCultivo> getAll() {
        return tipoCultivoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoCultivo getByNombre(String nombre) {
        return tipoCultivoRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de cultivo " + nombre + " no encontrado."));
    }
}
