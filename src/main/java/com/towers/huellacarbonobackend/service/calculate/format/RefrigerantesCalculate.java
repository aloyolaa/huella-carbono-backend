package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.data.*;
import com.towers.huellacarbonobackend.service.calculate.PCGRefrigeranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefrigerantesCalculate {
    private final PCGRefrigeranteService pcgRefrigeranteService;

    public double calculate(DatosGenerales datosGenerales) {
        double total = 0;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            RefrigeranteInstalacion refrigeranteInstalacion = detalle.getRefrigeranteInstalacion();
            RefrigeranteOperacion refrigeranteOperacion = detalle.getRefrigeranteOperacion();
            RefrigeranteDisposicion refrigeranteDisposicion = detalle.getRefrigeranteDisposicion();
            double instalacion = refrigeranteInstalacion != null ? refrigeranteInstalacion.getNumeroEquipos() * refrigeranteInstalacion.getCapacidadCarga() * refrigeranteInstalacion.getFugaInstalacion() : 0.0;
            double operacion = refrigeranteOperacion != null ? refrigeranteOperacion.getNumeroEquipos() * refrigeranteOperacion.getCapacidadCarga() * refrigeranteOperacion.getAnio() * refrigeranteOperacion.getFugaUso() : 0.0;
            double disposicion = refrigeranteDisposicion != null ? refrigeranteDisposicion.getNumeroEquipos() * refrigeranteDisposicion.getCapacidadCarga() * refrigeranteDisposicion.getFraccionRefrigeranteDisposicion() * refrigeranteDisposicion.getFraccionRefrigeranteRecuperado() : 0.0;
            double pcgI = refrigeranteInstalacion != null ? pcgRefrigeranteService.getByTipoRefrigerante(refrigeranteInstalacion.getTipoRefrigerante().getId()).getValor() : 0.0;
            double pcgO = refrigeranteOperacion != null ? pcgRefrigeranteService.getByTipoRefrigerante(refrigeranteOperacion.getTipoRefrigerante().getId()).getValor() : 0.0;
            double pcgD = refrigeranteDisposicion != null ? pcgRefrigeranteService.getByTipoRefrigerante(refrigeranteDisposicion.getTipoRefrigerante().getId()).getValor() : 0.0;
            double perdidaTotal = instalacion + operacion + disposicion;
            double emision = ((instalacion * pcgI) + (operacion * pcgO) + (disposicion * pcgD)) / 1000;
            total += emision;
        }

        return total;
    }
}
