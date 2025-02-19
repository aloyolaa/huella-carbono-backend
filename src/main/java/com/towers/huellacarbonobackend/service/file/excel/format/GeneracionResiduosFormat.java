package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.DatosGenerales;
import com.towers.huellacarbonobackend.entity.Detalle;
import com.towers.huellacarbonobackend.entity.GeneracionResiduos;
import com.towers.huellacarbonobackend.entity.GeneracionResiduosData;
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
        datosGenerales.setGeneracionResiduosData(
                new GeneracionResiduosData(
                        null,
                        readIntegerCell(sheet.getRow(19), 2),
                        readDoubleCell(sheet.getRow(22), 2),
                        readIntegerCell(sheet.getRow(19), 6),
                        readDoubleCell(sheet.getRow(22), 6),
                        readListCell(sheet.getRow(27), 2).equals("Sí"),
                        readDoubleCell(sheet.getRow(39), 2),
                        condicionSEDSService.getByNombre(readListCell(sheet.getRow(24), 2)),
                        datosGenerales
                )
        );
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 31; rowIndex <= 36; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            Integer anio = readIntegerCell(row, 1);
            if (anio == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setGeneracionResiduos(
                    new GeneracionResiduos(
                            null,
                            anio,
                            readDoubleCell(row, 2),
                            readDoubleCell(row, 3),
                            readDoubleCell(row, 4),
                            readDoubleCell(row, 5),
                            readDoubleCell(row, 6),
                            readDoubleCell(row, 7),
                            readDoubleCell(row, 8)
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    @Override
    public void writeData(Sheet sheet, DatosGenerales datosGenerales) {
        writeCell(sheet, 19, 2, datosGenerales.getGeneracionResiduosData().getAnioHuella());
        writeCell(sheet, 22, 2, datosGenerales.getGeneracionResiduosData().getPrecipitacion());
        writeCell(sheet, 19, 6, datosGenerales.getGeneracionResiduosData().getAnioInicio());
        writeCell(sheet, 22, 6, datosGenerales.getGeneracionResiduosData().getTemperatura());
        updateListCell(sheet, 27, 2, datosGenerales.getGeneracionResiduosData().getContenidoGrasas() ? "Sí" : "No");
        writeCell(sheet, 39, 2, datosGenerales.getGeneracionResiduosData().getTasaCrecimiento());
        updateListCell(sheet, 24, 2, datosGenerales.getGeneracionResiduosData().getCondicionSEDS().getNombre());
        int rowIndex = 31;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            writeCell(sheet, rowIndex, 1, detalle.getGeneracionResiduos().getAnio());
            writeCell(sheet, rowIndex, 2, detalle.getGeneracionResiduos().getProductosMadera());
            writeCell(sheet, rowIndex, 3, detalle.getGeneracionResiduos().getProductosPapel());
            writeCell(sheet, rowIndex, 4, detalle.getGeneracionResiduos().getResiduos());
            writeCell(sheet, rowIndex, 5, detalle.getGeneracionResiduos().getTextiles());
            writeCell(sheet, rowIndex, 6, detalle.getGeneracionResiduos().getJardines());
            writeCell(sheet, rowIndex, 7, detalle.getGeneracionResiduos().getPaniales());
            writeCell(sheet, rowIndex, 8, detalle.getGeneracionResiduos().getOtros());
            rowIndex++;
        }
    }
}
