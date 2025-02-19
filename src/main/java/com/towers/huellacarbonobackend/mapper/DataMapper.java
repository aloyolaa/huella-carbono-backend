package com.towers.huellacarbonobackend.mapper;

import com.towers.huellacarbonobackend.dto.DataDto;
import com.towers.huellacarbonobackend.dto.GanadoDataDto;
import com.towers.huellacarbonobackend.dto.GeneracionResiduosDataDto;
import com.towers.huellacarbonobackend.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataMapper {
    private final DetalleMapper detalleMapper;

    public DatosGenerales toDatosGenerales(DataDto dataDto, Long empresa, Long archivo) {
        DatosGenerales datosGenerales = new DatosGenerales();
        datosGenerales.setId(dataDto.id());
        datosGenerales.setNombre(dataDto.nombre());
        datosGenerales.setCargo(dataDto.cargo());
        datosGenerales.setCorreo(dataDto.correo());
        datosGenerales.setLocacion(dataDto.locacion());
        datosGenerales.setComentarios(dataDto.comentarios());
        datosGenerales.setGanadoData(dataDto.ganadoData() != null ?
                new GanadoData(dataDto.ganadoData().id(), dataDto.ganadoData().temperatura(), datosGenerales) : null);
        datosGenerales.setGeneracionResiduosData(dataDto.generacionResiduosData() != null ?
                new GeneracionResiduosData(
                        dataDto.generacionResiduosData().id(),
                        dataDto.generacionResiduosData().anioHuella(),
                        dataDto.generacionResiduosData().precipitacion(),
                        dataDto.generacionResiduosData().anioInicio(),
                        dataDto.generacionResiduosData().temperatura(),
                        dataDto.generacionResiduosData().contenidoGrasas(),
                        dataDto.generacionResiduosData().tasaCrecimiento(),
                        new CondicionSEDS(dataDto.generacionResiduosData().condicionSEDS()),
                        datosGenerales
                ) : null
        );
        datosGenerales.setArchivo(new Archivo(archivo));
        datosGenerales.setEmpresa(new Empresa(empresa));
        datosGenerales.setDetalles(dataDto.detalles().stream().map(d -> detalleMapper.toDetalle(d, datosGenerales)).toList());

        return datosGenerales;
    }

    public DataDto toDataDto(DatosGenerales datosGenerales) {
        return new DataDto(
                datosGenerales.getId(),
                datosGenerales.getNombre(),
                datosGenerales.getCargo(),
                datosGenerales.getCorreo(),
                datosGenerales.getLocacion(),
                datosGenerales.getComentarios(),
                datosGenerales.getGanadoData() != null ?
                        new GanadoDataDto(datosGenerales.getGanadoData().getId(), datosGenerales.getGanadoData().getTemperatura()) : null,
                datosGenerales.getGeneracionResiduosData() != null ?
                        new GeneracionResiduosDataDto(
                                datosGenerales.getGeneracionResiduosData().getId(),
                                datosGenerales.getGeneracionResiduosData().getAnioHuella(),
                                datosGenerales.getGeneracionResiduosData().getPrecipitacion(),
                                datosGenerales.getGeneracionResiduosData().getAnioInicio(),
                                datosGenerales.getGeneracionResiduosData().getTemperatura(),
                                datosGenerales.getGeneracionResiduosData().getContenidoGrasas(),
                                datosGenerales.getGeneracionResiduosData().getTasaCrecimiento(),
                                datosGenerales.getGeneracionResiduosData().getCondicionSEDS().getId()
                        ) : null,
                datosGenerales.getDetalles().stream().map(detalleMapper::toDetalleDto).toList()
        );
    }
}
