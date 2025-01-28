package com.towers.huellacarbonobackend.service.file;

import com.towers.huellacarbonobackend.dto.ExportDto;
import com.towers.huellacarbonobackend.entity.*;
import com.towers.huellacarbonobackend.service.data.DataService;
import com.towers.huellacarbonobackend.service.data.SeccionService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExportService {
    private final DataService dataService;
    private final SeccionService seccionService;
    private final FtpFileStorageService ftpFileStorageService;

    public ExportDto handleExcelExport(Long id) {
        DatosGenerales datosGenerales = dataService.getById(id);
        Archivo archivo = datosGenerales.getArchivo();
        String fileName = archivo.getFichero();

        try (InputStream inputStream = new ByteArrayInputStream(ftpFileStorageService.loadFileAsResource(fileName));
             Workbook workbook = new XSSFWorkbook(inputStream);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.getSheetAt(0);
            writeCommonData(sheet, datosGenerales);
            switch (archivo.getId().intValue()) {
                case 1:
                    writeGeneracionElectricidad(sheet, datosGenerales.getDetalles(), 23, 36, 3);
                    break;
                case 2:
                    writeFuentesFijas(sheet, datosGenerales);
                    break;
                case 3:
                    writeFuentesMovilesYRefinacion(sheet, datosGenerales.getDetalles(), 22, 50, 3);
                    break;
                case 4:
                    writeFuentesMovilesYRefinacion(sheet, datosGenerales.getDetalles(), 22, 37, 3);
                    break;
                case 5:
                    writeVenteoYQuema(sheet, datosGenerales.getDetalles(), 22, 29, 4);
                    break;
                case 6:
                    writeFugasProcesos(sheet, datosGenerales.getDetalles(), 23, 33, 3);
                    break;
                case 7:
                    writeClinker(sheet, datosGenerales.getDetalles(), 28);
                    break;
                case 8:
                    writeRefrigerantes(sheet, datosGenerales.getDetalles(), 22);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported archivo id: " + archivo.getId());
            }
            workbook.write(outputStream);
            return new ExportDto(fileName, Base64.getEncoder().encodeToString(outputStream.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeCommonData(Sheet sheet, DatosGenerales datosGenerales) {
        writeCell(sheet, 7, 2, datosGenerales.getNombre());
        writeCell(sheet, 8, 2, datosGenerales.getCargo());
        writeCell(sheet, 9, 2, datosGenerales.getCorreo());
        writeCell(sheet, 11, 2, datosGenerales.getLocacion());
        writeCell(sheet, 13, 2, datosGenerales.getComentarios());
    }

    private void writeGeneracionElectricidad(Sheet sheet, List<Detalle> detalles, int startRowIndex, int endRowIndex, int startColIndex) {
        for (Detalle detalle : detalles) {
            for (int rowIndex = startRowIndex; rowIndex <= endRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Cell cellB = row.getCell(1);
                    String tipoCombustibleNombre = cellB.getStringCellValue().replaceAll("\\(\\*\\)", "").trim();
                    if (cellB != null && detalle.getTipoCombustible().getNombre().equals(tipoCombustibleNombre)) {
                        writeMesesData(sheet, rowIndex, startColIndex, detalle.getMeses());
                        break;
                    }
                }
            }
        }
    }

    private void writeFuentesFijas(Sheet sheet, DatosGenerales datosGenerales) {
        for (Detalle detalle : datosGenerales.getDetalles()) {
            for (int rowIndex = 22; rowIndex <= 35; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Cell cellB = row.getCell(1);
                    String tipoCombustibleNombre = cellB.getStringCellValue().replaceAll("\\(\\*\\)", "").trim();
                    if (cellB != null && detalle.getTipoCombustible().getNombre().equals(tipoCombustibleNombre)) {
                        writeMesesData(sheet, rowIndex, 4, detalle.getMeses());
                        updateListCell(sheet, rowIndex, 3, detalle.getCategoriaInstitucion().getNombre());
                        break;
                    }
                }
            }
        }
    }

    private void writeFuentesMovilesYRefinacion(Sheet sheet, List<Detalle> detalles, int startRowIndex, int endRowIndex, int startColIndex) {
        Seccion currentSeccion = null;
        boolean lastCellWasSection = false;

        for (int rowIndex = startRowIndex; rowIndex <= endRowIndex; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Cell cellB = row.getCell(1);
                if (cellB != null) {
                    String cellValue = cellB.getStringCellValue().trim();
                    Optional<Seccion> optionalSeccion = seccionService.getOptionalByNombre(cellValue);
                    if (optionalSeccion.isPresent()) {
                        if (!lastCellWasSection) {
                            currentSeccion = optionalSeccion.get();
                            lastCellWasSection = true;
                        }
                    } else {
                        lastCellWasSection = false;
                        for (Detalle detalle : detalles) {
                            if (detalle.getTipoCombustible().getNombre().equals(cellValue) &&
                                    detalle.getTipoCombustible().getSeccion().equals(currentSeccion)) {
                                writeMesesData(sheet, rowIndex, startColIndex, detalle.getMeses());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void writeVenteoYQuema(Sheet sheet, List<Detalle> detalles, int startRowIndex, int endRowIndex, int startColIndex) {
        Seccion currentSeccion = null;
        boolean lastCellWasSection = false;

        for (int rowIndex = startRowIndex; rowIndex <= endRowIndex; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Cell cellB = row.getCell(1);
                Cell cellD = row.getCell(3);
                if (cellB != null && cellD != null) {
                    String cellValue = cellB.getStringCellValue().trim();
                    String accionNombre = cellD.getStringCellValue().trim();
                    Optional<Seccion> optionalSeccion = seccionService.getOptionalByNombre(cellValue);
                    if (optionalSeccion.isPresent()) {
                        if (!lastCellWasSection) {
                            currentSeccion = optionalSeccion.get();
                            lastCellWasSection = true;
                        }
                    } else {
                        lastCellWasSection = false;
                        for (Detalle detalle : detalles) {
                            if (detalle.getActividad().getNombre().equals(cellValue) &&
                                    detalle.getActividad().getSeccion().equals(currentSeccion) &&
                                    detalle.getActividad().getAccion().getNombre().equals(accionNombre)) {
                                writeMesesData(sheet, rowIndex, startColIndex, detalle.getMeses());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void writeFugasProcesos(Sheet sheet, List<Detalle> detalles, int startRowIndex, int endRowIndex, int startColIndex) {
        Seccion currentSeccion = null;
        boolean lastCellWasSection = false;

        for (int rowIndex = startRowIndex; rowIndex <= endRowIndex; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Cell cellB = row.getCell(1);
                if (cellB != null) {
                    String cellValue = cellB.getStringCellValue().trim();
                    if (cellValue.matches("^\\d.*")) {
                        cellValue = cellValue.substring(4).trim();
                    }
                    Optional<Seccion> optionalSeccion = seccionService.getOptionalByNombreContains(cellValue);
                    if (optionalSeccion.isPresent()) {
                        if (!lastCellWasSection) {
                            currentSeccion = optionalSeccion.get();
                            lastCellWasSection = true;
                        }
                    } else {
                        lastCellWasSection = false;
                        for (Detalle detalle : detalles) {
                            if (detalle.getActividad().getNombre().equals(cellValue) &&
                                    detalle.getActividad().getSeccion().equals(currentSeccion)) {
                                writeMesesData(sheet, rowIndex, startColIndex, detalle.getMeses());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void writeClinker(Sheet sheet, List<Detalle> detalles, int startRowIndex) {
        int rowIndex = startRowIndex;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            writeCell(sheet, rowIndex, 1, detalle.getClinker().getCemento());
            writeCell(sheet, rowIndex, 2, detalle.getClinker().getProduccionCemento());
            writeCell(sheet, rowIndex, 3, detalle.getClinker().getProduccionClinker());
            writeCell(sheet, rowIndex, 4, detalle.getClinker().getContenidoCaOClinker());
            writeCell(sheet, rowIndex, 5, detalle.getClinker().getContenidoCaOCaCO3());
            rowIndex++;
        }
    }

    private void writeRefrigerantes(Sheet sheet, List<Detalle> detalles, int startRowIndex) {
        int rowIndex = startRowIndex;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);

            if (row == null) {
                sheet.createRow(rowIndex);
            }

            RefrigeranteInstalacion refrigeranteInstalacion = detalle.getRefrigeranteInstalacion();
            RefrigeranteOperacion refrigeranteOperacion = detalle.getRefrigeranteOperacion();
            RefrigeranteDisposicion refrigeranteDisposicion = detalle.getRefrigeranteDisposicion();

            if (refrigeranteInstalacion != null) {
                writeRefrigerantesCommonData(sheet, rowIndex, 1, refrigeranteInstalacion);
                writeCell(sheet, rowIndex, 5, refrigeranteInstalacion.getFugaInstalacion());
            }

            if (refrigeranteOperacion != null) {
                writeRefrigerantesCommonData(sheet, rowIndex, 7, refrigeranteOperacion);
                writeCell(sheet, rowIndex, 11, refrigeranteOperacion.getAnio());
                writeCell(sheet, rowIndex, 12, refrigeranteOperacion.getFugaUso());
            }

            if (refrigeranteDisposicion != null) {
                writeRefrigerantesCommonData(sheet, rowIndex, 14, refrigeranteDisposicion);
                writeCell(sheet, rowIndex, 18, refrigeranteDisposicion.getFraccionRefrigeranteDisposicion());
                writeCell(sheet, rowIndex, 19, refrigeranteDisposicion.getFraccionRefrigeranteRecuperado());
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

    private void writeMesesData(Sheet sheet, int rowIndex, int startColIndex, Meses meses) {
        int colIndex = startColIndex;
        writeCell(sheet, rowIndex, colIndex++, meses.getEnero());
        writeCell(sheet, rowIndex, colIndex++, meses.getFebrero());
        writeCell(sheet, rowIndex, colIndex++, meses.getMarzo());
        writeCell(sheet, rowIndex, colIndex++, meses.getAbril());
        writeCell(sheet, rowIndex, colIndex++, meses.getMayo());
        writeCell(sheet, rowIndex, colIndex++, meses.getJunio());
        writeCell(sheet, rowIndex, colIndex++, meses.getJulio());
        writeCell(sheet, rowIndex, colIndex++, meses.getAgosto());
        writeCell(sheet, rowIndex, colIndex++, meses.getSeptiembre());
        writeCell(sheet, rowIndex, colIndex++, meses.getOctubre());
        writeCell(sheet, rowIndex, colIndex++, meses.getNoviembre());
        writeCell(sheet, rowIndex, colIndex, meses.getDiciembre());
    }

    private void updateListCell(Sheet sheet, int rowIndex, int colIndex, String value) {
        Cell cell = sheet.getRow(rowIndex).getCell(colIndex);
        if (cell != null && cell.getCellType() == CellType.STRING) {
            String[] items = cell.getStringCellValue().split(",");
            for (String item : items) {
                if (value.equals(item.trim())) {
                    cell.setCellValue(value);
                    break;
                }
            }
        } else if (cell != null && cell.getCellType() == CellType.BLANK) {
            cell.setCellValue(value);
        }
    }

    private void writeCell(Sheet sheet, int rowIndex, int colIndex, String value) {
        if (value == null) return;
        Row row = sheet.getRow(rowIndex);
        if (row == null) row = sheet.createRow(rowIndex);
        Cell cell = row.getCell(colIndex);
        if (cell == null) cell = row.createCell(colIndex);
        cell.setCellValue(value);
    }

    private void writeCell(Sheet sheet, int rowIndex, int colIndex, Double value) {
        if (value == null) return;
        Row row = sheet.getRow(rowIndex);
        if (row == null) row = sheet.createRow(rowIndex);
        Cell cell = row.getCell(colIndex);
        if (cell == null) cell = row.createCell(colIndex);
        cell.setCellValue(value);
    }

    private void writeCell(Sheet sheet, int rowIndex, int colIndex, Integer value) {
        if (value == null) return;
        Row row = sheet.getRow(rowIndex);
        if (row == null) row = sheet.createRow(rowIndex);
        Cell cell = row.getCell(colIndex);
        if (cell == null) cell = row.createCell(colIndex);
        cell.setCellValue(value);
    }
}
