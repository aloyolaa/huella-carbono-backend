package com.towers.huellacarbonobackend.mapper;

import com.towers.huellacarbonobackend.dto.DataDto;
import com.towers.huellacarbonobackend.entity.Archivo;
import com.towers.huellacarbonobackend.entity.DatosGenerales;
import com.towers.huellacarbonobackend.entity.Empresa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataMapper {
    private final DetalleMapper detalleMapper;

    public DatosGenerales toDatosGenerales(DataDto dataDto, Long empresa, Long archivo) {
        DatosGenerales datosGenerales = new DatosGenerales();
        datosGenerales.setId(dataDto.id());
        datosGenerales.setNombre(dataDto.nombre());
        datosGenerales.setCargo(dataDto.cargo());
        datosGenerales.setCorreo(dataDto.correo());
        datosGenerales.setLocacion(dataDto.locacion());
        datosGenerales.setComentarios(dataDto.comentarios());
        datosGenerales.setArchivo(new Archivo(archivo, null, null));
        datosGenerales.setEmpresa(new Empresa(empresa, null));
        datosGenerales.setDetalles(dataDto.detalles().stream().map(d -> detalleMapper.toDetalle(d, datosGenerales)).toList());

        return datosGenerales;
    }
}
