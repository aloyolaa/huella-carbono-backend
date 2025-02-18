package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.DatosGenerales;
import com.towers.huellacarbonobackend.entity.Detalle;
import com.towers.huellacarbonobackend.entity.Embalse;
import com.towers.huellacarbonobackend.service.data.ZonaService;
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
public class EmbalsesFormat implements ImportOperation, ExportOperation {
    private final ZonaService zonaService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String embalse = readListCell(row, 1);
            if (embalse == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setEmbalse(
                    new Embalse(
                            null,
                            embalse,
                            readCell(row, 2),
                            zonaService.getByNombre(readListCell(row, 3)),
                            readDoubleCell(row, 5),
                            readIntegerCell(row, 6),
                            readDoubleCell(row, 7)
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
            writeCell(sheet, rowIndex, 1, detalle.getEmbalse().getNombre());
            writeCell(sheet, rowIndex, 2, detalle.getEmbalse().getUbicacion());
            updateListCell(sheet, rowIndex, 3, detalle.getEmbalse().getZona().getNombre());
            writeCell(sheet, rowIndex, 5, detalle.getEmbalse().getArea());
            writeCell(sheet, rowIndex, 6, detalle.getEmbalse().getPeriodoLibreHielo());
            writeCell(sheet, rowIndex, 7, detalle.getEmbalse().getFraccionAreaInundada());
            rowIndex++;
        }
    }
}
