package com.towers.huellacarbonobackend.controller;

import com.towers.huellacarbonobackend.dto.DataDto;
import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {
    private final DataService dataService;

    @PostMapping("/save/{empresa}/{archivo}")
    public ResponseEntity<ResponseDto> getAll(@RequestBody DataDto dataDto, @PathVariable Long empresa, @PathVariable Long archivo) {
        dataService.save(dataDto, empresa, archivo);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos guardados correctamente.",
                        true)
                , HttpStatus.OK
        );
    }
}
