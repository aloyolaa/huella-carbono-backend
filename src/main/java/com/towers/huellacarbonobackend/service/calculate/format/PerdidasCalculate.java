package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionPerdidas;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.service.calculate.FactorEmisionPerdidasService;
import com.towers.huellacarbonobackend.service.calculate.PCGCombustibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerdidasCalculate {
    private final FactorEmisionPerdidasService factorEmisionPerdidasService;
    private final PCGCombustibleService pcgCombustibleService;

    public double calculate(DatosGenerales datosGenerales) {
        double total = 0;
        FactorEmisionPerdidas factorEmisionPerdidas = factorEmisionPerdidasService.getByAnio(datosGenerales.getAnio());
        double factorConversion = 1000 / 3.6;
        double feCO2 = factorEmisionPerdidas.getCo2();
        double feCH4 = factorEmisionPerdidas.getCh4() * 1000;
        double feN2O = factorEmisionPerdidas.getN2o() * 1000;
        double pcgCO2 = pcgCombustibleService.getByNombre("Dióxido de carbono").getValor();
        double pcgCH4 = pcgCombustibleService.getByNombre("Metano - fósil").getValor();
        double pcgN2O = pcgCombustibleService.getByNombre("Óxido nitroso").getValor();
        for (Detalle detalle : datosGenerales.getDetalles()) {
            double perdidas = getPerdidas(detalle);
            double consumo = perdidas * factorConversion;
            double emisionCO2 = perdidas * feCO2 / 1000;
            double emisionCH4 = perdidas * feCH4 / 1000000;
            double emisionN2O = perdidas * feN2O / 1000000;
            double emision = (emisionCO2 * pcgCO2) + (emisionCH4 * pcgCH4) + (emisionN2O * pcgN2O);
            total += emision;
        }

        return total;
    }

    private double getPerdidas(Detalle detalle) {
        double perdidas = 0;
        if (detalle.getMeses().getEnero() != null) perdidas += detalle.getMeses().getEnero();
        if (detalle.getMeses().getFebrero() != null) perdidas += detalle.getMeses().getFebrero();
        if (detalle.getMeses().getMarzo() != null) perdidas += detalle.getMeses().getMarzo();
        if (detalle.getMeses().getAbril() != null) perdidas += detalle.getMeses().getAbril();
        if (detalle.getMeses().getMayo() != null) perdidas += detalle.getMeses().getMayo();
        if (detalle.getMeses().getJunio() != null) perdidas += detalle.getMeses().getJunio();
        if (detalle.getMeses().getJulio() != null) perdidas += detalle.getMeses().getJulio();
        if (detalle.getMeses().getAgosto() != null) perdidas += detalle.getMeses().getAgosto();
        if (detalle.getMeses().getSeptiembre() != null) perdidas += detalle.getMeses().getSeptiembre();
        if (detalle.getMeses().getOctubre() != null) perdidas += detalle.getMeses().getOctubre();
        if (detalle.getMeses().getNoviembre() != null) perdidas += detalle.getMeses().getNoviembre();
        if (detalle.getMeses().getDiciembre() != null) perdidas += detalle.getMeses().getDiciembre();
        return perdidas;
    }
}
