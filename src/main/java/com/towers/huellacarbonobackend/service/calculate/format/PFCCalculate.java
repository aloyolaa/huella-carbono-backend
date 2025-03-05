package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.data.*;
import com.towers.huellacarbonobackend.service.calculate.PCGPFCService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PFCCalculate {
    private final PCGPFCService pcgpfcService;

    public double calculate(DatosGenerales datosGenerales) {
        double total = 0;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            PFCInstalacion pfcInstalacion = detalle.getPfcInstalacion();
            PFCOperacion pfcOperacion = detalle.getPfcOperacion();
            PFCDisposicion pfcDisposicion = detalle.getPfcDisposicion();
            double instalacion = pfcInstalacion != null ? pfcInstalacion.getNumeroEquipos() * pfcInstalacion.getCapacidadCarga() * pfcInstalacion.getFugaInstalacion() : 0.0;
            double operacion = pfcOperacion != null ? pfcOperacion.getNumeroEquipos() * pfcOperacion.getCapacidadCarga() * pfcOperacion.getTiempoUso() * pfcOperacion.getFugaUso() : 0.0;
            double disposicion = pfcDisposicion != null ? pfcDisposicion.getNumeroEquipos() * pfcDisposicion.getCapacidadCarga() * pfcDisposicion.getFraccionGasPFCDisposicion() * pfcDisposicion.getFraccionGasPFCRecuperado() : 0.0;
            double pcgI = pfcInstalacion != null ? pcgpfcService.getByTipoPFC(pfcInstalacion.getTipoPFC().getId()).getValor() : 0.0;
            double pcgO = pfcOperacion != null ? pcgpfcService.getByTipoPFC(pfcOperacion.getTipoPFC().getId()).getValor() : 0.0;
            double pcgD = pfcDisposicion != null ? pcgpfcService.getByTipoPFC(pfcDisposicion.getTipoPFC().getId()).getValor() : 0.0;
            double perdidaTotal = instalacion + operacion + disposicion;
            double emision = ((instalacion * pcgI) + (operacion * pcgO) + (disposicion * pcgD)) / 1000;
            total += emision;
        }

        return total;
    }
}
