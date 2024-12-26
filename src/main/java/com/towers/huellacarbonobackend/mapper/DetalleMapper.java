package com.towers.huellacarbonobackend.mapper;

import com.towers.huellacarbonobackend.dto.DetalleDto;
import com.towers.huellacarbonobackend.entity.*;
import org.springframework.stereotype.Service;

@Service
public class DetalleMapper {
    public Detalle toDetalle(DetalleDto detalleDto, DatosGenerales datosGenerales) {
        Detalle detalle = new Detalle();
        detalle.setId(detalleDto.id());
        detalle.setDatosGenerales(datosGenerales);
        detalle.setMeses(
                new Meses(
                        detalleDto.meses().id(),
                        detalleDto.meses().enero(),
                        detalleDto.meses().febrero(),
                        detalleDto.meses().marzo(),
                        detalleDto.meses().abril(),
                        detalleDto.meses().mayo(),
                        detalleDto.meses().junio(),
                        detalleDto.meses().julio(),
                        detalleDto.meses().agosto(),
                        detalleDto.meses().septiembre(),
                        detalleDto.meses().octubre(),
                        detalleDto.meses().noviembre(),
                        detalleDto.meses().diciembre()
                ));
        detalle.setTipoCombustible(detalleDto.tipoCombustible() != null ? new TipoCombustible(detalleDto.tipoCombustible(), null) : null);
        detalle.setUnidad(detalleDto.unidad() != null ? new Unidad(detalleDto.unidad(), null) : null);
        //detalle.setAccion(detalleDto.accion() != null ? new Accion(detalleDto.accion(), null) : null);
        //detalle.setActividad(detalleDto.actividad() != null ? new Actividad(detalleDto.actividad(), null) : null);
        detalle.setCategoriaInstitucion(detalleDto.categoriaInstitucion() != null ? new CategoriaInstitucion(detalleDto.categoriaInstitucion(), null) : null);
        //detalle.setProduccion(detalleDto.produccion() != null ? new Produccion(detalleDto.produccion(), null) : null);
        return detalle;
    }
}
