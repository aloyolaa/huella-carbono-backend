package com.towers.huellacarbonobackend.service.file.excel.format;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.entity.data.TipoMovilidad;
import com.towers.huellacarbonobackend.entity.data.TransporteCasaTrabajo;
import com.towers.huellacarbonobackend.service.file.excel.exp.ExportOperation;
import com.towers.huellacarbonobackend.service.file.excel.imp.ImportOperation;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.towers.huellacarbonobackend.service.file.excel.Commons.*;

@Component
@RequiredArgsConstructor
public class TransporteCasaTrabajoFormat implements ImportOperation, ExportOperation {
    @Override
    public void readData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 23; ; rowIndex++) {
            List<TransporteCasaTrabajo> transportes = new ArrayList<>();
            Row row = sheet.getRow(rowIndex);

            if (row == null) {
                break;
            }

            String custer = readCell(row, 1);
            String combi = readCell(row, 7);
            String bus = readCell(row, 13);
            String tren = readCell(row, 19);
            String metropolitano = readCell(row, 25);
            String taxi = readCell(row, 31);
            String mototaxi = readCell(row, 37);
            String autoDB5 = readCell(row, 43);
            String autoGasohol = readCell(row, 49);
            String autoGLP = readCell(row, 55);
            String autoGNV = readCell(row, 61);

            if (custer == null && combi == null && bus == null &&
                    tren == null && metropolitano == null && taxi == null &&
                    mototaxi == null && autoDB5 == null && autoGasohol == null &&
                    autoGLP == null && autoGNV == null) {
                break;
            }

            Detalle detalle = new Detalle();

            if (custer != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, custer, 1L));
            }
            if (combi != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, combi, 2L));
            }
            if (bus != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, bus, 3L));
            }
            if (tren != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, tren, 4L));
            }
            if (metropolitano != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, metropolitano, 5L));
            }
            if (taxi != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, taxi, 6L));
            }
            if (mototaxi != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, mototaxi, 7L));
            }
            if (autoDB5 != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, autoDB5, 8L));
            }
            if (autoGasohol != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, autoGasohol, 9L));
            }
            if (autoGLP != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, autoGLP, 10L));
            }
            if (autoGNV != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, autoGNV, 11L));
            }

            detalle.setTransporteCasaTrabajos(transportes);
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private TransporteCasaTrabajo createTransporteCasaTrabajo(Row row, Detalle detalle, String descripcion, Long tipoMovilidadId) {
        int addCol = (tipoMovilidadId.intValue() - 1) * 6;
        return new TransporteCasaTrabajo(
                null,
                descripcion,
                readIntegerCell(row, 2 + addCol),
                readIntegerCell(row, 3 + addCol),
                readIntegerCell(row, 4 + addCol),
                readDoubleCell(row, 5 + addCol),
                new TipoMovilidad(tipoMovilidadId),
                detalle
        );
    }

    @Override
    public void writeData(Sheet sheet, DatosGenerales datosGenerales) {
        int rowIndex = 23;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            Row row = sheet.getRow(rowIndex);

            if (row == null) {
                sheet.createRow(rowIndex);
            }

            Optional<TransporteCasaTrabajo> custer = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 1).findFirst();
            Optional<TransporteCasaTrabajo> combi = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 2).findFirst();
            Optional<TransporteCasaTrabajo> bus = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 3).findFirst();
            Optional<TransporteCasaTrabajo> tren = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 4).findFirst();
            Optional<TransporteCasaTrabajo> metropolitano = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 5).findFirst();
            Optional<TransporteCasaTrabajo> taxi = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 6).findFirst();
            Optional<TransporteCasaTrabajo> mototaxi = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 7).findFirst();
            Optional<TransporteCasaTrabajo> autoDB5 = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 8).findFirst();
            Optional<TransporteCasaTrabajo> autoGasohol = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 9).findFirst();
            Optional<TransporteCasaTrabajo> autoGLP = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 10).findFirst();
            Optional<TransporteCasaTrabajo> autoGNV = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 11).findFirst();

            if (custer.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 1, custer.orElseThrow());
            }
            if (combi.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 7, combi.orElseThrow());
            }
            if (bus.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 13, bus.orElseThrow());
            }
            if (tren.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 19, tren.orElseThrow());
            }
            if (metropolitano.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 25, metropolitano.orElseThrow());
            }
            if (taxi.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 31, taxi.orElseThrow());
            }
            if (mototaxi.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 37, mototaxi.orElseThrow());
            }
            if (autoDB5.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 43, autoDB5.orElseThrow());
            }
            if (autoGasohol.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 49, autoGasohol.orElseThrow());
            }
            if (autoGLP.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 55, autoGLP.orElseThrow());
            }
            if (autoGNV.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 61, autoGNV.orElseThrow());
            }

            rowIndex++;
        }
    }

    private void writeTransporteCasaTrabajoCommonData(Sheet sheet, int rowIndex, int startColIndex, TransporteCasaTrabajo transporte) {
        writeCell(sheet, rowIndex, startColIndex++, transporte.getDescripcionPersonal());
        writeCell(sheet, rowIndex, startColIndex++, transporte.getTrabajadores());
        writeCell(sheet, rowIndex, startColIndex++, transporte.getViajesSemanales());
        writeCell(sheet, rowIndex, startColIndex++, transporte.getDiasLaborales());
        writeCell(sheet, rowIndex, startColIndex, transporte.getDistanciaViaje());
    }
}
