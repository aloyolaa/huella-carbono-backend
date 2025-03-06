package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionPerdidas;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.service.calculate.FactorEmisionPerdidasService;
import com.towers.huellacarbonobackend.service.calculate.PCGCombustibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.towers.huellacarbonobackend.service.calculate.format.CommonCalculate.getConsumo;

@Component
@RequiredArgsConstructor
public class PerdidasCalculate {
    private final FactorEmisionPerdidasService factorEmisionPerdidasService;
    private final PCGCombustibleService pcgCombustibleService;

    public double calculate(DatosGenerales datosGenerales) {
        try {
            double total = 0;
            FactorEmisionPerdidas fe = factorEmisionPerdidasService.getByAnio(datosGenerales.getAnio());
            double factorConversion = 1000 / 3.6;
            double feCO2 = fe.getCo2();
            double feCH4 = fe.getCh4() * 1000;
            double feN2O = fe.getN2o() * 1000;
            double pcgCO2 = pcgCombustibleService.getByNombre("Dióxido de carbono").getValor();
            double pcgCH4 = pcgCombustibleService.getByNombre("Metano - fósil").getValor();
            double pcgN2O = pcgCombustibleService.getByNombre("Óxido nitroso").getValor();
            for (Detalle detalle : datosGenerales.getDetalles()) {
                double perdidas = getConsumo(detalle);
                double consumo = perdidas * factorConversion;
                double co2 = perdidas * feCO2 / 1000;
                double ch4 = perdidas * feCH4 / 1000000;
                double n2o = perdidas * feN2O / 1000000;
                double e = (co2 * pcgCO2) + (ch4 * pcgCH4) + (n2o * pcgN2O);
                total += e;
            }

            return total;
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
