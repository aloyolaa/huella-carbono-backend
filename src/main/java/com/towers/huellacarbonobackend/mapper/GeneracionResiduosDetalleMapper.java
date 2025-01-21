package com.towers.huellacarbonobackend.mapper;

import com.towers.huellacarbonobackend.dto.GeneracionResiduosDetalleDto;
import com.towers.huellacarbonobackend.entity.GeneracionResiduos;
import com.towers.huellacarbonobackend.entity.GeneracionResiduosDetalle;
import org.springframework.stereotype.Service;

@Service
public class GeneracionResiduosDetalleMapper {
    public GeneracionResiduosDetalle toGeneracionResiduosDetalle(GeneracionResiduosDetalleDto generacionResiduosDetalleDto, GeneracionResiduos generacionResiduos) {
        GeneracionResiduosDetalle generacionResiduosDetalle = new GeneracionResiduosDetalle();
        generacionResiduosDetalle.setId(generacionResiduosDetalleDto.id());
        generacionResiduosDetalle.setAnio(generacionResiduosDetalleDto.anio());
        generacionResiduosDetalle.setProductosMadera(generacionResiduosDetalleDto.productosMadera());
        generacionResiduosDetalle.setProductosPapel(generacionResiduosDetalleDto.productosPapel());
        generacionResiduosDetalle.setResiduos(generacionResiduosDetalleDto.residuos());
        generacionResiduosDetalle.setTextiles(generacionResiduosDetalleDto.textiles());
        generacionResiduosDetalle.setJardines(generacionResiduosDetalleDto.jardines());
        generacionResiduosDetalle.setPaniales(generacionResiduosDetalleDto.paniales());
        generacionResiduosDetalle.setOtros(generacionResiduosDetalleDto.otros());
        generacionResiduosDetalle.setGeneracionResiduos(generacionResiduos);

        return generacionResiduosDetalle;
    }

    public GeneracionResiduosDetalleDto toGeneracionResiduosDetalleDto(GeneracionResiduosDetalle generacionResiduosDetalle) {
        return new GeneracionResiduosDetalleDto(
                generacionResiduosDetalle.getId(),
                generacionResiduosDetalle.getAnio(),
                generacionResiduosDetalle.getProductosMadera(),
                generacionResiduosDetalle.getProductosPapel(),
                generacionResiduosDetalle.getResiduos(),
                generacionResiduosDetalle.getTextiles(),
                generacionResiduosDetalle.getJardines(),
                generacionResiduosDetalle.getPaniales(),
                generacionResiduosDetalle.getOtros()
        );
    }
}
