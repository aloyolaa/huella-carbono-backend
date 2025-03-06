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
            PFCInstalacion pfcI = detalle.getPfcInstalacion();
            PFCOperacion pfcO = detalle.getPfcOperacion();
            PFCDisposicion pfcD = detalle.getPfcDisposicion();
            double i = pfcI != null ? pfcI.getNumeroEquipos() * pfcI.getCapacidadCarga() * pfcI.getFugaInstalacion() : 0.0;
            double o = pfcO != null ? pfcO.getNumeroEquipos() * pfcO.getCapacidadCarga() * pfcO.getTiempoUso() * pfcO.getFugaUso() : 0.0;
            double d = pfcD != null ? pfcD.getNumeroEquipos() * pfcD.getCapacidadCarga() * pfcD.getFraccionGasPFCDisposicion() * pfcD.getFraccionGasPFCRecuperado() : 0.0;
            double pcgI = pfcI != null ? pcgpfcService.getByTipoPFC(pfcI.getTipoPFC().getId()).getValor() : 0.0;
            double pcgO = pfcO != null ? pcgpfcService.getByTipoPFC(pfcO.getTipoPFC().getId()).getValor() : 0.0;
            double pcgD = pfcD != null ? pcgpfcService.getByTipoPFC(pfcD.getTipoPFC().getId()).getValor() : 0.0;
            double perdidaTotal = i + o + d;
            double e = ((i * pcgI) + (o * pcgO) + (d * pcgD)) / 1000;
            total += e;
        }

        return total;
    }
}
