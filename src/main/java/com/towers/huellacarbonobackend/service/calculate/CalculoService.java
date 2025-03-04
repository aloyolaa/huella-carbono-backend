package com.towers.huellacarbonobackend.service.calculate;

import com.towers.huellacarbonobackend.dto.CalculateDto;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.service.data.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculoService {
    private final DataService dataService;

    public CalculateDto getCalculos(Long empresa, Integer anio) {
        List<DatosGenerales> data = dataService.getByAnioAndEmpresa(anio, empresa);
        Double alcance1 = calcularAlcance1(data.stream().filter(d -> d.getArchivo().getAlcance().getId() == 1).toList());
        Double alcance2 = calcularAlcance2(data.stream().filter(d -> d.getArchivo().getAlcance().getId() == 2).toList());
        Double alcance3 = calcularAlcance3(data.stream().filter(d -> d.getArchivo().getAlcance().getId() == 3).toList());
        return new CalculateDto(
                alcance1,
                alcance2,
                alcance3,
                alcance1 + alcance2 + alcance3
        );
    }

    private Double calcularAlcance1(List<DatosGenerales> data) {
        return 0.0;
    }

    private Double calcularAlcance2(List<DatosGenerales> data) {
        return 0.0;
    }

    private Double calcularAlcance3(List<DatosGenerales> data) {
        return 0.0;
    }
}
