package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.DatosGenerales;
import com.towers.huellacarbonobackend.entity.Detalle;
import com.towers.huellacarbonobackend.entity.Meses;
import com.towers.huellacarbonobackend.entity.Seccion;
import com.towers.huellacarbonobackend.service.data.ActividadService;
import com.towers.huellacarbonobackend.service.data.SeccionService;
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
public class FugasProcesosFormat implements ImportOperation, ExportOperation {
    private final ActividadService actividadService;
    private final SeccionService seccionService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        Seccion currentSeccion = null;
        boolean lastCellWasSection = false;

        for (int rowIndex = 23; rowIndex <= 33; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String actividadNombre = readCell(row, 1);
                if (actividadNombre != null) {
                    if (actividadNombre.matches("^\\d.*")) {
                        actividadNombre = actividadNombre.substring(4).trim();
                    }
                    Optional<Seccion> optionalSeccion = seccionService.getOptionalByNombreContains(actividadNombre);
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
                            actividadNombre = actividadNombre.replaceAll("\\(\\*\\)", "").trim();
                            detalle.setActividad(actividadService.getByNombreAndArchivoAndSeccion(actividadNombre, datosGenerales.getArchivo().getId(), currentSeccion.getId()));
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

        for (int rowIndex = 23; rowIndex <= 33; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String actividadNombre = readCell(row, 1);
                if (actividadNombre != null) {
                    if (actividadNombre.matches("^\\d.*")) {
                        actividadNombre = actividadNombre.substring(4).trim();
                    }
                    actividadNombre = actividadNombre.replaceAll("\\(\\*\\)", "").trim();
                    Optional<Seccion> optionalSeccion = seccionService.getOptionalByNombreContains(actividadNombre);
                    if (optionalSeccion.isPresent()) {
                        if (!lastCellWasSection) {
                            currentSeccion = optionalSeccion.get();
                            lastCellWasSection = true;
                        }
                    } else {
                        lastCellWasSection = false;
                        for (Detalle detalle : datosGenerales.getDetalles()) {
                            if (detalle.getActividad().getNombre().equals(actividadNombre) &&
                                    detalle.getActividad().getSeccion().equals(currentSeccion)) {
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
