package com.towers.huellacarbonobackend.service.file;

import com.towers.huellacarbonobackend.dto.DataDto;
import com.towers.huellacarbonobackend.entity.*;
import com.towers.huellacarbonobackend.mapper.DataMapper;
import com.towers.huellacarbonobackend.service.data.CategoriaInstitucionService;
import com.towers.huellacarbonobackend.service.data.DataService;
import com.towers.huellacarbonobackend.service.data.TipoCombustibleService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImportService {
    private final DataService dataService;
    private final DataMapper dataMapper;
    private final TipoCombustibleService tipoCombustibleService;
    private final CategoriaInstitucionService categoriaInstitucionService;

    @Transactional
    public DataDto handleExcelImport(Long empresaId, Long archivoId, MultipartFile file) {
        Optional<DatosGenerales> optionalDatosGenerales = dataService.getByEmpresaAndAnio(empresaId, archivoId);

        if (optionalDatosGenerales.isPresent()) {
            dataService.deleteById(optionalDatosGenerales.orElseThrow().getId());
        }

        DatosGenerales datosGenerales = new DatosGenerales();
        datosGenerales.setArchivo(new Archivo(archivoId));
        datosGenerales.setEmpresa(new Empresa(empresaId));

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            readCommonData(sheet, datosGenerales);
            switch (archivoId.intValue()) {
                case 1, 3, 4:
                    readDetalleData(sheet, datosGenerales);
                    break;
                case 2:
                    readDetalleDataWithCategory(sheet, datosGenerales);
                    break;
                // Add more cases as needed
                default:
                    throw new IllegalArgumentException("Unsupported archivo id: " + archivoId);
            }
            DatosGenerales savedDatosGenerales = dataService.save(datosGenerales);
            return dataMapper.toDataDto(savedDatosGenerales);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readCommonData(Sheet sheet, DatosGenerales datosGenerales) {
        datosGenerales.setNombre(readCell(sheet, 7, 2));
        datosGenerales.setCargo(readCell(sheet, 8, 2));
        datosGenerales.setCorreo(readCell(sheet, 9, 2));
        datosGenerales.setLocacion(readCell(sheet, 11, 2));
        datosGenerales.setComentarios(readCell(sheet, 13, 2));
    }

    private void readDetalleData(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 23; rowIndex <= 36; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Cell cellB = row.getCell(1);
                if (cellB != null) {
                    Meses meses = readMeses(row, 3);
                    if (hasDataInAnyMonth(meses)) {
                        Detalle detalle = new Detalle();
                        String tipoCombustibleNombre = cellB.getStringCellValue().replaceAll("\\(\\*\\)", "").trim();
                        detalle.setTipoCombustible(tipoCombustibleService.getByNombreAndArchivo(tipoCombustibleNombre, datosGenerales.getArchivo().getId()));
                        detalle.setMeses(meses);
                        detalle.setDatosGenerales(datosGenerales);
                        detalles.add(detalle);
                    }
                }
            }
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readDetalleDataWithCategory(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; rowIndex <= 35; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Cell cellB = row.getCell(1);
                if (cellB != null) {
                    Meses meses = readMeses(row, 4);
                    if (hasDataInAnyMonth(meses)) {
                        Detalle detalle = new Detalle();
                        String tipoCombustibleNombre = cellB.getStringCellValue().replaceAll("\\(\\*\\)", "").trim();
                        detalle.setTipoCombustible(tipoCombustibleService.getByNombreAndArchivo(tipoCombustibleNombre, datosGenerales.getArchivo().getId()));
                        detalle.setMeses(meses);
                        detalle.setCategoriaInstitucion(categoriaInstitucionService.getByNombre(readListCell(row, 3)));
                        detalle.setDatosGenerales(datosGenerales);
                        detalles.add(detalle);
                    }
                }
            }
        }
        datosGenerales.setDetalles(detalles);
    }

    private boolean hasDataInAnyMonth(Meses meses) {
        return meses.getEnero() != null || meses.getFebrero() != null || meses.getMarzo() != null ||
                meses.getAbril() != null || meses.getMayo() != null || meses.getJunio() != null ||
                meses.getJulio() != null || meses.getAgosto() != null || meses.getSeptiembre() != null ||
                meses.getOctubre() != null || meses.getNoviembre() != null || meses.getDiciembre() != null;
    }

    private String readCell(Sheet sheet, int rowIndex, int colIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row != null) {
            Cell cell = row.getCell(colIndex);
            if (cell != null) {
                return cell.getStringCellValue();
            }
        }
        return null;
    }

    private Meses readMeses(Row row, int startColIndex) {
        Meses meses = new Meses();
        meses.setEnero(readDoubleCell(row, startColIndex));
        meses.setFebrero(readDoubleCell(row, startColIndex + 1));
        meses.setMarzo(readDoubleCell(row, startColIndex + 2));
        meses.setAbril(readDoubleCell(row, startColIndex + 3));
        meses.setMayo(readDoubleCell(row, startColIndex + 4));
        meses.setJunio(readDoubleCell(row, startColIndex + 5));
        meses.setJulio(readDoubleCell(row, startColIndex + 6));
        meses.setAgosto(readDoubleCell(row, startColIndex + 7));
        meses.setSeptiembre(readDoubleCell(row, startColIndex + 8));
        meses.setOctubre(readDoubleCell(row, startColIndex + 9));
        meses.setNoviembre(readDoubleCell(row, startColIndex + 10));
        meses.setDiciembre(readDoubleCell(row, startColIndex + 11));
        return meses;
    }

    private Double readDoubleCell(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell != null) {
            double value = cell.getNumericCellValue();
            return value == 0 ? null : value;
        }
        return null;
    }

    private Integer readIntegerCell(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell != null) {
            int value = (int) cell.getNumericCellValue();
            return value == 0 ? null : value;
        }
        return null;
    }

    private String readListCell(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell != null && cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        }
        return null;
    }
}
