package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.*;
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
public class FugasSF6Format implements ImportOperation, ExportOperation {
    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 21; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);

            if (row == null) {
                break;
            }

            String equipoI = readCell(row, 1);
            String equipoO = readCell(row, 6);
            String equipoD = readCell(row, 12);

            if (equipoI == null && equipoO == null && equipoD == null) {
                break;
            }

            Detalle detalle = new Detalle();

            if (equipoI != null) {
                detalle.setFugaInstalacion(
                        new FugaInstalacion(
                                null,
                                equipoI,
                                readIntegerCell(row, 2),
                                readDoubleCell(row, 3),
                                readDoubleCell(row, 4)
                        )
                );
            }

            if (equipoO != null) {
                detalle.setFugaOperacion(
                        new FugaOperacion(
                                null,
                                equipoO,
                                readIntegerCell(row, 7),
                                readDoubleCell(row, 8),
                                readDoubleCell(row, 9),
                                readDoubleCell(row, 10)
                        )
                );
            }

            if (equipoD != null) {
                detalle.setFugaDisposicion(
                        new FugaDisposicion(
                                null,
                                equipoD,
                                readIntegerCell(row, 13),
                                readDoubleCell(row, 14),
                                readDoubleCell(row, 15),
                                readDoubleCell(row, 16)
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
        int rowIndex = 21;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            Row row = sheet.getRow(rowIndex);

            if (row == null) {
                sheet.createRow(rowIndex);
            }

            FugaInstalacion fugaI = detalle.getFugaInstalacion();
            FugaOperacion fugaO = detalle.getFugaOperacion();
            FugaDisposicion fugaD = detalle.getFugaDisposicion();

            if (fugaI != null) {
                writeFugasCommonData(sheet, rowIndex, 1, fugaI);
                writeCell(sheet, rowIndex, 4, fugaI.getFugaInstalacion());
            }

            if (fugaO != null) {
                writeFugasCommonData(sheet, rowIndex, 6, fugaO);
                writeCell(sheet, rowIndex, 9, fugaO.getTiempoUso());
                writeCell(sheet, rowIndex, 10, fugaO.getFugaUso());
            }

            if (fugaD != null) {
                writeFugasCommonData(sheet, rowIndex, 12, fugaD);
                writeCell(sheet, rowIndex, 15, fugaD.getFraccionSF6Disposicion());
                writeCell(sheet, rowIndex, 16, fugaD.getFraccionSF6Recuperado());
            }

            rowIndex++;
        }
    }

    private void writeFugasCommonData(Sheet sheet, int rowIndex, int startColIndex, Fuga fuga) {
        writeCell(sheet, rowIndex, startColIndex++, fuga.getDescripcionEquipo());
        writeCell(sheet, rowIndex, startColIndex++, fuga.getNumeroEquipos());
        writeCell(sheet, rowIndex, startColIndex, fuga.getCapacidadCarga());
    }
}
