package com.towers.huellacarbonobackend.service.file.excel.exp;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import org.apache.poi.ss.usermodel.Sheet;

public interface ExportOperation {
    void writeData(Sheet sheet, DatosGenerales datosGenerales);
}
