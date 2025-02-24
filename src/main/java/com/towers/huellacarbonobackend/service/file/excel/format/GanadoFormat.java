package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.entity.data.Ganado;
import com.towers.huellacarbonobackend.entity.data.GanadoData;
import com.towers.huellacarbonobackend.service.data.TipoAnimalService;
import com.towers.huellacarbonobackend.service.data.TipoTratamientoService;
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
public class GanadoFormat implements ImportOperation, ExportOperation {
    private final TipoAnimalService tipoAnimalService;
    private final TipoTratamientoService tipoTratamientoService;

    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        datosGenerales.setGanadoData(new GanadoData(null, readDoubleCell(sheet.getRow(19), 3), datosGenerales));
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 24; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String tipoAnimal = readListCell(row, 1);
            if (tipoAnimal == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setGanado(
                    new Ganado(
                            null,
                            tipoAnimalService.getByNombre(tipoAnimal),
                            tipoTratamientoService.getByNombre(readListCell(row, 3)),
                            readDoubleCell(row, 5),
                            readIntegerCell(row, 6)
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    @Override
    public void writeData(Sheet sheet, DatosGenerales datosGenerales) {
        writeCell(sheet, 19, 3, datosGenerales.getGanadoData().getTemperatura());
        int rowIndex = 24;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            updateListCell(sheet, rowIndex, 1, detalle.getGanado().getTipoAnimal().getNombre());
            updateListCell(sheet, rowIndex, 3, detalle.getGanado().getTipoTratamiento().getNombre());
            writeCell(sheet, rowIndex, 5, detalle.getGanado().getPesoPromedioAnimal());
            writeCell(sheet, rowIndex, 6, detalle.getGanado().getCantidadAnualAnimales());
            rowIndex++;
        }
    }
}
