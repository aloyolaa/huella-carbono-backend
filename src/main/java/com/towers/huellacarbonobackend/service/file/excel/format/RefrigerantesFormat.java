package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.*;
import com.towers.huellacarbonobackend.service.data.TipoEquipoService;
import com.towers.huellacarbonobackend.service.data.TipoRefrigeranteService;
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
public class RefrigerantesFormat implements ImportOperation, ExportOperation {
    private final TipoEquipoService tipoEquipoService;
    private final TipoRefrigeranteService tipoRefrigeranteService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String tipoEquipoI = readListCell(row, 1);
            String tipoEquipoO = readListCell(row, 7);
            String tipoEquipoD = readListCell(row, 14);
            if (tipoEquipoI == null && tipoEquipoO == null && tipoEquipoD == null) {
                break;
            }

            Detalle detalle = new Detalle();

            if (tipoEquipoI != null) {
                detalle.setRefrigeranteInstalacion(
                        new RefrigeranteInstalacion(
                                null,
                                tipoEquipoService.getByNombre(tipoEquipoI),
                                tipoRefrigeranteService.getByNombre(readListCell(row, 2)),
                                readIntegerCell(row, 3),
                                readDoubleCell(row, 4),
                                readDoubleCell(row, 5)
                        )
                );
            }

            if (tipoEquipoO != null) {
                detalle.setRefrigeranteOperacion(
                        new RefrigeranteOperacion(
                                null,
                                tipoEquipoService.getByNombre(tipoEquipoO),
                                tipoRefrigeranteService.getByNombre(readListCell(row, 8)),
                                readIntegerCell(row, 9),
                                readDoubleCell(row, 10),
                                readDoubleCell(row, 11),
                                readDoubleCell(row, 12)
                        )
                );
            }

            if (tipoEquipoD != null) {
                detalle.setRefrigeranteDisposicion(
                        new RefrigeranteDisposicion(
                                null,
                                tipoEquipoService.getByNombre(tipoEquipoD),
                                tipoRefrigeranteService.getByNombre(readListCell(row, 15)),
                                readIntegerCell(row, 16),
                                readDoubleCell(row, 17),
                                readDoubleCell(row, 18),
                                readDoubleCell(row, 19)
                        )
                );
            }

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

            RefrigeranteInstalacion refrigeranteI = detalle.getRefrigeranteInstalacion();
            RefrigeranteOperacion refrigeranteO = detalle.getRefrigeranteOperacion();
            RefrigeranteDisposicion refrigeranteD = detalle.getRefrigeranteDisposicion();

            if (refrigeranteI != null) {
                writeRefrigerantesCommonData(sheet, rowIndex, 1, refrigeranteI);
                writeCell(sheet, rowIndex, 5, refrigeranteI.getFugaInstalacion());
            }

            if (refrigeranteO != null) {
                writeRefrigerantesCommonData(sheet, rowIndex, 7, refrigeranteO);
                writeCell(sheet, rowIndex, 11, refrigeranteO.getAnio());
                writeCell(sheet, rowIndex, 12, refrigeranteO.getFugaUso());
            }

            if (refrigeranteD != null) {
                writeRefrigerantesCommonData(sheet, rowIndex, 14, refrigeranteD);
                writeCell(sheet, rowIndex, 18, refrigeranteD.getFraccionRefrigeranteDisposicion());
                writeCell(sheet, rowIndex, 19, refrigeranteD.getFraccionRefrigeranteRecuperado());
            }

            rowIndex++;
        }
    }

    private void writeRefrigerantesCommonData(Sheet sheet, int rowIndex, int startColIndex, Refrigerante refrigerante) {
        updateListCell(sheet, rowIndex, startColIndex++, refrigerante.getTipoEquipo().getNombre());
        updateListCell(sheet, rowIndex, startColIndex++, refrigerante.getTipoRefrigerante().getNombre());
        writeCell(sheet, rowIndex, startColIndex++, refrigerante.getNumeroEquipos());
        writeCell(sheet, rowIndex, startColIndex, refrigerante.getCapacidadCarga());
    }
}
