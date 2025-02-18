package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.ExportDto;
import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.service.file.excel.exp.ExportService;
import com.towers.huellacarbonobackend.service.file.excel.imp.ImportService;
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

    @GetMapping("/export/{dataId}")
    public ResponseEntity<ResponseDto> exportToExcel(@PathVariable Long dataId) {
        ExportDto exportDto = exportService.handleExcelExport(dataId);
        return new ResponseEntity<>(
                new ResponseDto(exportDto, true)
                , HttpStatus.OK
        );
    }

    @PostMapping("/import/{empresa}/{archivo}")
    public ResponseEntity<ResponseDto> importExcel(@RequestParam MultipartFile file, @PathVariable Long empresa, @PathVariable Long archivo) {
        importService.handleExcelImport(empresa, archivo, file);
        return new ResponseEntity<>(
                new ResponseDto("Datos importados y guardados correctamente.", true)
                , HttpStatus.OK
        );
    }
}
