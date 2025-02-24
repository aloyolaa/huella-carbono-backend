package com.towers.huellacarbonobackend.service.calculate.impl;

import com.towers.huellacarbonobackend.entity.calculate.PotencialCalentamientoGlobal;
import com.towers.huellacarbonobackend.repository.PotencialCalentamientoGlobalRepository;
import com.towers.huellacarbonobackend.service.calculate.PotencialCalentamientoGlobalService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PotencialCalentamientoGlobalServiceImpl implements PotencialCalentamientoGlobalService {
    private final PotencialCalentamientoGlobalRepository potencialCalentamientoGlobalRepository;

    @Override
    @Transactional(readOnly = true)
    public PotencialCalentamientoGlobal getByNombre(String nombre) {
        return potencialCalentamientoGlobalRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("No hay Potencial de Calentamiento global registrado para " + nombre));
    }
}
