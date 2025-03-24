package com.towers.huellacarbonobackend.service.calculate;

import com.towers.huellacarbonobackend.dto.CalculateDto;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.service.data.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CalculoService {
    private final DataService dataService;

    public CalculateDto getCalculos(Long empresa, Integer anio) {
        List<DatosGenerales> data = dataService.getByAnioAndEmpresa(anio, empresa);
        double alcance1 = calcularAlcance(data, 1L);
        double alcance2 = calcularAlcance(data, 2L);
        double alcance3 = calcularAlcance(data, 3L);
        return new CalculateDto(
                alcance1,
                alcance2,
                alcance3,
                alcance1 + alcance2 + alcance3
        );
    }

    private double calcularAlcance(List<DatosGenerales> data, Long alcance) {
        return data.stream().filter(d -> Objects.equals(d.getArchivo().getAlcance().getId(), alcance)).toList()
                .stream().mapToDouble(DatosGenerales::getEmision).sum();
    }
}
