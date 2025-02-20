package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.ExportDto;
import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.service.file.excel.imp.ImportService;
import com.towers.huellacarbonobackend.service.file.excel.exp.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final ExportService exportService;
    private final ImportService importService;

    @GetMapping("/export/{archivoId}/{anio}")
    public ResponseEntity<ResponseDto> exportToExcel(@PathVariable Long archivoId, @PathVariable Integer anio) {
        ExportDto exportDto = exportService.handleExcelExport(archivoId, anio);
        return new ResponseEntity<>(
                new ResponseDto(exportDto, true)
                , HttpStatus.OK
        );
    }

    @PostMapping("/import/{empresa}/{archivo}/{anio}")
    public ResponseEntity<ResponseDto> importExcel(@RequestParam MultipartFile file, @PathVariable Long empresa, @PathVariable Long archivo, @PathVariable Integer anio) {
        importService.handleExcelImport(empresa, archivo, anio, file);
        return new ResponseEntity<>(
                new ResponseDto("Datos importados y guardados correctamente.", true)
                , HttpStatus.OK
        );
    }
}
