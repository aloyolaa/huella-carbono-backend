package com.towers.huellacarbonobackend.service.file.excel.exp;

import com.towers.huellacarbonobackend.dto.ExportDto;
import com.towers.huellacarbonobackend.entity.Archivo;
import com.towers.huellacarbonobackend.entity.DatosGenerales;
import com.towers.huellacarbonobackend.service.data.DataService;
import com.towers.huellacarbonobackend.service.file.ftp.FtpFileStorageService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;

import static com.towers.huellacarbonobackend.service.file.excel.Commons.writeCommonData;

@Service
@RequiredArgsConstructor
public class ExportService {
    private final DataService dataService;
    private final FtpFileStorageService ftpFileStorageService;
    private final Map<Long, ExportOperation> exportOperations;

    public ExportDto handleExcelExport(Long id) {
        DatosGenerales datosGenerales = dataService.getById(id);
        Archivo archivo = datosGenerales.getArchivo();
        String fileName = archivo.getFichero();

        try (InputStream inputStream = new ByteArrayInputStream(ftpFileStorageService.loadFileAsResource(fileName));
             Workbook workbook = new XSSFWorkbook(inputStream);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.getSheetAt(0);
            writeCommonData(sheet, datosGenerales);
            ExportOperation exportOperation = exportOperations.get(archivo.getId());
            if (exportOperation != null) {
                exportOperation.writeData(sheet, datosGenerales);
            } else {
                throw new IllegalArgumentException("Unsupported archivo id: " + archivo.getId());
            }
            workbook.write(outputStream);
            return new ExportDto(fileName, Base64.getEncoder().encodeToString(outputStream.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
