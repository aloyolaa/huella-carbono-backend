package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.*;
import com.towers.huellacarbonobackend.service.data.TipoPFCService;
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
public class PFCFormat implements ImportOperation, ExportOperation {
    private final TipoPFCService tipoPFCService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);

            if (row == null) {
                break;
            }

            String equipoI = readCell(row, 1);
            String equipoO = readCell(row, 7);
            String equipoD = readCell(row, 14);

            if (equipoI == null && equipoO == null && equipoD == null) {
                break;
            }

            Detalle detalle = new Detalle();

            if (equipoI != null) {
                detalle.setPfcInstalacion(
                        new PFCInstalacion(
                                null,
                                equipoI,
                                tipoPFCService.getByNombre(readCell(row, 2)),
                                readIntegerCell(row, 3),
                                readDoubleCell(row, 4),
                                readDoubleCell(row, 5)
                        )
                );
            }

            if (equipoO != null) {
                detalle.setPfcOperacion(
                        new PFCOperacion(
                                null,
                                equipoO,
                                tipoPFCService.getByNombre(readCell(row, 8)),
                                readIntegerCell(row, 9),
                                readDoubleCell(row, 10),
                                readDoubleCell(row, 11),
                                readDoubleCell(row, 12)
                        )
                );
            }

            if (equipoD != null) {
                detalle.setPfcDisposicion(
                        new PFCDisposicion(
                                null,
                                equipoD,
                                tipoPFCService.getByNombre(readCell(row, 15)),
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

            PFCInstalacion pfcI = detalle.getPfcInstalacion();
            PFCOperacion pfcO = detalle.getPfcOperacion();
            PFCDisposicion pfcD = detalle.getPfcDisposicion();

            if (pfcI != null) {
                writePFCCommonData(sheet, rowIndex, 1, pfcI);
                writeCell(sheet, rowIndex, 5, pfcI.getFugaInstalacion());
            }

            if (pfcO != null) {
                writePFCCommonData(sheet, rowIndex, 7, pfcO);
                writeCell(sheet, rowIndex, 11, pfcO.getTiempoUso());
                writeCell(sheet, rowIndex, 12, pfcO.getFugaUso());
            }

            if (pfcD != null) {
                writePFCCommonData(sheet, rowIndex, 14, pfcD);
                writeCell(sheet, rowIndex, 18, pfcD.getFraccionGasPFCDisposicion());
                writeCell(sheet, rowIndex, 19, pfcD.getFraccionGasPFCRecuperado());
            }

            rowIndex++;
        }
    }

    private void writePFCCommonData(Sheet sheet, int rowIndex, int startColIndex, PFC pfc) {
        writeCell(sheet, rowIndex, startColIndex++, pfc.getDescripcionEquipo());
        updateListCell(sheet, rowIndex, startColIndex++, pfc.getTipoPFC().getNombre());
        writeCell(sheet, rowIndex, startColIndex++, pfc.getNumeroEquipos());
        writeCell(sheet, rowIndex, startColIndex, pfc.getCapacidadCarga());
    }
}
