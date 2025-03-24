package com.towers.huellacarbonobackend.service.file.excel.imp;

import com.towers.huellacarbonobackend.entity.data.Archivo;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Empresa;
import com.towers.huellacarbonobackend.service.data.DataService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.towers.huellacarbonobackend.service.file.excel.Commons.readCommonData;

@Service
@RequiredArgsConstructor
public class ImportService {
    private final DataService dataService;
    private final Map<Long, ImportOperation> importOperations;

    @Transactional
    public void handleExcelImport(Long empresaId, Long archivoId, Integer anio, Integer mes, MultipartFile file) {
        Optional<DatosGenerales> optionalDatosGenerales = dataService.getOptionalByEmpresaAndAnio(empresaId, archivoId, anio, mes);

        if (optionalDatosGenerales.isPresent()) {
            dataService.deleteById(optionalDatosGenerales.orElseThrow().getId());
        }

        DatosGenerales datosGenerales = new DatosGenerales();
        datosGenerales.setAnio(anio);
        datosGenerales.setMes(mes);
        datosGenerales.setArchivo(new Archivo(archivoId));
        datosGenerales.setEmpresa(new Empresa(empresaId));

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            readCommonData(sheet, datosGenerales);
            ImportOperation importOperation = importOperations.get(archivoId);
            if (importOperation != null) {
                importOperation.readData(sheet, datosGenerales);
            } else {
                throw new IllegalArgumentException("Unsupported archivo id: " + archivoId);
            }
            dataService.save(datosGenerales);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
