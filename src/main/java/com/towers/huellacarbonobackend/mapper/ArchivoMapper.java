package com.towers.huellacarbonobackend.mapper;

import com.towers.huellacarbonobackend.dto.ArchivoDto;
import com.towers.huellacarbonobackend.entity.Archivo;
import org.springframework.stereotype.Service;

@Service
public class ArchivoMapper {

    public ArchivoDto toArchivoDto(Archivo archivo) {
        return new ArchivoDto(
                archivo.getId(),
                archivo.getClave(),
                archivo.getNombre(),
                archivo.getAlcance().getId(),
                archivo.getAlcance().getNombre()
        );
    }
}
