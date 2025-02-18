package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.DatosGenerales;
import com.towers.huellacarbonobackend.entity.Detalle;
import com.towers.huellacarbonobackend.entity.QuemaBiomasa;
import com.towers.huellacarbonobackend.service.data.ResiduoAgricolaService;
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
public class BiomasaFormat implements ImportOperation, ExportOperation {
    private final ResiduoAgricolaService residuoAgricolaService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String residuoAgricola = readListCell(row, 1);
            if (residuoAgricola == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setQuemaBiomasa(
                    new QuemaBiomasa(
                            null,
                            residuoAgricolaService.getByNombre(residuoAgricola),
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
        int rowIndex = 22;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            updateListCell(sheet, rowIndex, 1, detalle.getQuemaBiomasa().getResiduoAgricola().getNombre());
            writeCell(sheet, rowIndex, 3, detalle.getQuemaBiomasa().getAreaCultiva());
            writeCell(sheet, rowIndex, 4, detalle.getQuemaBiomasa().getAreaQuemada());
            writeCell(sheet, rowIndex, 5, detalle.getQuemaBiomasa().getProduccion());
            rowIndex++;
        }
    }
}
