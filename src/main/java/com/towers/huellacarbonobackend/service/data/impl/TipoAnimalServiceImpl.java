package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.TipoAnimal;
import com.towers.huellacarbonobackend.repository.TipoAnimalRepository;
import com.towers.huellacarbonobackend.service.data.TipoAnimalService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoAnimalServiceImpl implements TipoAnimalService {
    private final TipoAnimalRepository tipoAnimalRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoAnimal> getAll() {
        return tipoAnimalRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoAnimal getByNombre(String nombre) {
        return tipoAnimalRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de animal " + nombre + " no encontrado."));
    }
}
