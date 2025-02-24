package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.entity.data.GeneracionIndirectaNF3;
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
public class GeneracionIndirectaNF3Format implements ImportOperation, ExportOperation {
    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 23; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            Integer pantallas = readIntegerCell(row, 1);
            if (pantallas == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setGeneracionIndirectaNF3(
                    new GeneracionIndirectaNF3(
                            null,
                            pantallas,
                            readDoubleCell(row, 2),
                            readDoubleCell(row, 3)
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    @Override
    public void writeData(Sheet sheet, DatosGenerales datosGenerales) {
        int rowIndex = 23;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            writeCell(sheet, rowIndex, 1, detalle.getGeneracionIndirectaNF3().getNumeroPantallas());
            writeCell(sheet, rowIndex, 2, detalle.getGeneracionIndirectaNF3().getAlto());
            writeCell(sheet, rowIndex, 3, detalle.getGeneracionIndirectaNF3().getAncho());
            rowIndex++;
        }
    }
}
