package com.towers.huellacarbonobackend.service.file.excel;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Meses;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class Commons {
    public static void readCommonData(Sheet sheet, DatosGenerales datosGenerales) {
        datosGenerales.setNombre(readCell(sheet.getRow(7), 2));
        datosGenerales.setCargo(readCell(sheet.getRow(8), 2));
        datosGenerales.setCorreo(readCell(sheet.getRow(9), 2));
        datosGenerales.setLocacion(readCell(sheet.getRow(11), 2));
        datosGenerales.setComentarios(readCell(sheet.getRow(13), 2));
    }

    public static boolean hasDataInAnyMonth(Meses meses) {
        return meses.getEnero() != null || meses.getFebrero() != null || meses.getMarzo() != null ||
                meses.getAbril() != null || meses.getMayo() != null || meses.getJunio() != null ||
                meses.getJulio() != null || meses.getAgosto() != null || meses.getSeptiembre() != null ||
                meses.getOctubre() != null || meses.getNoviembre() != null || meses.getDiciembre() != null;
    }

    public static Meses readMeses(Row row, int startColIndex) {
        Meses meses = new Meses();
        int colIndex = startColIndex;
        meses.setEnero(readDoubleCell(row, colIndex++));
        meses.setFebrero(readDoubleCell(row, colIndex++));
        meses.setMarzo(readDoubleCell(row, colIndex++));
        meses.setAbril(readDoubleCell(row, colIndex++));
        meses.setMayo(readDoubleCell(row, colIndex++));
        meses.setJunio(readDoubleCell(row, colIndex++));
        meses.setJulio(readDoubleCell(row, colIndex++));
        meses.setAgosto(readDoubleCell(row, colIndex++));
        meses.setSeptiembre(readDoubleCell(row, colIndex++));
        meses.setOctubre(readDoubleCell(row, colIndex++));
        meses.setNoviembre(readDoubleCell(row, colIndex++));
        meses.setDiciembre(readDoubleCell(row, colIndex));
        return meses;
    }

    public static String readListCell(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell != null && cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        }
        return null;
    }

    public static String readCell(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell != null) {
            String value = cell.getStringCellValue().trim();
            return value.isBlank() ? null : value;
        }
        return null;
    }

    public static Double readDoubleCell(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell != null) {
            double value = cell.getNumericCellValue();
            return value == 0 ? null : value;
        }
        return null;
    }

    public static Integer readIntegerCell(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell != null) {
            int value = (int) cell.getNumericCellValue();
            return value == 0 ? null : value;
        }
        return null;
    }

    public static void writeCommonData(Sheet sheet, DatosGenerales datosGenerales) {
        writeCell(sheet, 7, 2, datosGenerales.getNombre());
        writeCell(sheet, 8, 2, datosGenerales.getCargo());
        writeCell(sheet, 9, 2, datosGenerales.getCorreo());
        writeCell(sheet, 11, 2, datosGenerales.getLocacion());
        writeCell(sheet, 13, 2, datosGenerales.getComentarios());
    }

    public static void writeMesesData(Sheet sheet, int rowIndex, int startColIndex, Meses meses) {
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

    public static void updateListCell(Sheet sheet, int rowIndex, int colIndex, String value) {
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

    public static void writeCell(Sheet sheet, int rowIndex, int colIndex, String value) {
        if (value == null) return;
        Row row = sheet.getRow(rowIndex);
        if (row == null) row = sheet.createRow(rowIndex);
        Cell cell = row.getCell(colIndex);
        if (cell == null) cell = row.createCell(colIndex);
        cell.setCellValue(value);
    }

    public static void writeCell(Sheet sheet, int rowIndex, int colIndex, Double value) {
        if (value == null) return;
        Row row = sheet.getRow(rowIndex);
        if (row == null) row = sheet.createRow(rowIndex);
        Cell cell = row.getCell(colIndex);
        if (cell == null) cell = row.createCell(colIndex);
        cell.setCellValue(value);
    }

    public static void writeCell(Sheet sheet, int rowIndex, int colIndex, Integer value) {
        if (value == null) return;
        Row row = sheet.getRow(rowIndex);
        if (row == null) row = sheet.createRow(rowIndex);
        Cell cell = row.getCell(colIndex);
        if (cell == null) cell = row.createCell(colIndex);
        cell.setCellValue(value);
    }
}
