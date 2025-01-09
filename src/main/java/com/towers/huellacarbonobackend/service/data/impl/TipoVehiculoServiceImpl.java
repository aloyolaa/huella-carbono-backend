package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.TipoVehiculo;
import com.towers.huellacarbonobackend.repository.TipoVehiculoRepository;
import com.towers.huellacarbonobackend.service.data.TipoVehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoVehiculoServiceImpl implements TipoVehiculoService {
    private final TipoVehiculoRepository tipoVehiculoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoVehiculo> getAll() {
        return tipoVehiculoRepository.findAll();
    }
}
