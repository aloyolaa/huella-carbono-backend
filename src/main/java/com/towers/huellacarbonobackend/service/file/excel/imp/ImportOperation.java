package com.towers.huellacarbonobackend.service.file.excel.imp;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import org.apache.poi.ss.usermodel.Sheet;

public interface ImportOperation {
    void readData(Sheet sheet, DatosGenerales datosGenerales);
}
