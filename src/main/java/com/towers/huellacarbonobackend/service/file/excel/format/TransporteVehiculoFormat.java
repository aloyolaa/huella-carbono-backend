package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.DatosGenerales;
import com.towers.huellacarbonobackend.entity.Detalle;
import com.towers.huellacarbonobackend.entity.TipoTransporte;
import com.towers.huellacarbonobackend.entity.TransporteVehiculo;
import com.towers.huellacarbonobackend.service.data.TipoTransporteService;
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
public class TransporteVehiculoFormat implements ImportOperation, ExportOperation {
    private final TipoTransporteService tipoTransporteService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 23; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String tramo = readCell(row, 1);
            if (tramo == null) {
                break;
            }

            int distanciaRecorridaRowIndex = 2;
            int personasViajandoRowIndex = 3;
            TipoTransporte tipoTransporte = null;
            if (datosGenerales.getArchivo().getId() == 25) {
                tipoTransporte = tipoTransporteService.getByNombre(readListCell(row, 2));
                distanciaRecorridaRowIndex = 3;
                personasViajandoRowIndex = 5;
            }

            Detalle detalle = new Detalle();
            detalle.setTransporteVehiculo(
                    new TransporteVehiculo(
                            null,
                            tramo,
                            readDoubleCell(row, distanciaRecorridaRowIndex),
                            readIntegerCell(row, personasViajandoRowIndex),
                            readIntegerCell(row, 4),
                            tipoTransporte
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

            int distanciaRecorridaRowIndex = 2;
            int personasViajandoRowIndex = 3;
            if (detalle.getTransporteVehiculo().getTipoTransporte() != null) {
                updateListCell(sheet, rowIndex, 2, detalle.getTransporteVehiculo().getTipoTransporte().getNombre());
                distanciaRecorridaRowIndex = 3;
                personasViajandoRowIndex = 5;
            }

            writeCell(sheet, rowIndex, 1, detalle.getTransporteVehiculo().getTramo());
            writeCell(sheet, rowIndex, distanciaRecorridaRowIndex, detalle.getTransporteVehiculo().getDistanciaRecorrida());
            writeCell(sheet, rowIndex, personasViajandoRowIndex, detalle.getTransporteVehiculo().getPersonasViajando());
            writeCell(sheet, rowIndex, 4, detalle.getTransporteVehiculo().getVecesRecorrido());
            rowIndex++;
        }
    }
}
