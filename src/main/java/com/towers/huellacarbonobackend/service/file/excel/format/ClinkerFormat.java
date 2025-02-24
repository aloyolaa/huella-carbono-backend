package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.Clinker;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
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
public class ClinkerFormat implements ImportOperation, ExportOperation {
    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 28; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String cemento = readCell(row, 1);
            if (cemento == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setClinker(
                    new Clinker(
                            null,
                            cemento,
                            readDoubleCell(row, 2),
                            readDoubleCell(row, 3),
                            readDoubleCell(row, 4),
                            readDoubleCell(row, 5)
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    @Override
    public void writeData(Sheet sheet, DatosGenerales datosGenerales) {
        int rowIndex = 28;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            writeCell(sheet, rowIndex, 1, detalle.getClinker().getCemento());
            writeCell(sheet, rowIndex, 2, detalle.getClinker().getProduccionCemento());
            writeCell(sheet, rowIndex, 3, detalle.getClinker().getProduccionClinker());
            writeCell(sheet, rowIndex, 4, detalle.getClinker().getContenidoCaOClinker());
            writeCell(sheet, rowIndex, 5, detalle.getClinker().getContenidoCaOCaCO3());
            rowIndex++;
        }
    }
}
