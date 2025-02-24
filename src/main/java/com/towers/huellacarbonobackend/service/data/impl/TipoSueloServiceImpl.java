package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.data.TipoSuelo;
import com.towers.huellacarbonobackend.repository.TipoSueloRepository;
import com.towers.huellacarbonobackend.service.data.TipoSueloService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoSueloServiceImpl implements TipoSueloService {
    private final TipoSueloRepository tipoSueloRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoSuelo> getAll() {
        return tipoSueloRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoSuelo getByNombre(String nombre) {
        return tipoSueloRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de suelo " + nombre + " no encontrado."));
    }
}
