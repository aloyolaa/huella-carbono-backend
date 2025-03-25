package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionConsumo;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.service.calculate.FactorEmisionConsumoService;
import com.towers.huellacarbonobackend.service.calculate.PCGCombustibleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.towers.huellacarbonobackend.service.calculate.format.CommonCalculate.getConsumoMes;

@Component
@RequiredArgsConstructor
public class ConsumoElectricidadCalculate {
    private final FactorEmisionConsumoService factorEmisionConsumoService;
    private final PCGCombustibleService pcgCombustibleService;

    private double factorConversion;
    private double pcgCO2;
    private double pcgCH4;
    private double pcgN2O;

    @PostConstruct
    private void init() {
        this.factorConversion = 1000 / 3.6;
        this.pcgCO2 = pcgCombustibleService.getByNombre("Dióxido de carbono").getValor();
        this.pcgCH4 = pcgCombustibleService.getByNombre("Metano - fósil").getValor();
        this.pcgN2O = pcgCombustibleService.getByNombre("Óxido nitroso").getValor();
    }

    public double calculate(DatosGenerales datosGenerales) {
        try {
            double total = 0;
            for (int mes = 1; mes <= 12; mes++) {
                total += calculateByMes(datosGenerales, mes);
            }

            return total;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public double calculateByMes(DatosGenerales datosGenerales, int mes) {
        try {
            double total = 0;
            FactorEmisionConsumo fe = factorEmisionConsumoService.getByAnio(datosGenerales.getAnio());
            double feCO2 = fe.getCo2();
            double feCH4 = fe.getCh4() * 1000;
            double feN2O = fe.getN2o() * 1000;
            for (Detalle detalle : datosGenerales.getDetalles()) {
                double consumoElectricidad = getConsumoMes(detalle, mes);
                double consumo = consumoElectricidad * factorConversion;
                double co2 = consumoElectricidad * feCO2 / 1000;
                double ch4 = consumoElectricidad * feCH4 / 1000000;
                double n2o = consumoElectricidad * feN2O / 1000000;
                double e = (co2 * pcgCO2) + (ch4 * pcgCH4) + (n2o * pcgN2O);
                total += e;
            }

            return total;
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
