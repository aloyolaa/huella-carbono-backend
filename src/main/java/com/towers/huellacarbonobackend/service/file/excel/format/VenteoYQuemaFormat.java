package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.*;
import com.towers.huellacarbonobackend.service.data.AccionService;
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
public class VenteoYQuemaFormat implements ImportOperation, ExportOperation {
    private final ActividadService actividadService;
    private final SeccionService seccionService;
    private final AccionService accionService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        Seccion currentSeccion = null;

        for (int rowIndex = 22; rowIndex <= 29; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String actividadNombre = readCell(row, 1);
                String accionNombre = readCell(row, 3);
                if (actividadNombre != null && accionNombre == null) {
                    Optional<Seccion> optionalSeccion = seccionService.getOptionalByNombre(actividadNombre);
                    if (optionalSeccion.isPresent()) {
                        currentSeccion = optionalSeccion.orElseThrow();
                    }
                }
                if (actividadNombre != null && accionNombre != null) {
                    Meses meses = readMeses(row, 4);
                    if (hasDataInAnyMonth(meses)) {
                        Detalle detalle = new Detalle();
                        actividadNombre = actividadNombre.replaceAll("\\(\\*\\)", "").trim();
                        Accion accion = accionService.getByNombre(accionNombre);
                        detalle.setActividad(actividadService.getByNombreAndArchivoAndSeccionAndAccion(actividadNombre, datosGenerales.getArchivo().getId(), currentSeccion.getId(), accion.getId()));
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
        Seccion currentSeccion = null;

        for (int rowIndex = 22; rowIndex <= 29; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String actividadNombre = readCell(row, 1);
                String accionNombre = readCell(row, 3);
                if (actividadNombre != null && accionNombre == null) {
                    actividadNombre = actividadNombre.replaceAll("\\(\\*\\)", "").trim();
                    Optional<Seccion> optionalSeccion = seccionService.getOptionalByNombre(actividadNombre);
                    if (optionalSeccion.isPresent()) {
                        currentSeccion = optionalSeccion.get();
                    }
                }
                if (actividadNombre != null && accionNombre != null) {
                    for (Detalle detalle : datosGenerales.getDetalles()) {
                        if (detalle.getActividad().getNombre().equals(actividadNombre) &&
                                detalle.getActividad().getSeccion().equals(currentSeccion) &&
                                detalle.getActividad().getAccion().getNombre().equals(accionNombre)) {
                            writeMesesData(sheet, rowIndex, 4, detalle.getMeses());
                            break;
                        }
                    }
                }
            }
        }
    }
}
