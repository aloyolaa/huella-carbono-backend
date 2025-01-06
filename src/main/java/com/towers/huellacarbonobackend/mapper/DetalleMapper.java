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
                detalleDto.meses() != null ?
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
                        ) : null
        );
        detalle.setTipoCombustible(detalleDto.tipoCombustible() != null ? new TipoCombustible(detalleDto.tipoCombustible()) : null);
        detalle.setCategoriaInstitucion(detalleDto.categoriaInstitucion() != null ? new CategoriaInstitucion(detalleDto.categoriaInstitucion()) : null);
        detalle.setActividad(detalleDto.actividad() != null ? new Actividad(detalleDto.actividad()) : null);
        detalle.setClinker(
                detalleDto.clinker() != null ?
                        new Clinker(
                                detalleDto.clinker().id(),
                                detalleDto.clinker().cemento(),
                                detalleDto.clinker().produccionCemento(),
                                detalleDto.clinker().produccionClinker(),
                                detalleDto.clinker().contenidoCaOClinker(),
                                detalleDto.clinker().contenidoCaOCaCO3()
                        ) : null
        );
        detalle.setRefrigeranteInstalacion(
                detalleDto.refrigerante() != null && detalleDto.refrigerante().instalacion() != null ?
                        new RefrigeranteInstalacion(
                                detalleDto.refrigerante().instalacion().id(),
                                new TipoEquipo(detalleDto.refrigerante().instalacion().tipoEquipo()),
                                new TipoRefrigerante(detalleDto.refrigerante().instalacion().tipoRefrigerante()),
                                detalleDto.refrigerante().instalacion().numeroEquipos(),
                                detalleDto.refrigerante().instalacion().capacidadCarga(),
                                detalleDto.refrigerante().instalacion().fugaInstalacion()
                        ) : null
        );
        detalle.setRefrigeranteOperacion(
                detalleDto.refrigerante() != null && detalleDto.refrigerante().operacion() != null ?
                        new RefrigeranteOperacion(
                                detalleDto.refrigerante().operacion().id(),
                                new TipoEquipo(detalleDto.refrigerante().operacion().tipoEquipo()),
                                new TipoRefrigerante(detalleDto.refrigerante().operacion().tipoRefrigerante()),
                                detalleDto.refrigerante().operacion().numeroEquipos(),
                                detalleDto.refrigerante().operacion().capacidadCarga(),
                                detalleDto.refrigerante().operacion().anio(),
                                detalleDto.refrigerante().operacion().fugaUso()
                        ) : null
        );
        detalle.setRefrigeranteDisposicion(
                detalleDto.refrigerante() != null && detalleDto.refrigerante().disposicion() != null ?
                        new RefrigeranteDisposicion(
                                detalleDto.refrigerante().disposicion().id(),
                                new TipoEquipo(detalleDto.refrigerante().disposicion().tipoEquipo()),
                                new TipoRefrigerante(detalleDto.refrigerante().disposicion().tipoRefrigerante()),
                                detalleDto.refrigerante().disposicion().numeroEquipos(),
                                detalleDto.refrigerante().disposicion().capacidadCarga(),
                                detalleDto.refrigerante().disposicion().fraccionRefrigeranteDisposicion(),
                                detalleDto.refrigerante().disposicion().fraccionRefrigeranteRecuperacion()
                        ) : null
        );
        return detalle;
    }
}
