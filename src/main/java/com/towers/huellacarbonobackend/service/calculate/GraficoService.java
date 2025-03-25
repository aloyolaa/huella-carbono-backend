package com.towers.huellacarbonobackend.service.calculate;

import com.towers.huellacarbonobackend.dto.StatisticsDataDto;
import com.towers.huellacarbonobackend.dto.StatisticsDto;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.service.calculate.format.*;
import com.towers.huellacarbonobackend.service.data.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.towers.huellacarbonobackend.service.util.NumberFormat.formatNumber;

@Service
@RequiredArgsConstructor
public class GraficoService {
    private final DataService dataService;
    private final EnergiaYCombustionCalculate energiaYCombustionCalculate;
    private final FuentesMovilesYRefinacionCalculate fuentesMovilesYRefinacionCalculate;
    private final ConsumoElectricidadCalculate consumoElectricidadCalculate;
    private final PerdidasCalculate perdidasCalculate;
    private final ConsumoAguaCalculate consumoAguaCalculate;

    public StatisticsDto getGrafico(Long empresa, Integer anio) {
        List<DatosGenerales> data = dataService.getByAnioAndEmpresa(anio, empresa);

        return new StatisticsDto(
                calculateStatistics(data, 1L),
                calculateStatistics(data, 2L),
                calculateStatistics(data, 3L)
        );
    }

    private List<StatisticsDataDto> calculateStatistics(List<DatosGenerales> data, Long alcance) {
        List<StatisticsDataDto> statisticsData = new ArrayList<>();
        for (int mes = 1; mes <= 12; mes++) {
            int finalMes = mes;
            double e = data.stream().filter(d -> (d.getMes() == finalMes || d.getMes() == 0) && d.getArchivo().getAlcance().getId().equals(alcance))
                    .mapToDouble(d -> calculateEmision(d, finalMes)).sum();
            statisticsData.add(new StatisticsDataDto(mes, e, formatNumber(e)));
        }

        return statisticsData;
    }

    private double calculateEmision(DatosGenerales datosGenerales, int mes) {
        return switch (datosGenerales.getArchivo().getId().intValue()) {
            case 1, 2, 21 -> energiaYCombustionCalculate.calculateByMes(datosGenerales, mes);
            case 3 -> fuentesMovilesYRefinacionCalculate.calculateByMes(datosGenerales, mes);
            case 4, 5, 6 -> 0;
            case 18 -> consumoElectricidadCalculate.calculateByMes(datosGenerales, mes);
            case 19, 20 -> perdidasCalculate.calculateByMes(datosGenerales, mes);
            case 27 -> consumoAguaCalculate.calculateByMes(datosGenerales, mes);
            default -> datosGenerales.getEmision();
        };
    }
}
