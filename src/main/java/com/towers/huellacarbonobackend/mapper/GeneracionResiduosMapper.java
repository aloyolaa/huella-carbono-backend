package com.towers.huellacarbonobackend.mapper;

import com.towers.huellacarbonobackend.dto.GeneracionResiduosDto;
import com.towers.huellacarbonobackend.entity.data.CondicionSEDS;
import com.towers.huellacarbonobackend.entity.data.GeneracionResiduos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeneracionResiduosMapper {
    private final GeneracionResiduosDetalleMapper generacionResiduosDetalleMapper;

    public GeneracionResiduos toGeneracionResiduos(GeneracionResiduosDto generacionResiduosDto) {
        GeneracionResiduos generacionResiduos = new GeneracionResiduos();
        generacionResiduos.setId(generacionResiduosDto.id());
        generacionResiduos.setAnioHuella(generacionResiduosDto.anioHuella());
        generacionResiduos.setPrecipitacion(generacionResiduosDto.precipitacion());
        generacionResiduos.setAnioInicio(generacionResiduosDto.anioInicio());
        generacionResiduos.setTemperatura(generacionResiduosDto.temperatura());
        generacionResiduos.setContenidoGrasas(generacionResiduosDto.contenidoGrasas());
        generacionResiduos.setTasaCrecimiento(generacionResiduosDto.tasaCrecimiento());
        generacionResiduos.setCondicionSEDS(new CondicionSEDS(generacionResiduosDto.condicionSEDS()));
        generacionResiduos.setGeneracionResiduosDetalles(generacionResiduosDto.generacionResiduosDetalles().stream().map(d -> generacionResiduosDetalleMapper.toGeneracionResiduosDetalle(d, generacionResiduos)).toList());
        return generacionResiduos;
    }

    public GeneracionResiduosDto toGeneracionResiduosDto(GeneracionResiduos generacionResiduos) {
        return new GeneracionResiduosDto(
                generacionResiduos.getId(),
                generacionResiduos.getAnioHuella(),
                generacionResiduos.getPrecipitacion(),
                generacionResiduos.getAnioInicio(),
                generacionResiduos.getTemperatura(),
                generacionResiduos.getContenidoGrasas(),
                generacionResiduos.getTasaCrecimiento(),
                generacionResiduos.getCondicionSEDS().getId(),
                generacionResiduos.getGeneracionResiduosDetalles().stream().map(generacionResiduosDetalleMapper::toGeneracionResiduosDetalleDto).toList()
        );
    }
}
