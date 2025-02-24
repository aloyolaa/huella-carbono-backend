package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.entity.data.TransporteMaterial;
import com.towers.huellacarbonobackend.service.data.TipoVehiculoService;
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
public class TransporteMaterialFormat implements ImportOperation, ExportOperation {
    private final TipoVehiculoService tipoVehiculoService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        int startRowIndex = datosGenerales.getArchivo().getId() == 22 ? 22 : 24;
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = startRowIndex; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String descripcion = readCell(row, 1);
            if (descripcion == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setTransporteMaterial(
                    new TransporteMaterial(
                            null,
                            descripcion,
                            readIntegerCell(row, 2),
                            readCell(row, 3),
                            readDoubleCell(row, 4),
                            readDoubleCell(row, 5),
                            tipoVehiculoService.getByNombre(readListCell(row, 6))
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    @Override
    public void writeData(Sheet sheet, DatosGenerales datosGenerales) {
        int startRowIndex = datosGenerales.getArchivo().getId() == 22 ? 22 : 24;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            Row row = sheet.getRow(startRowIndex);
            if (row == null) {
                sheet.createRow(startRowIndex);
            }
            writeCell(sheet, startRowIndex, 1, detalle.getTransporteMaterial().getDescripcion());
            writeCell(sheet, startRowIndex, 2, detalle.getTransporteMaterial().getViajes());
            writeCell(sheet, startRowIndex, 3, detalle.getTransporteMaterial().getTramo());
            writeCell(sheet, startRowIndex, 4, detalle.getTransporteMaterial().getPesoTransportado());
            writeCell(sheet, startRowIndex, 5, detalle.getTransporteMaterial().getDistanciaRecorrida());
            updateListCell(sheet, startRowIndex, 6, detalle.getTransporteMaterial().getTipoVehiculo().getNombre());
            startRowIndex++;
        }
    }
}
