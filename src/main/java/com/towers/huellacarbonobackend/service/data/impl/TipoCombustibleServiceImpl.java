package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.dto.TipoCombustibleDto;
import com.towers.huellacarbonobackend.entity.data.TipoCombustible;
import com.towers.huellacarbonobackend.mapper.TipoCombustibleMapper;
import com.towers.huellacarbonobackend.repository.TipoCombustibleRepository;
import com.towers.huellacarbonobackend.service.data.TipoCombustibleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoCombustibleServiceImpl implements TipoCombustibleService {
    private final TipoCombustibleRepository tipoCombustibleRepository;
    private final TipoCombustibleMapper tipoCombustibleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TipoCombustibleDto> getAllByArchivo(Long archivoId) {
        archivoId = archivoId == 2 || archivoId == 21 ? 1 : archivoId;
        return tipoCombustibleRepository.findByArchivo(archivoId).stream().map(tipoCombustibleMapper::toTipoCombustibleDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoCombustible getByNombreAndArchivo(String nombre, Long archivoId) {
        archivoId = archivoId == 2 || archivoId == 21 ? 1 : archivoId;
        return tipoCombustibleRepository.findByNombreAndArchivo(nombre, archivoId)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de combustible " + nombre + " no encontrado."));
    }

    @Override
    @Transactional(readOnly = true)
    public TipoCombustible getByNombreAndArchivoAndSeccion(String nombre, Long archivoId, Long seccionId) {
        return tipoCombustibleRepository.findByNombreAndArchivoAndSeccion(nombre, archivoId, seccionId)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de combustible " + nombre + " no encontrado."));
    }
}
