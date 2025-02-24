package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.entity.data.Fertilizante;
import com.towers.huellacarbonobackend.service.data.ResiduoService;
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
public class FertilizantesFormat implements ImportOperation, ExportOperation {
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
            String tipoFertilizante = readListCell(row, 1);
            if (tipoFertilizante == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setFertilizante(
                    new Fertilizante(
                            null,
                            tipoFertilizanteService.getByNombre(tipoFertilizante),
                            residuoService.getByNombre(readListCell(row, 3)),
                            readDoubleCell(row, 5),
                            readDoubleCell(row, 6)
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
            updateListCell(sheet, rowIndex, 1, detalle.getFertilizante().getTipoFertilizante().getNombre());
            updateListCell(sheet, rowIndex, 3, detalle.getFertilizante().getResiduo().getNombre());
            writeCell(sheet, rowIndex, 5, detalle.getFertilizante().getContenidoNitrogeno());
            writeCell(sheet, rowIndex, 6, detalle.getFertilizante().getCantidadEmpleada());
            rowIndex++;
        }
    }
}
