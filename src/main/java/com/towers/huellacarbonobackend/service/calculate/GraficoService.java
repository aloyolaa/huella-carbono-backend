package com.towers.huellacarbonobackend.service.calculate;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.service.data.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GraficoService {
    private final DataService dataService;

    public void getGrafico(Long empresa, Integer anio) {
        List<DatosGenerales> data = dataService.getByAnioAndEmpresa(anio, empresa);

    }
}
