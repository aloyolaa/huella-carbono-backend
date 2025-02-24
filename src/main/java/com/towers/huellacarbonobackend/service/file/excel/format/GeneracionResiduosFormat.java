package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.entity.data.GeneracionResiduos;
import com.towers.huellacarbonobackend.entity.data.GeneracionResiduosDetalle;
import com.towers.huellacarbonobackend.service.data.CondicionSEDSService;
import com.towers.huellacarbonobackend.service.file.excel.exp.ExportOperation;
import com.towers.huellacarbonobackend.service.file.excel.imp.ImportOperation;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.towers.huellacarbonobackend.service.file.excel.Commons.*;

@Component
@RequiredArgsConstructor
public class GeneracionResiduosFormat implements ImportOperation, ExportOperation {
    private final CondicionSEDSService condicionSEDSService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        Detalle detalle = new Detalle();
        GeneracionResiduos generacionResiduos = new GeneracionResiduos();
        List<GeneracionResiduosDetalle> generacionResiduosDetalles = new ArrayList<>();
        generacionResiduos.setAnioHuella(readIntegerCell(sheet.getRow(19), 2));
        generacionResiduos.setPrecipitacion(readDoubleCell(sheet.getRow(22), 2));
        generacionResiduos.setAnioInicio(readIntegerCell(sheet.getRow(19), 6));
        generacionResiduos.setTemperatura(readDoubleCell(sheet.getRow(22), 6));
        generacionResiduos.setContenidoGrasas(readListCell(sheet.getRow(27), 2).equals("Sí"));
        generacionResiduos.setTasaCrecimiento(readDoubleCell(sheet.getRow(39), 2));
        generacionResiduos.setCondicionSEDS(condicionSEDSService.getByNombre(readListCell(sheet.getRow(24), 2)));
        for (int rowIndex = 31; rowIndex <= 36; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            Integer anio = readIntegerCell(row, 1);
            if (anio == null) {
                break;
            }

            GeneracionResiduosDetalle generacionResiduosDetalle = new GeneracionResiduosDetalle(
                    null,
                    anio,
                    readDoubleCell(row, 2),
                    readDoubleCell(row, 3),
                    readDoubleCell(row, 4),
                    readDoubleCell(row, 5),
                    readDoubleCell(row, 6),
                    readDoubleCell(row, 7),
                    readDoubleCell(row, 8),
                    generacionResiduos
            );

            generacionResiduosDetalles.add(generacionResiduosDetalle);
        }
        generacionResiduos.setGeneracionResiduosDetalles(generacionResiduosDetalles);
        detalle.setGeneracionResiduos(generacionResiduos);
        detalle.setDatosGenerales(datosGenerales);
        datosGenerales.setDetalles(List.of(detalle));
    }

    @Override
    public void writeData(Sheet sheet, DatosGenerales datosGenerales) {
        int rowIndex = 31;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            writeCell(sheet, 19, 2, detalle.getGeneracionResiduos().getAnioHuella());
            writeCell(sheet, 22, 2, detalle.getGeneracionResiduos().getPrecipitacion());
            writeCell(sheet, 19, 6, detalle.getGeneracionResiduos().getAnioInicio());
            writeCell(sheet, 22, 6, detalle.getGeneracionResiduos().getTemperatura());
            updateListCell(sheet, 27, 2, detalle.getGeneracionResiduos().getContenidoGrasas() ? "Sí" : "No");
            writeCell(sheet, 39, 2, detalle.getGeneracionResiduos().getTasaCrecimiento());
            updateListCell(sheet, 24, 2, detalle.getGeneracionResiduos().getCondicionSEDS().getNombre());
            for (GeneracionResiduosDetalle generacionResiduosDetalle : detalle.getGeneracionResiduos().getGeneracionResiduosDetalles()) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    sheet.createRow(rowIndex);
                }
                writeCell(sheet, rowIndex, 1, generacionResiduosDetalle.getAnio());
                writeCell(sheet, rowIndex, 2, generacionResiduosDetalle.getProductosMadera());
                writeCell(sheet, rowIndex, 3, generacionResiduosDetalle.getProductosPapel());
                writeCell(sheet, rowIndex, 4, generacionResiduosDetalle.getResiduos());
                writeCell(sheet, rowIndex, 5, generacionResiduosDetalle.getTextiles());
                writeCell(sheet, rowIndex, 6, generacionResiduosDetalle.getJardines());
                writeCell(sheet, rowIndex, 7, generacionResiduosDetalle.getPaniales());
                writeCell(sheet, rowIndex, 8, generacionResiduosDetalle.getOtros());
                rowIndex++;
            }
        }
    }
}
