package com.towers.huellacarbonobackend.mapper;

import com.towers.huellacarbonobackend.dto.DataDto;
import com.towers.huellacarbonobackend.dto.GanadoDataDto;
import com.towers.huellacarbonobackend.entity.data.Archivo;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Empresa;
import com.towers.huellacarbonobackend.entity.data.GanadoData;
import com.towers.huellacarbonobackend.service.calculate.ConsumoElectricidadCalculate;
import com.towers.huellacarbonobackend.service.calculate.GeneracionYOtraEnergiaCalculate;
import com.towers.huellacarbonobackend.service.calculate.PerdidasCalculate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataMapper {
    private final DetalleMapper detalleMapper;
    private final GeneracionYOtraEnergiaCalculate generacionYOtraEnergiaCalculate;
    private final ConsumoElectricidadCalculate consumoElectricidadCalculate;
    private final PerdidasCalculate perdidasCalculate;

    public DatosGenerales toDatosGenerales(DataDto dataDto, Long empresa, Long archivo) {
        DatosGenerales datosGenerales = new DatosGenerales();
        datosGenerales.setId(dataDto.id());
        datosGenerales.setNombre(dataDto.nombre());
        datosGenerales.setCargo(dataDto.cargo());
        datosGenerales.setCorreo(dataDto.correo());
        datosGenerales.setLocacion(dataDto.locacion());
        datosGenerales.setComentarios(dataDto.comentarios());
        datosGenerales.setAnio(dataDto.anio());
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
                getEmision(datosGenerales),
                datosGenerales.getGanadoData() != null ?
                        new GanadoDataDto(datosGenerales.getGanadoData().getId(), datosGenerales.getGanadoData().getTemperatura()) : null,
                datosGenerales.getDetalles().stream().map(detalleMapper::toDetalleDto).toList()
        );
    }

    private double getEmision(DatosGenerales datosGenerales) {
        return switch (datosGenerales.getArchivo().getId().intValue()) {
            case 1, 21 -> generacionYOtraEnergiaCalculate.calculate(datosGenerales);
            case 18 -> consumoElectricidadCalculate.calculate(datosGenerales);
            case 19, 20 -> perdidasCalculate.calculate(datosGenerales);
            default -> 0.0;
        };
    }
}
