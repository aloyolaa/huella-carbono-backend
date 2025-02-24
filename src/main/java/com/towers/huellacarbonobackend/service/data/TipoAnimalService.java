package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.data.TipoAnimal;

import java.util.List;

public interface TipoAnimalService {
    List<TipoAnimal> getAll();

    TipoAnimal getByNombre(String nombre);
}
