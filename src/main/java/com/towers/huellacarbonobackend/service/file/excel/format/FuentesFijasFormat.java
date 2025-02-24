package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.entity.data.Meses;
import com.towers.huellacarbonobackend.service.data.CategoriaInstitucionService;
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
public class FuentesFijasFormat implements ImportOperation, ExportOperation {
    private final TipoCombustibleService tipoCombustibleService;
    private final CategoriaInstitucionService categoriaInstitucionService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; rowIndex <= 35; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String tipoCombustibleNombre = readCell(row, 1);
                if (tipoCombustibleNombre != null) {
                    Meses meses = readMeses(row, 4);
                    if (hasDataInAnyMonth(meses)) {
                        Detalle detalle = new Detalle();
                        tipoCombustibleNombre = tipoCombustibleNombre.replaceAll("\\(\\*\\)", "").trim();
                        detalle.setTipoCombustible(tipoCombustibleService.getByNombreAndArchivo(tipoCombustibleNombre, datosGenerales.getArchivo().getId()));
                        detalle.setMeses(meses);
                        detalle.setCategoriaInstitucion(categoriaInstitucionService.getByNombre(readListCell(row, 3)));
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
        for (Detalle detalle : datosGenerales.getDetalles()) {
            for (int rowIndex = 22; rowIndex <= 35; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    String tipoCombustibleNombre = readCell(row, 1);
                    if (tipoCombustibleNombre != null) {
                        tipoCombustibleNombre = tipoCombustibleNombre.replaceAll("\\(\\*\\)", "").trim();
                        if (detalle.getTipoCombustible().getNombre().equalsIgnoreCase(tipoCombustibleNombre)) {
                            writeMesesData(sheet, rowIndex, 4, detalle.getMeses());
                            updateListCell(sheet, rowIndex, 3, detalle.getCategoriaInstitucion().getNombre());
                            break;
                        }
                    }
                }
            }
        }
    }
}
