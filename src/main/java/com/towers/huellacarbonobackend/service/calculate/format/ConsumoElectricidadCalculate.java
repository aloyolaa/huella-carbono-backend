package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionConsumo;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.service.calculate.FactorEmisionConsumoService;
import com.towers.huellacarbonobackend.service.calculate.PCGCombustibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsumoElectricidadCalculate {
    private final FactorEmisionConsumoService factorEmisionConsumoService;
    private final PCGCombustibleService pcgCombustibleService;

    public double calculate(DatosGenerales datosGenerales) {
        double total = 0;
        FactorEmisionConsumo factorEmisionConsumo = factorEmisionConsumoService.getByAnio(datosGenerales.getAnio());
        double factorConversion = 1000 / 3.6;
        double feCO2 = factorEmisionConsumo.getCo2();
        double feCH4 = factorEmisionConsumo.getCh4() * 1000;
        double feN2O = factorEmisionConsumo.getN2o() * 1000;
        double pcgCO2 = pcgCombustibleService.getByNombre("Dióxido de carbono").getValor();
        double pcgCH4 = pcgCombustibleService.getByNombre("Metano - fósil").getValor();
        double pcgN2O = pcgCombustibleService.getByNombre("Óxido nitroso").getValor();
        for (Detalle detalle : datosGenerales.getDetalles()) {
            double consumoElectricidad = getConsumo(detalle);
            double consumo = consumoElectricidad * factorConversion;
            double emisionCO2 = consumoElectricidad * feCO2 / 1000;
            double emisionCH4 = consumoElectricidad * feCH4 / 1000000;
            double emisionN2O = consumoElectricidad * feN2O / 1000000;
            double emision = (emisionCO2 * pcgCO2) + (emisionCH4 * pcgCH4) + (emisionN2O * pcgN2O);
            total += emision;
        }

        return total;
    }

    private double getConsumo(Detalle detalle) {
        double consumoElectricidad = 0;
        if (detalle.getMeses().getEnero() != null) consumoElectricidad += detalle.getMeses().getEnero();
        if (detalle.getMeses().getFebrero() != null) consumoElectricidad += detalle.getMeses().getFebrero();
        if (detalle.getMeses().getMarzo() != null) consumoElectricidad += detalle.getMeses().getMarzo();
        if (detalle.getMeses().getAbril() != null) consumoElectricidad += detalle.getMeses().getAbril();
        if (detalle.getMeses().getMayo() != null) consumoElectricidad += detalle.getMeses().getMayo();
        if (detalle.getMeses().getJunio() != null) consumoElectricidad += detalle.getMeses().getJunio();
        if (detalle.getMeses().getJulio() != null) consumoElectricidad += detalle.getMeses().getJulio();
        if (detalle.getMeses().getAgosto() != null) consumoElectricidad += detalle.getMeses().getAgosto();
        if (detalle.getMeses().getSeptiembre() != null) consumoElectricidad += detalle.getMeses().getSeptiembre();
        if (detalle.getMeses().getOctubre() != null) consumoElectricidad += detalle.getMeses().getOctubre();
        if (detalle.getMeses().getNoviembre() != null) consumoElectricidad += detalle.getMeses().getNoviembre();
        if (detalle.getMeses().getDiciembre() != null) consumoElectricidad += detalle.getMeses().getDiciembre();
        return consumoElectricidad;
    }
}
