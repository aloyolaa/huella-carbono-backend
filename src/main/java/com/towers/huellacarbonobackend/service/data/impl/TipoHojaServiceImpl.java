package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.TipoHoja;
import com.towers.huellacarbonobackend.repository.TipoHojaRepository;
import com.towers.huellacarbonobackend.service.data.TipoHojaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoHojaServiceImpl implements TipoHojaService {
    private final TipoHojaRepository tipoHojaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoHoja> getAll() {
        return tipoHojaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoHoja getTipoHojaByNombre(String nombre) {
        return tipoHojaRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Tipo de hoja " + nombre + " no encontrado."));
    }
}
