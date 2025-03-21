package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.calculate.FactorConversionCombustible;
import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionCombustible;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.service.calculate.FactorConversionCombustibleService;
import com.towers.huellacarbonobackend.service.calculate.FactorEmisionCombustibleService;
import com.towers.huellacarbonobackend.service.calculate.PCGCombustibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.towers.huellacarbonobackend.service.calculate.format.CommonCalculate.getConsumoMes;

@Component
@RequiredArgsConstructor
public class EnergiaYCombustionCalculate {
    private final FactorConversionCombustibleService factorConversionCombustibleService;
    private final FactorEmisionCombustibleService factorEmisionCombustibleService;
    private final PCGCombustibleService pcgCombustibleService;

    public double calculate(DatosGenerales datosGenerales) {
        try {
            double pcgCO2 = pcgCombustibleService.getByNombre("Dióxido de carbono").getValor();
            double pcgCH4 = pcgCombustibleService.getByNombre("Metano - fósil").getValor();
            double pcgN2O = pcgCombustibleService.getByNombre("Óxido nitroso").getValor();

            return calculateByMes(datosGenerales, pcgCO2, pcgCH4, pcgN2O, 1) +
                    calculateByMes(datosGenerales, pcgCO2, pcgCH4, pcgN2O, 2) +
                    calculateByMes(datosGenerales, pcgCO2, pcgCH4, pcgN2O, 3) +
                    calculateByMes(datosGenerales, pcgCO2, pcgCH4, pcgN2O, 4) +
                    calculateByMes(datosGenerales, pcgCO2, pcgCH4, pcgN2O, 5) +
                    calculateByMes(datosGenerales, pcgCO2, pcgCH4, pcgN2O, 6) +
                    calculateByMes(datosGenerales, pcgCO2, pcgCH4, pcgN2O, 7) +
                    calculateByMes(datosGenerales, pcgCO2, pcgCH4, pcgN2O, 8) +
                    calculateByMes(datosGenerales, pcgCO2, pcgCH4, pcgN2O, 9) +
                    calculateByMes(datosGenerales, pcgCO2, pcgCH4, pcgN2O, 10) +
                    calculateByMes(datosGenerales, pcgCO2, pcgCH4, pcgN2O, 11) +
                    calculateByMes(datosGenerales, pcgCO2, pcgCH4, pcgN2O, 12);
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public double calculateByMes(DatosGenerales datosGenerales, double pcgCO2, double pcgCH4, double pcgN2O, int mes) {
        double total = 0;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            double consumo = getConsumoMes(detalle, mes);
            FactorConversionCombustible fc = factorConversionCombustibleService.getByTipoCombustible(detalle.getTipoCombustible().getId());
            FactorEmisionCombustible fe = factorEmisionCombustibleService.getByTipoCombustible(detalle.getTipoCombustible().getId());
            consumo = consumo * fc.getValor();
            double feCO2 = fe.getCo2();
            double feCH4 = fe.getCh4();
            double feN2O = fe.getN2o();
            double co2 = consumo * feCO2 / 1000;
            double ch4 = consumo * feCH4 / 1000;
            double n2o = consumo * feN2O / 1000;
            double e = (co2 * pcgCO2) + (ch4 * pcgCH4) + (n2o * pcgN2O);
            total += e;
        }
        return total;
    }
}
