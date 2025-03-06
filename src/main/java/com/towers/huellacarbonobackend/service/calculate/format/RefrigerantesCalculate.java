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
            RefrigeranteInstalacion rI = detalle.getRefrigeranteInstalacion();
            RefrigeranteOperacion rO = detalle.getRefrigeranteOperacion();
            RefrigeranteDisposicion rD = detalle.getRefrigeranteDisposicion();
            double i = rI != null ? rI.getNumeroEquipos() * rI.getCapacidadCarga() * rI.getFugaInstalacion() : 0.0;
            double o = rO != null ? rO.getNumeroEquipos() * rO.getCapacidadCarga() * rO.getAnio() * rO.getFugaUso() : 0.0;
            double d = rD != null ? rD.getNumeroEquipos() * rD.getCapacidadCarga() * rD.getFraccionRefrigeranteDisposicion() * rD.getFraccionRefrigeranteRecuperado() : 0.0;
            double pcgI = rI != null ? pcgRefrigeranteService.getByTipoRefrigerante(rI.getTipoRefrigerante().getId()).getValor() : 0.0;
            double pcgO = rO != null ? pcgRefrigeranteService.getByTipoRefrigerante(rO.getTipoRefrigerante().getId()).getValor() : 0.0;
            double pcgD = rD != null ? pcgRefrigeranteService.getByTipoRefrigerante(rD.getTipoRefrigerante().getId()).getValor() : 0.0;
            double perdidaTotal = i + o + d;
            double e = ((i * pcgI) + (o * pcgO) + (d * pcgD)) / 1000;
            total += e;
        }

        return total;
    }
}
