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
        detalle.setFugaInstalacion(
                detalleDto.fuga() != null && detalleDto.fuga().instalacion() != null ?
                        new FugaInstalacion(
                                detalleDto.fuga().instalacion().id(),
                                detalleDto.fuga().instalacion().descripcionEquipo(),
                                detalleDto.fuga().instalacion().numeroEquipos(),
                                detalleDto.fuga().instalacion().capacidadCarga(),
                                detalleDto.fuga().instalacion().fugaInstalacion()
                        ) : null
        );
        detalle.setFugaOperacion(
                detalleDto.fuga() != null && detalleDto.fuga().operacion() != null ?
                        new FugaOperacion(
                                detalleDto.fuga().operacion().id(),
                                detalleDto.fuga().operacion().descripcionEquipo(),
                                detalleDto.fuga().operacion().numeroEquipos(),
                                detalleDto.fuga().operacion().capacidadCarga(),
                                detalleDto.fuga().operacion().tiempoUso(),
                                detalleDto.fuga().operacion().fugaUso()
                        ) : null
        );
        detalle.setFugaDisposicion(
                detalleDto.fuga() != null && detalleDto.fuga().disposicion() != null ?
                        new FugaDisposicion(
                                detalleDto.fuga().disposicion().id(),
                                detalleDto.fuga().disposicion().descripcionEquipo(),
                                detalleDto.fuga().disposicion().numeroEquipos(),
                                detalleDto.fuga().disposicion().capacidadCarga(),
                                detalleDto.fuga().disposicion().fraccionSF6Disposicion(),
                                detalleDto.fuga().disposicion().fraccionSF6Recuperado()
                        ) : null
        );
        detalle.setPfcInstalacion(
                detalleDto.pfc() != null && detalleDto.pfc().instalacion() != null ?
                        new PFCInstalacion(
                                detalleDto.pfc().instalacion().id(),
                                detalleDto.pfc().instalacion().descripcionEquipo(),
                                new TipoPFC(detalleDto.pfc().instalacion().tipoPFC()),
                                detalleDto.pfc().instalacion().numeroEquipos(),
                                detalleDto.pfc().instalacion().capacidadCarga(),
                                detalleDto.pfc().instalacion().fugaInstalacion()
                        ) : null
        );
        detalle.setPfcOperacion(
                detalleDto.pfc() != null && detalleDto.pfc().operacion() != null ?
                        new PFCOperacion(
                                detalleDto.pfc().operacion().id(),
                                detalleDto.pfc().operacion().descripcionEquipo(),
                                new TipoPFC(detalleDto.pfc().operacion().tipoPFC()),
                                detalleDto.pfc().operacion().numeroEquipos(),
                                detalleDto.pfc().operacion().capacidadCarga(),
                                detalleDto.pfc().operacion().tiempoUso(),
                                detalleDto.pfc().operacion().fugaUso()
                        ) : null
        );
        detalle.setPfcDisposicion(
                detalleDto.pfc() != null && detalleDto.pfc().disposicion() != null ?
                        new PFCDisposicion(
                                detalleDto.pfc().disposicion().id(),
                                detalleDto.pfc().disposicion().descripcionEquipo(),
                                new TipoPFC(detalleDto.pfc().disposicion().tipoPFC()),
                                detalleDto.pfc().disposicion().numeroEquipos(),
                                detalleDto.pfc().disposicion().capacidadCarga(),
                                detalleDto.pfc().disposicion().fraccionGasPFCDisposicion(),
                                detalleDto.pfc().disposicion().fraccionGasPFCRecuperado()
                        ) : null
        );
        detalle.setGanado(
                detalleDto.ganado() != null ?
                        new Ganado(
                                detalleDto.ganado().id(),
                                new TipoAnimal(detalleDto.ganado().tipoAnimal()),
                                new TipoTratamiento(detalleDto.ganado().tipoTratamiento()),
                                detalleDto.ganado().pesoPromedioAnimal(),
                                detalleDto.ganado().cantidadAnualAnimales()
                        ) : null
        );
        detalle.setFertilizante(
                detalleDto.fertilizante() != null ?
                        new Fertilizante(
                                detalleDto.fertilizante().id(),
                                new TipoFertilizante(detalleDto.fertilizante().tipoFertilizante()),
                                new Residuo(detalleDto.fertilizante().residuo()),
                                detalleDto.fertilizante().contenidoNitrogeno(),
                                detalleDto.fertilizante().cantidadEmpleada()
                        ) : null
        );
        detalle.setEncalado(
                detalleDto.encalado() != null ?
                        new Encalado(
                                detalleDto.encalado().id(),
                                new TipoCal(detalleDto.encalado().tipoCal()),
                                detalleDto.encalado().cantidadAplicada()
                        ) : null
        );
        detalle.setSueloGestionado(
                detalleDto.sueloGestionado() != null ?
                        new SueloGestionado(
                                detalleDto.sueloGestionado().id(),
                                new TipoSuelo(detalleDto.sueloGestionado().tipoSuelo()),
                                detalleDto.sueloGestionado().areaGestionada()
                        ) : null
        );
        detalle.setCultivoArroz(
                detalleDto.cultivoArroz() != null ?
                        new CultivoArroz(
                                detalleDto.cultivoArroz().id(),
                                new TipoCultivo(detalleDto.cultivoArroz().tipoCultivo()),
                                detalleDto.cultivoArroz().periodoCultivo(),
                                detalleDto.cultivoArroz().areaCultivo(),
                                new TipoFertilizante(detalleDto.cultivoArroz().tipoFertilizante()),
                                new Residuo(detalleDto.cultivoArroz().residuo()),
                                detalleDto.cultivoArroz().contenidoNitrogeno(),
                                detalleDto.cultivoArroz().cantidadEmpleada()
                        ) : null
        );
        detalle.setQuemaBiomasa(
                detalleDto.quemaBiomasa() != null ?
                        new QuemaBiomasa(
                                detalleDto.quemaBiomasa().id(),
                                new ResiduoAgricola(detalleDto.quemaBiomasa().residuoAgricola()),
                                detalleDto.quemaBiomasa().areaCultiva(),
                                detalleDto.quemaBiomasa().areaQuemada(),
                                detalleDto.quemaBiomasa().produccion()
                        ) : null
        );
        detalle.setEmbalse(
                detalleDto.embalse() != null ?
                        new Embalse(
                                detalleDto.embalse().id(),
                                detalleDto.embalse().nombre(),
                                detalleDto.embalse().ubicacion(),
                                new Zona(detalleDto.embalse().zona()),
                                detalleDto.embalse().area(),
                                detalleDto.embalse().periodoLibreHielo(),
                                detalleDto.embalse().fraccionAreaInundada()
                        ) : null
        );
        detalle.setArea(detalleDto.area() != null ? detalleDto.area() : null);
        detalle.setSuministro(detalleDto.suministro() != null ? detalleDto.suministro() : null);
        detalle.setDescripcion(detalleDto.descripcion() != null ? detalleDto.descripcion() : null);
        return detalle;
    }
}
