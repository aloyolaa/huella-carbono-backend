package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.ConsumoPapel;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.service.data.TipoHojaService;
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
public class ConsumoPapelFormat implements ImportOperation, ExportOperation {
    private final TipoHojaService tipoHojaService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; rowIndex <= 28; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String tipoHojaNombre = readCell(row, 1);
                if (tipoHojaNombre != null) {
                    Integer comprasAnuales = readIntegerCell(row, 2);
                    if (comprasAnuales != null) {
                        Detalle detalle = new Detalle();
                        detalle.setConsumoPapel(
                                new ConsumoPapel(
                                        null,
                                        tipoHojaService.getTipoHojaByNombre(tipoHojaNombre),
                                        comprasAnuales,
                                        readCell(row, 4),
                                        readDoubleCell(row, 5),
                                        readCell(row, 6),
                                        readDoubleCell(row, 7)
                                ));
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
            for (int rowIndex = 22; rowIndex <= 28; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    String tipoHojaNombre = readCell(row, 1);
                    if (detalle.getConsumoPapel().getTipoHoja().getNombre().equals(tipoHojaNombre)) {
                        ConsumoPapel consumoPapel = detalle.getConsumoPapel();
                        writeCell(sheet, rowIndex, 2, consumoPapel.getComprasAnuales());
                        writeCell(sheet, rowIndex, 5, consumoPapel.getReciclado());
                        writeCell(sheet, rowIndex, 6, consumoPapel.getCertificado());
                        writeCell(sheet, rowIndex, 7, consumoPapel.getDensidad());
                        break;
                    }

                }
            }
        }
    }
}
