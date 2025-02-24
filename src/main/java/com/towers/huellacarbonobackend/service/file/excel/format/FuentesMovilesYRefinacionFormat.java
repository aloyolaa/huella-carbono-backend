package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.entity.data.Meses;
import com.towers.huellacarbonobackend.entity.data.Seccion;
import com.towers.huellacarbonobackend.service.data.SeccionService;
import com.towers.huellacarbonobackend.service.data.TipoCombustibleService;
import com.towers.huellacarbonobackend.service.file.excel.exp.ExportOperation;
import com.towers.huellacarbonobackend.service.file.excel.imp.ImportOperation;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.towers.huellacarbonobackend.service.file.excel.Commons.*;

@Component
@RequiredArgsConstructor
public class FuentesMovilesYRefinacionFormat implements ImportOperation, ExportOperation {
    private final TipoCombustibleService tipoCombustibleService;
    private final SeccionService seccionService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        int endRowIndex = datosGenerales.getArchivo().getId() == 3 ? 50 : 37;
        List<Detalle> detalles = new ArrayList<>();
        Seccion currentSeccion = null;
        boolean lastCellWasSection = false;

        for (int rowIndex = 22; rowIndex <= endRowIndex; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String tipoCombustibleNombre = readCell(row, 1);
                if (tipoCombustibleNombre != null) {
                    Optional<Seccion> optionalSeccion = seccionService.getOptionalByNombre(tipoCombustibleNombre);
                    if (optionalSeccion.isPresent()) {
                        if (!lastCellWasSection) {
                            currentSeccion = optionalSeccion.get();
                            lastCellWasSection = true;
                        }
                    } else {
                        lastCellWasSection = false;
                        Meses meses = readMeses(row, 3);
                        if (hasDataInAnyMonth(meses)) {
                            Detalle detalle = new Detalle();
                            tipoCombustibleNombre = tipoCombustibleNombre.replaceAll("\\(\\*\\)", "").trim();
                            detalle.setTipoCombustible(tipoCombustibleService.getByNombreAndArchivoAndSeccion(tipoCombustibleNombre, datosGenerales.getArchivo().getId(), currentSeccion.getId()));
                            detalle.setMeses(meses);
                            detalle.setDatosGenerales(datosGenerales);
                            detalles.add(detalle);
                        }
                    }
                }
            }
        }
        datosGenerales.setDetalles(detalles);
    }

    @Override
    public void writeData(Sheet sheet, DatosGenerales datosGenerales) {
        Seccion currentSeccion = null;
        boolean lastCellWasSection = false;
        int endRowIndex = datosGenerales.getArchivo().getId() == 3 ? 50 : 37;

        for (int rowIndex = 22; rowIndex <= endRowIndex; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String tipoCombustibleNombre = readCell(row, 1);
                if (tipoCombustibleNombre != null) {
                    tipoCombustibleNombre = tipoCombustibleNombre.replaceAll("\\(\\*\\)", "").trim();
                    Optional<Seccion> optionalSeccion = seccionService.getOptionalByNombre(tipoCombustibleNombre);
                    if (optionalSeccion.isPresent()) {
                        if (!lastCellWasSection) {
                            currentSeccion = optionalSeccion.get();
                            lastCellWasSection = true;
                        }
                    } else {
                        lastCellWasSection = false;
                        for (Detalle detalle : datosGenerales.getDetalles()) {
                            if (detalle.getTipoCombustible().getNombre().equals(tipoCombustibleNombre) &&
                                    detalle.getTipoCombustible().getSeccion().equals(currentSeccion)) {
                                writeMesesData(sheet, rowIndex, 3, detalle.getMeses());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
