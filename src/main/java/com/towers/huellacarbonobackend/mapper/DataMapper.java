package com.towers.huellacarbonobackend.mapper;

import com.towers.huellacarbonobackend.dto.DataDto;
import com.towers.huellacarbonobackend.dto.GanadoDataDto;
import com.towers.huellacarbonobackend.entity.data.Archivo;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Empresa;
import com.towers.huellacarbonobackend.entity.data.GanadoData;
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
        datosGenerales.setAnio(dataDto.anio());
        datosGenerales.setMes(dataDto.mes());
        datosGenerales.setGanadoData(dataDto.ganadoData() != null ?
                new GanadoData(dataDto.ganadoData().id(), dataDto.ganadoData().temperatura(), datosGenerales) : null);
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
                datosGenerales.getAnio(),
                datosGenerales.getMes(),
                datosGenerales.getEmision() != null ? datosGenerales.getEmision() : 0.0,
                datosGenerales.getGanadoData() != null ?
                        new GanadoDataDto(datosGenerales.getGanadoData().getId(), datosGenerales.getGanadoData().getTemperatura()) : null,
                datosGenerales.getDetalles().stream().map(detalleMapper::toDetalleDto).toList()
        );
    }
}
