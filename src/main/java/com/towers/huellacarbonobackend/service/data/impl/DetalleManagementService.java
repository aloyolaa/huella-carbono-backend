package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.entity.data.TransporteCasaTrabajo;
import com.towers.huellacarbonobackend.exception.DataServiceException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DetalleManagementService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void updateDetalles(DatosGenerales existingDatosGenerales, List<Detalle> newDetalles) {
        if (newDetalles == null || newDetalles.isEmpty()) {
            log.debug("No detalles to update");
            return;
        }

        try {
            log.info("Recorriendo detalles para actualizar");
            for (Detalle newDetalle : newDetalles) {
                if (newDetalle.getId() != null) {
                    log.info("Actualizando");
                    updateExistingDetalle(existingDatosGenerales, newDetalle);
                } else {
                    log.info("Insertando");
                    addNewDetalle(existingDatosGenerales, newDetalle);
                }
            }
        } catch (Exception e) {
            log.error("Error updating detalles", e);
            throw new DataServiceException("Error al actualizar detalles", e);
        }
        log.info("Detalles actualizados correctamente");
    }

    private void updateExistingDetalle(DatosGenerales existingDatosGenerales, Detalle newDetalle) {
        Optional<Detalle> existingDetalleOpt = existingDatosGenerales.getDetalles().stream()
                .filter(d -> d.equals(newDetalle))
                .findFirst();

        if (existingDetalleOpt.isPresent()) {
            Detalle existingDetalle = existingDetalleOpt.get();

            updateDetalleFields(existingDetalle, newDetalle);

            if (newDetalle.getTransporteCasaTrabajos() != null && !newDetalle.getTransporteCasaTrabajos().isEmpty()) {
                updateTransporteCasaTrabajo(existingDetalle, newDetalle.getTransporteCasaTrabajos());
            }

            log.debug("Updated detalle with ID: {}", newDetalle.getId());
        } else {
            addNewDetalle(existingDatosGenerales, newDetalle);
        }
    }

    private void updateDetalleFields(Detalle existingDetalle, Detalle newDetalle) {
        if (newDetalle.getArea() != null) {
            existingDetalle.setArea(newDetalle.getArea());
        }
        if (newDetalle.getSuministro() != null) {
            existingDetalle.setSuministro(newDetalle.getSuministro());
        }
        if (newDetalle.getSuperficie() != null) {
            existingDetalle.setSuperficie(newDetalle.getSuperficie());
        }
        if (newDetalle.getMedidor() != null) {
            existingDetalle.setMedidor(newDetalle.getMedidor());
        }
        if (newDetalle.getDescripcion() != null) {
            existingDetalle.setDescripcion(newDetalle.getDescripcion());
        }

        if (newDetalle.getMeses() != null) {
            existingDetalle.setMeses(newDetalle.getMeses());
        }
        if (newDetalle.getClinker() != null) {
            existingDetalle.setClinker(newDetalle.getClinker());
        }
        if (newDetalle.getRefrigeranteInstalacion() != null) {
            existingDetalle.setRefrigeranteInstalacion(newDetalle.getRefrigeranteInstalacion());
        }
        if (newDetalle.getRefrigeranteOperacion() != null) {
            existingDetalle.setRefrigeranteOperacion(newDetalle.getRefrigeranteOperacion());
        }
        if (newDetalle.getRefrigeranteDisposicion() != null) {
            existingDetalle.setRefrigeranteDisposicion(newDetalle.getRefrigeranteDisposicion());
        }
        if (newDetalle.getFugaInstalacion() != null) {
            existingDetalle.setFugaInstalacion(newDetalle.getFugaInstalacion());
        }
        if (newDetalle.getFugaOperacion() != null) {
            existingDetalle.setFugaOperacion(newDetalle.getFugaOperacion());
        }
        if (newDetalle.getFugaDisposicion() != null) {
            existingDetalle.setFugaDisposicion(newDetalle.getFugaDisposicion());
        }
        if (newDetalle.getPfcInstalacion() != null) {
            existingDetalle.setPfcInstalacion(newDetalle.getPfcInstalacion());
        }
        if (newDetalle.getPfcOperacion() != null) {
            existingDetalle.setPfcOperacion(newDetalle.getPfcOperacion());
        }
        if (newDetalle.getPfcDisposicion() != null) {
            existingDetalle.setPfcDisposicion(newDetalle.getPfcDisposicion());
        }
        if (newDetalle.getGanado() != null) {
            existingDetalle.setGanado(newDetalle.getGanado());
        }
        if (newDetalle.getFertilizante() != null) {
            existingDetalle.setFertilizante(newDetalle.getFertilizante());
        }
        if (newDetalle.getEncalado() != null) {
            existingDetalle.setEncalado(newDetalle.getEncalado());
        }
        if (newDetalle.getSueloGestionado() != null) {
            existingDetalle.setSueloGestionado(newDetalle.getSueloGestionado());
        }
        if (newDetalle.getCultivoArroz() != null) {
            existingDetalle.setCultivoArroz(newDetalle.getCultivoArroz());
        }
        if (newDetalle.getQuemaBiomasa() != null) {
            existingDetalle.setQuemaBiomasa(newDetalle.getQuemaBiomasa());
        }
        if (newDetalle.getEmbalse() != null) {
            existingDetalle.setEmbalse(newDetalle.getEmbalse());
        }
        if (newDetalle.getTransporteMaterial() != null) {
            existingDetalle.setTransporteMaterial(newDetalle.getTransporteMaterial());
        }
        if (newDetalle.getTransporteVehiculo() != null) {
            existingDetalle.setTransporteVehiculo(newDetalle.getTransporteVehiculo());
        }
        if (newDetalle.getConsumoPapel() != null) {
            existingDetalle.setConsumoPapel(newDetalle.getConsumoPapel());
        }
        if (newDetalle.getGeneracionIndirectaNF3() != null) {
            existingDetalle.setGeneracionIndirectaNF3(newDetalle.getGeneracionIndirectaNF3());
        }
        if (newDetalle.getGeneracionResiduos() != null) {
            existingDetalle.setGeneracionResiduos(newDetalle.getGeneracionResiduos());
        }

        if (newDetalle.getTipoCombustible() != null) {
            existingDetalle.setTipoCombustible(newDetalle.getTipoCombustible());
        }
        if (newDetalle.getCategoriaInstitucion() != null) {
            existingDetalle.setCategoriaInstitucion(newDetalle.getCategoriaInstitucion());
        }
        if (newDetalle.getActividad() != null) {
            existingDetalle.setActividad(newDetalle.getActividad());
        }
    }

    private void updateTransporteCasaTrabajo(Detalle existingDetalle, List<TransporteCasaTrabajo> newTransportes) {
        if (newTransportes == null || newTransportes.isEmpty()) {
            return;
        }

        if (existingDetalle.getTransporteCasaTrabajos() == null) {
            existingDetalle.setTransporteCasaTrabajos(new ArrayList<>());
        }

        for (TransporteCasaTrabajo newTransporte : newTransportes) {
            if (newTransporte.getId() != null) {
                updateExistingTransporte(existingDetalle, newTransporte);
            } else {
                addNewTransporte(existingDetalle, newTransporte);
            }
        }
    }

    private void updateExistingTransporte(Detalle existingDetalle, TransporteCasaTrabajo newTransporte) {
        Optional<TransporteCasaTrabajo> existingTransporteOpt = existingDetalle.getTransporteCasaTrabajos().stream()
                .filter(t -> t.equals(newTransporte))
                .findFirst();

        if (existingTransporteOpt.isPresent()) {
            TransporteCasaTrabajo existingTransporte = existingTransporteOpt.get();
            updateTransporteFields(existingTransporte, newTransporte);
            log.debug("Updated TransporteCasaTrabajo with ID: {}", newTransporte.getId());
        } else {
            addNewTransporte(existingDetalle, newTransporte);
        }
    }

    private void updateTransporteFields(TransporteCasaTrabajo existingTransporte, TransporteCasaTrabajo newTransporte) {
        if (newTransporte.getDescripcionPersonal() != null) {
            existingTransporte.setDescripcionPersonal(newTransporte.getDescripcionPersonal());
        }
        if (newTransporte.getTrabajadores() != null) {
            existingTransporte.setTrabajadores(newTransporte.getTrabajadores());
        }
        if (newTransporte.getViajesSemanales() != null) {
            existingTransporte.setViajesSemanales(newTransporte.getViajesSemanales());
        }
        if (newTransporte.getDiasLaborales() != null) {
            existingTransporte.setDiasLaborales(newTransporte.getDiasLaborales());
        }
        if (newTransporte.getDistanciaViaje() != null) {
            existingTransporte.setDistanciaViaje(newTransporte.getDistanciaViaje());
        }
        if (newTransporte.getTipoMovilidad() != null) {
            existingTransporte.setTipoMovilidad(newTransporte.getTipoMovilidad());
        }
    }

    private void addNewTransporte(Detalle existingDetalle, TransporteCasaTrabajo newTransporte) {
        newTransporte.setDetalle(existingDetalle);
        existingDetalle.getTransporteCasaTrabajos().add(newTransporte);
        log.debug("Added new TransporteCasaTrabajo to detalle with ID: {}", existingDetalle.getId());
    }

    private void addNewDetalle(DatosGenerales existingDatosGenerales, Detalle newDetalle) {
        try {
            newDetalle.setDatosGenerales(existingDatosGenerales);
            Detalle managedDetalle = entityManager.merge(newDetalle);
            existingDatosGenerales.getDetalles().add(managedDetalle);
        } catch (Exception e) {
            log.error("Error al insertar nuevo detalle con entityManager", e);
            throw new DataServiceException("Error al agregar nuevo detalle", e);
        }
    }
}