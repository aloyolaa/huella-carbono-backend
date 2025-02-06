package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.entity.CondicionSEDS;

import java.util.List;

public interface CondicionSEDSService {
    List<CondicionSEDS> getAll();

    CondicionSEDS getByNombre(String nombre);
}
