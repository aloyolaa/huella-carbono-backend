package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.entity.data.Meses;
import com.towers.huellacarbonobackend.service.data.TipoCombustibleService;
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
public class GeneracionYOtraEnergiaFormat implements ImportOperation, ExportOperation {
    private final TipoCombustibleService tipoCombustibleService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        int startRowIndex = datosGenerales.getArchivo().getId() == 1 ? 23 : 22;
        int endRowIndex = datosGenerales.getArchivo().getId() == 1 ? 36 : 35;
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = startRowIndex; rowIndex <= endRowIndex; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String tipoCombustibleNombre = readCell(row, 1);
                if (tipoCombustibleNombre != null) {
                    Meses meses = readMeses(row, 3);
                    if (hasDataInAnyMonth(meses)) {
                        Detalle detalle = new Detalle();
                        tipoCombustibleNombre = tipoCombustibleNombre.replaceAll("\\(\\*\\)", "").trim();
                        detalle.setTipoCombustible(tipoCombustibleService.getByNombreAndArchivo(tipoCombustibleNombre, datosGenerales.getArchivo().getId()));
                        detalle.setMeses(meses);
                        detalle.setDatosGenerales(datosGenerales);
                        detalles.add(detalle);
                    }
                }
            }
        }
        datosGenerales.setDetalles(detalles);
    }

    @Override
    public void writeData(Sheet sheet, DatosGenerales datosGenerales) {
        int startRowIndex = datosGenerales.getArchivo().getId() == 1 ? 23 : 22;
        int endRowIndex = datosGenerales.getArchivo().getId() == 1 ? 36 : 35;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            for (int rowIndex = startRowIndex; rowIndex <= endRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    String tipoCombustibleNombre = readCell(row, 1);
                    if (tipoCombustibleNombre != null) {
                        tipoCombustibleNombre = tipoCombustibleNombre.replaceAll("\\(\\*\\)", "").trim();
                        if (detalle.getTipoCombustible().getNombre().equalsIgnoreCase(tipoCombustibleNombre)) {
                            writeMesesData(sheet, rowIndex, 3, detalle.getMeses());
                            break;
                        }
                    }
                }
            }
        }
    }
}
