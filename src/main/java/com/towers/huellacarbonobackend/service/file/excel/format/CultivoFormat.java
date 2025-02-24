package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.CultivoArroz;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.service.data.ResiduoService;
import com.towers.huellacarbonobackend.service.data.TipoCultivoService;
import com.towers.huellacarbonobackend.service.data.TipoFertilizanteService;
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
public class CultivoFormat implements ImportOperation, ExportOperation {
    private final TipoCultivoService tipoCultivoService;
    private final TipoFertilizanteService tipoFertilizanteService;
    private final ResiduoService residuoService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 24; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String tipoCultivo = readListCell(row, 1);
            if (tipoCultivo == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setCultivoArroz(
                    new CultivoArroz(
                            null,
                            tipoCultivoService.getByNombre(tipoCultivo),
                            readIntegerCell(row, 3),
                            readDoubleCell(row, 4),
                            tipoFertilizanteService.getByNombre(readListCell(row, 5)),
                            residuoService.getByNombre(readListCell(row, 7)),
                            readDoubleCell(row, 9),
                            readDoubleCell(row, 10)
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    @Override
    public void writeData(Sheet sheet, DatosGenerales datosGenerales) {
        int rowIndex = 24;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            updateListCell(sheet, rowIndex, 1, detalle.getCultivoArroz().getTipoCultivo().getNombre());
            writeCell(sheet, rowIndex, 3, detalle.getCultivoArroz().getPeriodoCultivo());
            writeCell(sheet, rowIndex, 4, detalle.getCultivoArroz().getAreaCultivo());
            updateListCell(sheet, rowIndex, 5, detalle.getCultivoArroz().getTipoFertilizante().getNombre());
            updateListCell(sheet, rowIndex, 7, detalle.getCultivoArroz().getResiduo().getNombre());
            writeCell(sheet, rowIndex, 9, detalle.getCultivoArroz().getContenidoNitrogeno());
            writeCell(sheet, rowIndex, 10, detalle.getCultivoArroz().getCantidadEmpleada());
            rowIndex++;
        }
    }
}
