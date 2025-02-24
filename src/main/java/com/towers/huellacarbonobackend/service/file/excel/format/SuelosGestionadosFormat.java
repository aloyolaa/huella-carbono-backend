package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.entity.data.SueloGestionado;
import com.towers.huellacarbonobackend.service.data.TipoSueloService;
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
public class SuelosGestionadosFormat implements ImportOperation, ExportOperation {
    private final TipoSueloService tipoSueloService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 23; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String tipoSuelo = readListCell(row, 1);
            if (tipoSuelo == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setSueloGestionado(
                    new SueloGestionado(
                            null,
                            tipoSueloService.getByNombre(tipoSuelo),
                            readDoubleCell(row, 4)
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
            updateListCell(sheet, rowIndex, 1, detalle.getSueloGestionado().getTipoSuelo().getNombre());
            writeCell(sheet, rowIndex, 3, detalle.getSueloGestionado().getTipoSuelo().getDescripcion());
            writeCell(sheet, rowIndex, 4, detalle.getSueloGestionado().getAreaGestionada());
            rowIndex++;
        }
    }
}
