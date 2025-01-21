package com.towers.huellacarbonobackend.service.file;

import com.towers.huellacarbonobackend.dto.ExportDto;
import com.towers.huellacarbonobackend.entity.Archivo;
import com.towers.huellacarbonobackend.entity.DatosGenerales;
import com.towers.huellacarbonobackend.entity.Detalle;
import com.towers.huellacarbonobackend.service.data.DataService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportService {
    private final DataService dataService;
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
                case 1, 3, 4:
                    writeDetalleData(sheet, datosGenerales.getDetalles());
                    break;
                case 2:
                    writeDetalleDataWithCategory(sheet, datosGenerales);
                    break;
                // Add more cases as needed
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

    private void writeDetalleData(Sheet sheet, List<Detalle> detalles) {
        for (Detalle detalle : detalles) {
            for (int rowIndex = 23; rowIndex <= 36; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Cell cellB = row.getCell(1);
                    String tipoCombustibleNombre = cellB.getStringCellValue().replaceAll("\\(\\*\\)", "").trim();
                    if (cellB != null && detalle.getTipoCombustible().getNombre().equals(tipoCombustibleNombre)) {
                        writeCell(sheet, rowIndex, 3, detalle.getMeses().getEnero());
                        writeCell(sheet, rowIndex, 4, detalle.getMeses().getFebrero());
                        writeCell(sheet, rowIndex, 5, detalle.getMeses().getMarzo());
                        writeCell(sheet, rowIndex, 6, detalle.getMeses().getAbril());
                        writeCell(sheet, rowIndex, 7, detalle.getMeses().getMayo());
                        writeCell(sheet, rowIndex, 8, detalle.getMeses().getJunio());
                        writeCell(sheet, rowIndex, 9, detalle.getMeses().getJulio());
                        writeCell(sheet, rowIndex, 10, detalle.getMeses().getAgosto());
                        writeCell(sheet, rowIndex, 11, detalle.getMeses().getSeptiembre());
                        writeCell(sheet, rowIndex, 12, detalle.getMeses().getOctubre());
                        writeCell(sheet, rowIndex, 13, detalle.getMeses().getNoviembre());
                        writeCell(sheet, rowIndex, 14, detalle.getMeses().getDiciembre());
                        break;
                    }
                }
            }
        }
    }

    private void writeDetalleDataWithCategory(Sheet sheet, DatosGenerales datosGenerales) {
        for (Detalle detalle : datosGenerales.getDetalles()) {
            for (int rowIndex = 22; rowIndex <= 35; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Cell cellB = row.getCell(1);
                    String tipoCombustibleNombre = cellB.getStringCellValue().replaceAll("\\(\\*\\)", "").trim();
                    if (cellB != null && detalle.getTipoCombustible().getNombre().equals(tipoCombustibleNombre)) {
                        writeCell(sheet, rowIndex, 4, detalle.getMeses().getEnero());
                        writeCell(sheet, rowIndex, 5, detalle.getMeses().getFebrero());
                        writeCell(sheet, rowIndex, 6, detalle.getMeses().getMarzo());
                        writeCell(sheet, rowIndex, 7, detalle.getMeses().getAbril());
                        writeCell(sheet, rowIndex, 8, detalle.getMeses().getMayo());
                        writeCell(sheet, rowIndex, 9, detalle.getMeses().getJunio());
                        writeCell(sheet, rowIndex, 10, detalle.getMeses().getJulio());
                        writeCell(sheet, rowIndex, 11, detalle.getMeses().getAgosto());
                        writeCell(sheet, rowIndex, 12, detalle.getMeses().getSeptiembre());
                        writeCell(sheet, rowIndex, 13, detalle.getMeses().getOctubre());
                        writeCell(sheet, rowIndex, 14, detalle.getMeses().getNoviembre());
                        writeCell(sheet, rowIndex, 15, detalle.getMeses().getDiciembre());

                        updateListCell(sheet, rowIndex, 3, detalle.getCategoriaInstitucion().getNombre());
                        break;
                    }
                }
            }
        }
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
