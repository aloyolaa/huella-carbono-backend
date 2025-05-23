package com.towers.huellacarbonobackend.mapper;

import com.towers.huellacarbonobackend.dto.*;
import com.towers.huellacarbonobackend.entity.data.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetalleMapper {
    private final GeneracionResiduosMapper generacionResiduosMapper;

    public Detalle toDetalle(DetalleDto detalleDto, DatosGenerales datosGenerales) {
        Detalle detalle = new Detalle();
        detalle.setId(detalleDto.id());
        detalle.setArea(detalleDto.area() != null ? detalleDto.area() : null);
        detalle.setSuministro(detalleDto.suministro() != null ? detalleDto.suministro() : null);
        detalle.setSuperficie(detalleDto.superficie() != null ? detalleDto.superficie() : null);
        detalle.setMedidor(detalleDto.medidor() != null ? detalleDto.medidor() : null);
        detalle.setDescripcion(detalleDto.descripcion() != null ? detalleDto.descripcion() : null);
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
        detalle.setTransporteMaterial(
                detalleDto.transporteMaterial() != null ?
                        new TransporteMaterial(
                                detalleDto.transporteMaterial().id(),
                                detalleDto.transporteMaterial().descripcion(),
                                detalleDto.transporteMaterial().viajes(),
                                detalleDto.transporteMaterial().tramo(),
                                detalleDto.transporteMaterial().pesoTransportado(),
                                detalleDto.transporteMaterial().distanciaRecorrida(),
                                new TipoVehiculo(detalleDto.transporteMaterial().tipoVehiculo())
                        ) : null
        );
        detalle.setTransporteVehiculo(
                detalleDto.transporteVehiculo() != null ?
                        new TransporteVehiculo(
                                detalleDto.transporteVehiculo().id(),
                                detalleDto.transporteVehiculo().tramo(),
                                detalleDto.transporteVehiculo().distanciaRecorrida(),
                                detalleDto.transporteVehiculo().personasViajando(),
                                detalleDto.transporteVehiculo().vecesRecorrido(),
                                detalleDto.transporteVehiculo().tipoTransporte() != null ? new TipoTransporte(detalleDto.transporteVehiculo().tipoTransporte()) : null
                        ) : null
        );
        detalle.setConsumoPapel(
                detalleDto.consumoPapel() != null ?
                        new ConsumoPapel(
                                detalleDto.consumoPapel().id(),
                                new TipoHoja(detalleDto.consumoPapel().tipoHoja()),
                                detalleDto.consumoPapel().comprasAnuales(),
                                detalleDto.consumoPapel().unidad(),
                                detalleDto.consumoPapel().reciclado(),
                                detalleDto.consumoPapel().certificado(),
                                detalleDto.consumoPapel().densidad()
                        ) : null
        );
        detalle.setGeneracionIndirectaNF3(
                detalleDto.generacionIndirectaNF3() != null ?
                        new GeneracionIndirectaNF3(
                                detalleDto.generacionIndirectaNF3().id(),
                                detalleDto.generacionIndirectaNF3().numeroPantallas(),
                                detalleDto.generacionIndirectaNF3().alto(),
                                detalleDto.generacionIndirectaNF3().ancho()
                        ) : null
        );
        detalle.setTransporteCasaTrabajos(
                detalleDto.transporteCasaTrabajos() != null ?
                        detalleDto.transporteCasaTrabajos().stream().map(transporteCasaTrabajoDto -> {
                            TransporteCasaTrabajo transporteCasaTrabajo = new TransporteCasaTrabajo();
                            transporteCasaTrabajo.setId(transporteCasaTrabajoDto.id());
                            transporteCasaTrabajo.setDescripcionPersonal(transporteCasaTrabajoDto.descripcionPersonal());
                            transporteCasaTrabajo.setTrabajadores(transporteCasaTrabajoDto.trabajadores());
                            transporteCasaTrabajo.setViajesSemanales(transporteCasaTrabajoDto.viajesSemanales());
                            transporteCasaTrabajo.setDiasLaborales(transporteCasaTrabajoDto.diasLaborales());
                            transporteCasaTrabajo.setDistanciaViaje(transporteCasaTrabajoDto.distanciaViaje());
                            transporteCasaTrabajo.setTipoMovilidad(new TipoMovilidad(transporteCasaTrabajoDto.tipoMovilidad()));
                            transporteCasaTrabajo.setDetalle(detalle);
                            return transporteCasaTrabajo;
                        }).toList() : null
        );
        detalle.setGeneracionResiduos(
                detalleDto.generacionResiduos() != null ?
                        generacionResiduosMapper.toGeneracionResiduos(detalleDto.generacionResiduos()) : null
        );
        return detalle;
    }

    public DetalleDto toDetalleDto(Detalle detalle) {
        return new DetalleDto(
                detalle.getId(),
                detalle.getArea(),
                detalle.getSuministro(),
                detalle.getSuperficie(),
                detalle.getMedidor(),
                detalle.getDescripcion(),
                detalle.getTipoCombustible() != null ? detalle.getTipoCombustible().getId() : null,
                detalle.getMeses() != null ? new MesesDto(
                        detalle.getMeses().getId(),
                        detalle.getMeses().getEnero(),
                        detalle.getMeses().getFebrero(),
                        detalle.getMeses().getMarzo(),
                        detalle.getMeses().getAbril(),
                        detalle.getMeses().getMayo(),
                        detalle.getMeses().getJunio(),
                        detalle.getMeses().getJulio(),
                        detalle.getMeses().getAgosto(),
                        detalle.getMeses().getSeptiembre(),
                        detalle.getMeses().getOctubre(),
                        detalle.getMeses().getNoviembre(),
                        detalle.getMeses().getDiciembre()
                ) : null,
                detalle.getCategoriaInstitucion() != null ? detalle.getCategoriaInstitucion().getId() : null,
                detalle.getActividad() != null ? detalle.getActividad().getId() : null,
                detalle.getClinker() != null ? new ClinkerDto(
                        detalle.getClinker().getId(),
                        detalle.getClinker().getCemento(),
                        detalle.getClinker().getProduccionCemento(),
                        detalle.getClinker().getProduccionClinker(),
                        detalle.getClinker().getContenidoCaOClinker(),
                        detalle.getClinker().getContenidoCaOCaCO3()
                ) : null,
                detalle.getRefrigeranteInstalacion() != null || detalle.getRefrigeranteOperacion() != null || detalle.getRefrigeranteDisposicion() != null ? new RefrigeranteDto(
                        detalle.getRefrigeranteInstalacion() != null ? new RefrigeranteInstalacionDto(
                                detalle.getRefrigeranteInstalacion().getId(),
                                detalle.getRefrigeranteInstalacion().getTipoEquipo().getId(),
                                detalle.getRefrigeranteInstalacion().getTipoRefrigerante().getId(),
                                detalle.getRefrigeranteInstalacion().getNumeroEquipos(),
                                detalle.getRefrigeranteInstalacion().getCapacidadCarga(),
                                detalle.getRefrigeranteInstalacion().getFugaInstalacion()
                        ) : null,
                        detalle.getRefrigeranteOperacion() != null ? new RefrigeranteOperacionDto(
                                detalle.getRefrigeranteOperacion().getId(),
                                detalle.getRefrigeranteOperacion().getTipoEquipo().getId(),
                                detalle.getRefrigeranteOperacion().getTipoRefrigerante().getId(),
                                detalle.getRefrigeranteOperacion().getNumeroEquipos(),
                                detalle.getRefrigeranteOperacion().getCapacidadCarga(),
                                detalle.getRefrigeranteOperacion().getAnio(),
                                detalle.getRefrigeranteOperacion().getFugaUso()
                        ) : null,
                        detalle.getRefrigeranteDisposicion() != null ? new RefrigeranteDisposicionDto(
                                detalle.getRefrigeranteDisposicion().getId(),
                                detalle.getRefrigeranteDisposicion().getTipoEquipo().getId(),
                                detalle.getRefrigeranteDisposicion().getTipoRefrigerante().getId(),
                                detalle.getRefrigeranteDisposicion().getNumeroEquipos(),
                                detalle.getRefrigeranteDisposicion().getCapacidadCarga(),
                                detalle.getRefrigeranteDisposicion().getFraccionRefrigeranteDisposicion(),
                                detalle.getRefrigeranteDisposicion().getFraccionRefrigeranteRecuperado()
                        ) : null
                ) : null,
                detalle.getFugaInstalacion() != null || detalle.getFugaOperacion() != null || detalle.getFugaDisposicion() != null ? new FugaDto(
                        detalle.getFugaInstalacion() != null ? new FugaInstalacionDto(
                                detalle.getFugaInstalacion().getId(),
                                detalle.getFugaInstalacion().getDescripcionEquipo(),
                                detalle.getFugaInstalacion().getNumeroEquipos(),
                                detalle.getFugaInstalacion().getCapacidadCarga(),
                                detalle.getFugaInstalacion().getFugaInstalacion()
                        ) : null,
                        detalle.getFugaOperacion() != null ? new FugaOperacionDto(
                                detalle.getFugaOperacion().getId(),
                                detalle.getFugaOperacion().getDescripcionEquipo(),
                                detalle.getFugaOperacion().getNumeroEquipos(),
                                detalle.getFugaOperacion().getCapacidadCarga(),
                                detalle.getFugaOperacion().getTiempoUso(),
                                detalle.getFugaOperacion().getFugaUso()
                        ) : null,
                        detalle.getFugaDisposicion() != null ? new FugaDisposicionDto(
                                detalle.getFugaDisposicion().getId(),
                                detalle.getFugaDisposicion().getDescripcionEquipo(),
                                detalle.getFugaDisposicion().getNumeroEquipos(),
                                detalle.getFugaDisposicion().getCapacidadCarga(),
                                detalle.getFugaDisposicion().getFraccionSF6Disposicion(),
                                detalle.getFugaDisposicion().getFraccionSF6Recuperado()
                        ) : null
                ) : null,
                detalle.getPfcInstalacion() != null || detalle.getPfcOperacion() != null || detalle.getPfcDisposicion() != null ? new PFCDto(
                        detalle.getPfcInstalacion() != null ? new PFCInstalacionDto(
                                detalle.getPfcInstalacion().getId(),
                                detalle.getPfcInstalacion().getDescripcionEquipo(),
                                detalle.getPfcInstalacion().getTipoPFC().getId(),
                                detalle.getPfcInstalacion().getNumeroEquipos(),
                                detalle.getPfcInstalacion().getCapacidadCarga(),
                                detalle.getPfcInstalacion().getFugaInstalacion()
                        ) : null,
                        detalle.getPfcOperacion() != null ? new PFCOperacionDto(
                                detalle.getPfcOperacion().getId(),
                                detalle.getPfcOperacion().getDescripcionEquipo(),
                                detalle.getPfcOperacion().getTipoPFC().getId(),
                                detalle.getPfcOperacion().getNumeroEquipos(),
                                detalle.getPfcOperacion().getCapacidadCarga(),
                                detalle.getPfcOperacion().getTiempoUso(),
                                detalle.getPfcOperacion().getFugaUso()
                        ) : null,
                        detalle.getPfcDisposicion() != null ? new PFCDisposicionDto(
                                detalle.getPfcDisposicion().getId(),
                                detalle.getPfcDisposicion().getDescripcionEquipo(),
                                detalle.getPfcDisposicion().getTipoPFC().getId(),
                                detalle.getPfcDisposicion().getNumeroEquipos(),
                                detalle.getPfcDisposicion().getCapacidadCarga(),
                                detalle.getPfcDisposicion().getFraccionGasPFCDisposicion(),
                                detalle.getPfcDisposicion().getFraccionGasPFCRecuperado()
                        ) : null
                ) : null,
                detalle.getGanado() != null ? new GanadoDto(
                        detalle.getGanado().getId(),
                        detalle.getGanado().getTipoAnimal().getId(),
                        detalle.getGanado().getTipoTratamiento().getId(),
                        detalle.getGanado().getPesoPromedioAnimal(),
                        detalle.getGanado().getCantidadAnualAnimales()
                ) : null,
                detalle.getFertilizante() != null ? new FertilizanteDto(
                        detalle.getFertilizante().getId(),
                        detalle.getFertilizante().getTipoFertilizante().getId(),
                        detalle.getFertilizante().getResiduo().getId(),
                        detalle.getFertilizante().getContenidoNitrogeno(),
                        detalle.getFertilizante().getCantidadEmpleada()
                ) : null,
                detalle.getEncalado() != null ? new EncaladoDto(
                        detalle.getEncalado().getId(),
                        detalle.getEncalado().getTipoCal().getId(),
                        detalle.getEncalado().getCantidadAplicada()
                ) : null,
                detalle.getSueloGestionado() != null ? new SueloGestionadoDto(
                        detalle.getSueloGestionado().getId(),
                        detalle.getSueloGestionado().getTipoSuelo().getId(),
                        detalle.getSueloGestionado().getAreaGestionada()
                ) : null,
                detalle.getCultivoArroz() != null ? new CultivoArrozDto(
                        detalle.getCultivoArroz().getId(),
                        detalle.getCultivoArroz().getTipoCultivo().getId(),
                        detalle.getCultivoArroz().getPeriodoCultivo(),
                        detalle.getCultivoArroz().getAreaCultivo(),
                        detalle.getCultivoArroz().getTipoFertilizante().getId(),
                        detalle.getCultivoArroz().getResiduo().getId(),
                        detalle.getCultivoArroz().getContenidoNitrogeno(),
                        detalle.getCultivoArroz().getCantidadEmpleada()
                ) : null,
                detalle.getQuemaBiomasa() != null ? new QuemaBiomadaDto(
                        detalle.getQuemaBiomasa().getId(),
                        detalle.getQuemaBiomasa().getResiduoAgricola().getId(),
                        detalle.getQuemaBiomasa().getAreaCultiva(),
                        detalle.getQuemaBiomasa().getAreaQuemada(),
                        detalle.getQuemaBiomasa().getProduccion()
                ) : null,
                detalle.getEmbalse() != null ? new EmbalseDto(
                        detalle.getEmbalse().getId(),
                        detalle.getEmbalse().getNombre(),
                        detalle.getEmbalse().getUbicacion(),
                        detalle.getEmbalse().getZona().getId(),
                        detalle.getEmbalse().getArea(),
                        detalle.getEmbalse().getPeriodoLibreHielo(),
                        detalle.getEmbalse().getFraccionAreaInundada()
                ) : null,
                detalle.getTransporteMaterial() != null ? new TransporteMaterialDto(
                        detalle.getTransporteMaterial().getId(),
                        detalle.getTransporteMaterial().getDescripcion(),
                        detalle.getTransporteMaterial().getViajes(),
                        detalle.getTransporteMaterial().getTramo(),
                        detalle.getTransporteMaterial().getPesoTransportado(),
                        detalle.getTransporteMaterial().getDistanciaRecorrida(),
                        detalle.getTransporteMaterial().getTipoVehiculo().getId()
                ) : null,
                detalle.getTransporteVehiculo() != null ? new TransporteVehiculoDto(
                        detalle.getTransporteVehiculo().getId(),
                        detalle.getTransporteVehiculo().getTramo(),
                        detalle.getTransporteVehiculo().getDistanciaRecorrida(),
                        detalle.getTransporteVehiculo().getPersonasViajando(),
                        detalle.getTransporteVehiculo().getVecesRecorrido(),
                        detalle.getTransporteVehiculo().getTipoTransporte() != null ? detalle.getTransporteVehiculo().getTipoTransporte().getId() : null
                ) : null,
                detalle.getConsumoPapel() != null ? new ConsumoPapelDto(
                        detalle.getConsumoPapel().getId(),
                        detalle.getConsumoPapel().getTipoHoja().getId(),
                        detalle.getConsumoPapel().getComprasAnuales(),
                        detalle.getConsumoPapel().getUnidad(),
                        detalle.getConsumoPapel().getReciclado(),
                        detalle.getConsumoPapel().getCertificado(),
                        detalle.getConsumoPapel().getDensidad()
                ) : null,
                detalle.getGeneracionIndirectaNF3() != null ? new GeneracionIndirectaNF3Dto(
                        detalle.getGeneracionIndirectaNF3().getId(),
                        detalle.getGeneracionIndirectaNF3().getNumeroPantallas(),
                        detalle.getGeneracionIndirectaNF3().getAlto(),
                        detalle.getGeneracionIndirectaNF3().getAncho()
                ) : null,
                detalle.getGeneracionResiduos() != null ? generacionResiduosMapper.toGeneracionResiduosDto(detalle.getGeneracionResiduos()) : null,
                detalle.getTransporteCasaTrabajos() != null ? detalle.getTransporteCasaTrabajos().stream().map(transporteCasaTrabajo -> new TransporteCasaTrabajoDto(
                        transporteCasaTrabajo.getId(),
                        transporteCasaTrabajo.getDescripcionPersonal(),
                        transporteCasaTrabajo.getTrabajadores(),
                        transporteCasaTrabajo.getViajesSemanales(),
                        transporteCasaTrabajo.getDiasLaborales(),
                        transporteCasaTrabajo.getDistanciaViaje(),
                        transporteCasaTrabajo.getTipoMovilidad().getId()
                )).toList() : null
        );
    }
}
