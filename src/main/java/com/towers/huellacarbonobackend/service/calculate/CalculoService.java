package com.towers.huellacarbonobackend.service.calculate;

import com.towers.huellacarbonobackend.dto.CalculateDto;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.service.data.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.towers.huellacarbonobackend.service.util.NumberFormat.formatNumber;

@Service
@RequiredArgsConstructor
public class CalculoService {
    private final DataService dataService;

    public CalculateDto getCalculos(Long empresa, Integer anio) {
        List<DatosGenerales> data = dataService.getByAnioAndEmpresa(anio, empresa);
        double alcance1 = calcularAlcance(data, 1L);
        double alcance2 = calcularAlcance(data, 2L);
        double alcance3 = calcularAlcance(data, 3L);
        double total = alcance1 + alcance2 + alcance3;
        double alcance1Porcentaje = alcance1 / total * 100;
        double alcance2Porcentaje = alcance2 / total * 100;
        double alcance3Porcentaje = alcance3 / total * 100;
        return new CalculateDto(
                alcance1,
                formatNumber(alcance1),
                alcance1Porcentaje,
                formatNumber(alcance1Porcentaje),
                alcance2,
                formatNumber(alcance2),
                alcance2Porcentaje,
                formatNumber(alcance2Porcentaje),
                alcance3,
                formatNumber(alcance3),
                alcance3Porcentaje,
                formatNumber(alcance3Porcentaje),
                total,
                formatNumber(total)
        );
    }

    private double calcularAlcance(List<DatosGenerales> data, Long alcance) {
        return data.stream().filter(d -> Objects.equals(d.getArchivo().getAlcance().getId(), alcance)).toList()
                .stream().mapToDouble(DatosGenerales::getEmision).sum();
    }
}
