package com.towers.huellacarbonobackend.service.file;

import com.towers.huellacarbonobackend.dto.ExportDto;
import com.towers.huellacarbonobackend.entity.data.*;
import com.towers.huellacarbonobackend.service.data.DataService;
import com.towers.huellacarbonobackend.service.data.SeccionService;
import com.towers.huellacarbonobackend.service.file.ftp.FtpFileStorageService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExportAllService {
    private final DataService dataService;
    private final SeccionService seccionService;
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
                case 1:
                    writeGeneracionYOtraEnergia(sheet, datosGenerales.getDetalles(), 23, 36);
                    break;
                case 2:
                    writeFuentesFijas(sheet, datosGenerales);
                    break;
                case 3:
                    writeFuentesMovilesYRefinacion(sheet, datosGenerales.getDetalles(), 50);
                    break;
                case 4:
                    writeFuentesMovilesYRefinacion(sheet, datosGenerales.getDetalles(), 37);
                    break;
                case 5:
                    writeVenteoYQuema(sheet, datosGenerales.getDetalles());
                    break;
                case 6:
                    writeFugasProcesos(sheet, datosGenerales.getDetalles());
                    break;
                case 7:
                    writeClinker(sheet, datosGenerales.getDetalles());
                    break;
                case 8:
                    writeRefrigerantes(sheet, datosGenerales.getDetalles());
                    break;
                case 9:
                    writeFugasSF6(sheet, datosGenerales.getDetalles());
                    break;
                case 10:
                    writePFC(sheet, datosGenerales.getDetalles());
                    break;
                case 11:
                    writeGanado(sheet, datosGenerales);
                    break;
                case 12:
                    writeFertilizantes(sheet, datosGenerales.getDetalles());
                    break;
                case 13:
                    writeEncalado(sheet, datosGenerales.getDetalles());
                    break;
                case 14:
                    writeSuelosGestionados(sheet, datosGenerales.getDetalles());
                    break;
                case 15:
                    writeCultivo(sheet, datosGenerales.getDetalles());
                    break;
                case 16:
                    writeBiomasa(sheet, datosGenerales.getDetalles());
                    break;
                case 17:
                    writeEmbalses(sheet, datosGenerales.getDetalles());
                    break;
                case 18:
                    writeConsumoElectricidad(sheet, datosGenerales.getDetalles());
                    break;
                case 19, 20:
                    writePerdidas(sheet, datosGenerales.getDetalles());
                    break;
                case 21:
                    writeGeneracionYOtraEnergia(sheet, datosGenerales.getDetalles(), 22, 35);
                    break;
                case 22:
                    writeTransporteMaterial(sheet, datosGenerales.getDetalles(), 22);
                    break;
                case 23:
                    writeTransporteMaterial(sheet, datosGenerales.getDetalles(), 24);
                    break;
                case 24, 25:
                    writeTransporteVehiculo(sheet, datosGenerales.getDetalles());
                    break;
                case 26:
                    writeTransporteCasaTrabajo(sheet, datosGenerales.getDetalles());
                    break;
                case 27:
                    writeConsumoAgua(sheet, datosGenerales.getDetalles());
                    break;
                case 28:
                    writeConsumoPapel(sheet, datosGenerales.getDetalles());
                    break;
                case 29:
                    writeGeneracionIndirectaNF3(sheet, datosGenerales.getDetalles());
                    break;
                case 30:
                    writeGeneracionResiduos(sheet, datosGenerales.getDetalles());
                    break;
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

    private void writeGeneracionYOtraEnergia(Sheet sheet, List<Detalle> detalles, int startRowIndex, int endRowIndex) {
        for (Detalle detalle : detalles) {
            for (int rowIndex = startRowIndex; rowIndex <= endRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    String tipoCombustibleNombre = readCell(row, 1);
                    if (tipoCombustibleNombre != null) {
                        tipoCombustibleNombre = tipoCombustibleNombre.replaceAll("\\(\\*\\)", "").trim();
                        if (detalle.getTipoCombustible().getNombre().equalsIgnoreCase(tipoCombustibleNombre)) {
                            writeMesesData(sheet, rowIndex, 3, detalle.getMeses());
                            break;
                        }
                    }
                }
            }
        }
    }

    private void writeFuentesFijas(Sheet sheet, DatosGenerales datosGenerales) {
        for (Detalle detalle : datosGenerales.getDetalles()) {
            for (int rowIndex = 22; rowIndex <= 35; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    String tipoCombustibleNombre = readCell(row, 1);
                    if (tipoCombustibleNombre != null) {
                        tipoCombustibleNombre = tipoCombustibleNombre.replaceAll("\\(\\*\\)", "").trim();
                        if (detalle.getTipoCombustible().getNombre().equalsIgnoreCase(tipoCombustibleNombre)) {
                            writeMesesData(sheet, rowIndex, 4, detalle.getMeses());
                            updateListCell(sheet, rowIndex, 3, detalle.getCategoriaInstitucion().getNombre());
                            break;
                        }
                    }
                }
            }
        }
    }

    private void writeFuentesMovilesYRefinacion(Sheet sheet, List<Detalle> detalles, int endRowIndex) {
        Seccion currentSeccion = null;
        boolean lastCellWasSection = false;

        for (int rowIndex = 22; rowIndex <= endRowIndex; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String tipoCombustibleNombre = readCell(row, 1);
                if (tipoCombustibleNombre != null) {
                    tipoCombustibleNombre = tipoCombustibleNombre.replaceAll("\\(\\*\\)", "").trim();
                    Optional<Seccion> optionalSeccion = seccionService.getOptionalByNombre(tipoCombustibleNombre);
                    if (optionalSeccion.isPresent()) {
                        if (!lastCellWasSection) {
                            currentSeccion = optionalSeccion.get();
                            lastCellWasSection = true;
                        }
                    } else {
                        lastCellWasSection = false;
                        for (Detalle detalle : detalles) {
                            if (detalle.getTipoCombustible().getNombre().equals(tipoCombustibleNombre) &&
                                    detalle.getTipoCombustible().getSeccion().equals(currentSeccion)) {
                                writeMesesData(sheet, rowIndex, 3, detalle.getMeses());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void writeVenteoYQuema(Sheet sheet, List<Detalle> detalles) {
        Seccion currentSeccion = null;

        for (int rowIndex = 22; rowIndex <= 29; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String actividadNombre = readCell(row, 1);
                String accionNombre = readCell(row, 3);
                if (actividadNombre != null && accionNombre == null) {
                    actividadNombre = actividadNombre.replaceAll("\\(\\*\\)", "").trim();
                    Optional<Seccion> optionalSeccion = seccionService.getOptionalByNombre(actividadNombre);
                    if (optionalSeccion.isPresent()) {
                        currentSeccion = optionalSeccion.get();
                    }
                }
                if (actividadNombre != null && accionNombre != null) {
                    for (Detalle detalle : detalles) {
                        if (detalle.getActividad().getNombre().equals(actividadNombre) &&
                                detalle.getActividad().getSeccion().equals(currentSeccion) &&
                                detalle.getActividad().getAccion().getNombre().equals(accionNombre)) {
                            writeMesesData(sheet, rowIndex, 4, detalle.getMeses());
                            break;
                        }
                    }
                }
            }
        }
    }

    private void writeFugasProcesos(Sheet sheet, List<Detalle> detalles) {
        Seccion currentSeccion = null;
        boolean lastCellWasSection = false;

        for (int rowIndex = 23; rowIndex <= 33; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String actividadNombre = readCell(row, 1);
                if (actividadNombre != null) {
                    if (actividadNombre.matches("^\\d.*")) {
                        actividadNombre = actividadNombre.substring(4).trim();
                    }
                    actividadNombre = actividadNombre.replaceAll("\\(\\*\\)", "").trim();
                    Optional<Seccion> optionalSeccion = seccionService.getOptionalByNombreContains(actividadNombre);
                    if (optionalSeccion.isPresent()) {
                        if (!lastCellWasSection) {
                            currentSeccion = optionalSeccion.get();
                            lastCellWasSection = true;
                        }
                    } else {
                        lastCellWasSection = false;
                        for (Detalle detalle : detalles) {
                            if (detalle.getActividad().getNombre().equals(actividadNombre) &&
                                    detalle.getActividad().getSeccion().equals(currentSeccion)) {
                                writeMesesData(sheet, rowIndex, 3, detalle.getMeses());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void writeClinker(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 28;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            writeCell(sheet, rowIndex, 1, detalle.getClinker().getCemento());
            writeCell(sheet, rowIndex, 2, detalle.getClinker().getProduccionCemento());
            writeCell(sheet, rowIndex, 3, detalle.getClinker().getProduccionClinker());
            writeCell(sheet, rowIndex, 4, detalle.getClinker().getContenidoCaOClinker());
            writeCell(sheet, rowIndex, 5, detalle.getClinker().getContenidoCaOCaCO3());
            rowIndex++;
        }
    }

    private void writeRefrigerantes(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 22;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);

            if (row == null) {
                sheet.createRow(rowIndex);
            }

            RefrigeranteInstalacion refrigeranteI = detalle.getRefrigeranteInstalacion();
            RefrigeranteOperacion refrigeranteO = detalle.getRefrigeranteOperacion();
            RefrigeranteDisposicion refrigeranteD = detalle.getRefrigeranteDisposicion();

            if (refrigeranteI != null) {
                writeRefrigerantesCommonData(sheet, rowIndex, 1, refrigeranteI);
                writeCell(sheet, rowIndex, 5, refrigeranteI.getFugaInstalacion());
            }

            if (refrigeranteO != null) {
                writeRefrigerantesCommonData(sheet, rowIndex, 7, refrigeranteO);
                writeCell(sheet, rowIndex, 11, refrigeranteO.getAnio());
                writeCell(sheet, rowIndex, 12, refrigeranteO.getFugaUso());
            }

            if (refrigeranteD != null) {
                writeRefrigerantesCommonData(sheet, rowIndex, 14, refrigeranteD);
                writeCell(sheet, rowIndex, 18, refrigeranteD.getFraccionRefrigeranteDisposicion());
                writeCell(sheet, rowIndex, 19, refrigeranteD.getFraccionRefrigeranteRecuperado());
            }

            rowIndex++;
        }
    }

    private void writeFugasSF6(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 21;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);

            if (row == null) {
                sheet.createRow(rowIndex);
            }

            FugaInstalacion fugaI = detalle.getFugaInstalacion();
            FugaOperacion fugaO = detalle.getFugaOperacion();
            FugaDisposicion fugaD = detalle.getFugaDisposicion();

            if (fugaI != null) {
                writeFugasCommonData(sheet, rowIndex, 1, fugaI);
                writeCell(sheet, rowIndex, 4, fugaI.getFugaInstalacion());
            }

            if (fugaO != null) {
                writeFugasCommonData(sheet, rowIndex, 6, fugaO);
                writeCell(sheet, rowIndex, 9, fugaO.getTiempoUso());
                writeCell(sheet, rowIndex, 10, fugaO.getFugaUso());
            }

            if (fugaD != null) {
                writeFugasCommonData(sheet, rowIndex, 12, fugaD);
                writeCell(sheet, rowIndex, 15, fugaD.getFraccionSF6Disposicion());
                writeCell(sheet, rowIndex, 16, fugaD.getFraccionSF6Recuperado());
            }

            rowIndex++;
        }
    }

    private void writePFC(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 22;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);

            if (row == null) {
                sheet.createRow(rowIndex);
            }

            PFCInstalacion pfcI = detalle.getPfcInstalacion();
            PFCOperacion pfcO = detalle.getPfcOperacion();
            PFCDisposicion pfcD = detalle.getPfcDisposicion();

            if (pfcI != null) {
                writePFCCommonData(sheet, rowIndex, 1, pfcI);
                writeCell(sheet, rowIndex, 5, pfcI.getFugaInstalacion());
            }

            if (pfcO != null) {
                writePFCCommonData(sheet, rowIndex, 7, pfcO);
                writeCell(sheet, rowIndex, 11, pfcO.getTiempoUso());
                writeCell(sheet, rowIndex, 12, pfcO.getFugaUso());
            }

            if (pfcD != null) {
                writePFCCommonData(sheet, rowIndex, 14, pfcD);
                writeCell(sheet, rowIndex, 18, pfcD.getFraccionGasPFCDisposicion());
                writeCell(sheet, rowIndex, 19, pfcD.getFraccionGasPFCRecuperado());
            }

            rowIndex++;
        }
    }

    private void writeGanado(Sheet sheet, DatosGenerales datosGenerales) {
        writeCell(sheet, 19, 3, datosGenerales.getGanadoData().getTemperatura());
        int rowIndex = 24;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            updateListCell(sheet, rowIndex, 1, detalle.getGanado().getTipoAnimal().getNombre());
            updateListCell(sheet, rowIndex, 3, detalle.getGanado().getTipoTratamiento().getNombre());
            writeCell(sheet, rowIndex, 5, detalle.getGanado().getPesoPromedioAnimal());
            writeCell(sheet, rowIndex, 6, detalle.getGanado().getCantidadAnualAnimales());
            rowIndex++;
        }
    }

    private void writeFertilizantes(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 24;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            updateListCell(sheet, rowIndex, 1, detalle.getFertilizante().getTipoFertilizante().getNombre());
            updateListCell(sheet, rowIndex, 3, detalle.getFertilizante().getResiduo().getNombre());
            writeCell(sheet, rowIndex, 5, detalle.getFertilizante().getContenidoNitrogeno());
            writeCell(sheet, rowIndex, 6, detalle.getFertilizante().getCantidadEmpleada());
            rowIndex++;
        }
    }

    private void writeEncalado(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 22;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            updateListCell(sheet, rowIndex, 1, detalle.getEncalado().getTipoCal().getNombre());
            writeCell(sheet, rowIndex, 3, detalle.getEncalado().getCantidadAplicada());
            rowIndex++;
        }
    }

    private void writeSuelosGestionados(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 23;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            updateListCell(sheet, rowIndex, 1, detalle.getSueloGestionado().getTipoSuelo().getNombre());
            writeCell(sheet, rowIndex, 3, detalle.getSueloGestionado().getTipoSuelo().getDescripcion());
            writeCell(sheet, rowIndex, 4, detalle.getSueloGestionado().getAreaGestionada());
            rowIndex++;
        }
    }

    private void writeCultivo(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 24;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            updateListCell(sheet, rowIndex, 1, detalle.getCultivoArroz().getTipoCultivo().getNombre());
            writeCell(sheet, rowIndex, 3, detalle.getCultivoArroz().getPeriodoCultivo());
            writeCell(sheet, rowIndex, 4, detalle.getCultivoArroz().getAreaCultivo());
            updateListCell(sheet, rowIndex, 5, detalle.getCultivoArroz().getTipoFertilizante().getNombre());
            updateListCell(sheet, rowIndex, 7, detalle.getCultivoArroz().getResiduo().getNombre());
            writeCell(sheet, rowIndex, 9, detalle.getCultivoArroz().getContenidoNitrogeno());
            writeCell(sheet, rowIndex, 10, detalle.getCultivoArroz().getCantidadEmpleada());
            rowIndex++;
        }
    }

    private void writeBiomasa(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 22;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            updateListCell(sheet, rowIndex, 1, detalle.getQuemaBiomasa().getResiduoAgricola().getNombre());
            writeCell(sheet, rowIndex, 3, detalle.getQuemaBiomasa().getAreaCultiva());
            writeCell(sheet, rowIndex, 4, detalle.getQuemaBiomasa().getAreaQuemada());
            writeCell(sheet, rowIndex, 5, detalle.getQuemaBiomasa().getProduccion());
            rowIndex++;
        }
    }

    private void writeEmbalses(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 22;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            writeCell(sheet, rowIndex, 1, detalle.getEmbalse().getNombre());
            writeCell(sheet, rowIndex, 2, detalle.getEmbalse().getUbicacion());
            updateListCell(sheet, rowIndex, 3, detalle.getEmbalse().getZona().getNombre());
            writeCell(sheet, rowIndex, 5, detalle.getEmbalse().getArea());
            writeCell(sheet, rowIndex, 6, detalle.getEmbalse().getPeriodoLibreHielo());
            writeCell(sheet, rowIndex, 7, detalle.getEmbalse().getFraccionAreaInundada());
            rowIndex++;
        }
    }

    private void writeConsumoElectricidad(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 22;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            writeCell(sheet, rowIndex, 1, detalle.getArea());
            writeCell(sheet, rowIndex, 2, detalle.getSuministro());
            writeMesesData(sheet, rowIndex, 3, detalle.getMeses());
            rowIndex++;
        }
    }

    private void writePerdidas(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 22;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            writeCell(sheet, rowIndex, 1, detalle.getDescripcion());
            writeMesesData(sheet, rowIndex, 2, detalle.getMeses());
            rowIndex++;
        }
    }

    private void writeTransporteMaterial(Sheet sheet, List<Detalle> detalles, int startRowIndex) {
        int rowIndex = startRowIndex;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            writeCell(sheet, rowIndex, 1, detalle.getTransporteMaterial().getDescripcion());
            writeCell(sheet, rowIndex, 2, detalle.getTransporteMaterial().getViajes());
            writeCell(sheet, rowIndex, 3, detalle.getTransporteMaterial().getTramo());
            writeCell(sheet, rowIndex, 4, detalle.getTransporteMaterial().getPesoTransportado());
            writeCell(sheet, rowIndex, 5, detalle.getTransporteMaterial().getDistanciaRecorrida());
            updateListCell(sheet, rowIndex, 6, detalle.getTransporteMaterial().getTipoVehiculo().getNombre());
            rowIndex++;
        }
    }

    private void writeTransporteVehiculo(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 23;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }

            int distanciaRecorridaRowIndex = 2;
            int personasViajandoRowIndex = 3;
            if (detalle.getTransporteVehiculo().getTipoTransporte() != null) {
                updateListCell(sheet, rowIndex, 2, detalle.getTransporteVehiculo().getTipoTransporte().getNombre());
                distanciaRecorridaRowIndex = 3;
                personasViajandoRowIndex = 5;
            }

            writeCell(sheet, rowIndex, 1, detalle.getTransporteVehiculo().getTramo());
            writeCell(sheet, rowIndex, distanciaRecorridaRowIndex, detalle.getTransporteVehiculo().getDistanciaRecorrida());
            writeCell(sheet, rowIndex, personasViajandoRowIndex, detalle.getTransporteVehiculo().getPersonasViajando());
            writeCell(sheet, rowIndex, 4, detalle.getTransporteVehiculo().getVecesRecorrido());
            rowIndex++;
        }
    }

    private void writeTransporteCasaTrabajo(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 23;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);

            if (row == null) {
                sheet.createRow(rowIndex);
            }

            Optional<TransporteCasaTrabajo> custer = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 1).findFirst();
            Optional<TransporteCasaTrabajo> combi = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 2).findFirst();
            Optional<TransporteCasaTrabajo> bus = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 3).findFirst();
            Optional<TransporteCasaTrabajo> tren = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 4).findFirst();
            Optional<TransporteCasaTrabajo> metropolitano = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 5).findFirst();
            Optional<TransporteCasaTrabajo> taxi = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 6).findFirst();
            Optional<TransporteCasaTrabajo> mototaxi = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 7).findFirst();
            Optional<TransporteCasaTrabajo> autoDB5 = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 8).findFirst();
            Optional<TransporteCasaTrabajo> autoGasohol = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 9).findFirst();
            Optional<TransporteCasaTrabajo> autoGLP = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 10).findFirst();
            Optional<TransporteCasaTrabajo> autoGNV = detalle.getTransporteCasaTrabajos().stream().filter(t -> t.getTipoMovilidad().getId() == 11).findFirst();

            if (custer.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 1, custer.orElseThrow());
            }
            if (combi.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 7, combi.orElseThrow());
            }
            if (bus.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 13, bus.orElseThrow());
            }
            if (tren.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 19, tren.orElseThrow());
            }
            if (metropolitano.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 25, metropolitano.orElseThrow());
            }
            if (taxi.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 31, taxi.orElseThrow());
            }
            if (mototaxi.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 37, mototaxi.orElseThrow());
            }
            if (autoDB5.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 43, autoDB5.orElseThrow());
            }
            if (autoGasohol.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 49, autoGasohol.orElseThrow());
            }
            if (autoGLP.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 55, autoGLP.orElseThrow());
            }
            if (autoGNV.isPresent()) {
                writeTransporteCasaTrabajoCommonData(sheet, rowIndex, 61, autoGNV.orElseThrow());
            }

            rowIndex++;
        }
    }

    private void writeConsumoAgua(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 22;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            writeCell(sheet, rowIndex, 1, detalle.getSuperficie());
            writeCell(sheet, rowIndex, 2, detalle.getMedidor());
            writeMesesData(sheet, rowIndex, 3, detalle.getMeses());
            rowIndex++;
        }
    }

    private void writeConsumoPapel(Sheet sheet, List<Detalle> detalles) {
        for (Detalle detalle : detalles) {
            for (int rowIndex = 22; rowIndex <= 28; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    String tipoHojaNombre = readCell(row, 1);
                    if (detalle.getConsumoPapel().getTipoHoja().getNombre().equals(tipoHojaNombre)) {
                            ConsumoPapel consumoPapel = detalle.getConsumoPapel();
                            writeCell(sheet, rowIndex, 2, consumoPapel.getComprasAnuales());
                            writeCell(sheet, rowIndex, 5, consumoPapel.getReciclado());
                            writeCell(sheet, rowIndex, 6, consumoPapel.getCertificado());
                            writeCell(sheet, rowIndex, 7, consumoPapel.getDensidad());
                            break;
                        }

                }
            }
        }
    }

    private void writeGeneracionIndirectaNF3(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 23;
        for (Detalle detalle : detalles) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                sheet.createRow(rowIndex);
            }
            writeCell(sheet, rowIndex, 1, detalle.getGeneracionIndirectaNF3().getNumeroPantallas());
            writeCell(sheet, rowIndex, 2, detalle.getGeneracionIndirectaNF3().getAlto());
            writeCell(sheet, rowIndex, 3, detalle.getGeneracionIndirectaNF3().getAncho());
            rowIndex++;
        }
    }

    private void writeGeneracionResiduos(Sheet sheet, List<Detalle> detalles) {
        int rowIndex = 31;
        for (Detalle detalle : detalles) {
            writeCell(sheet, 19, 2, detalle.getGeneracionResiduos().getAnioHuella());
            writeCell(sheet, 22, 2, detalle.getGeneracionResiduos().getPrecipitacion());
            writeCell(sheet, 19, 6, detalle.getGeneracionResiduos().getAnioInicio());
            writeCell(sheet, 22, 6, detalle.getGeneracionResiduos().getTemperatura());
            updateListCell(sheet, 27, 2, detalle.getGeneracionResiduos().getContenidoGrasas() ? "Sí" : "No");
            writeCell(sheet, 39, 2, detalle.getGeneracionResiduos().getTasaCrecimiento());
            updateListCell(sheet, 24, 2, detalle.getGeneracionResiduos().getCondicionSEDS().getNombre());
            for (GeneracionResiduosDetalle generacionResiduosDetalle : detalle.getGeneracionResiduos().getGeneracionResiduosDetalles()) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    sheet.createRow(rowIndex);
                }
                writeCell(sheet, rowIndex, 1, generacionResiduosDetalle.getAnio());
                writeCell(sheet, rowIndex, 2, generacionResiduosDetalle.getProductosMadera());
                writeCell(sheet, rowIndex, 3, generacionResiduosDetalle.getProductosPapel());
                writeCell(sheet, rowIndex, 4, generacionResiduosDetalle.getResiduos());
                writeCell(sheet, rowIndex, 5, generacionResiduosDetalle.getTextiles());
                writeCell(sheet, rowIndex, 6, generacionResiduosDetalle.getJardines());
                writeCell(sheet, rowIndex, 7, generacionResiduosDetalle.getPaniales());
                writeCell(sheet, rowIndex, 8, generacionResiduosDetalle.getOtros());
                rowIndex++;
            }
        }
    }

    private void writeTransporteCasaTrabajoCommonData(Sheet sheet, int rowIndex, int startColIndex, TransporteCasaTrabajo transporte) {
        writeCell(sheet, rowIndex, startColIndex++, transporte.getDescripcionPersonal());
        writeCell(sheet, rowIndex, startColIndex++, transporte.getTrabajadores());
        writeCell(sheet, rowIndex, startColIndex++, transporte.getViajesSemanales());
        writeCell(sheet, rowIndex, startColIndex++, transporte.getDiasLaborales());
        writeCell(sheet, rowIndex, startColIndex, transporte.getDistanciaViaje());
    }

    private void writePFCCommonData(Sheet sheet, int rowIndex, int startColIndex, PFC pfc) {
        writeCell(sheet, rowIndex, startColIndex++, pfc.getDescripcionEquipo());
        updateListCell(sheet, rowIndex, startColIndex++, pfc.getTipoPFC().getNombre());
        writeCell(sheet, rowIndex, startColIndex++, pfc.getNumeroEquipos());
        writeCell(sheet, rowIndex, startColIndex, pfc.getCapacidadCarga());
    }

    private void writeFugasCommonData(Sheet sheet, int rowIndex, int startColIndex, Fuga fuga) {
        writeCell(sheet, rowIndex, startColIndex++, fuga.getDescripcionEquipo());
        writeCell(sheet, rowIndex, startColIndex++, fuga.getNumeroEquipos());
        writeCell(sheet, rowIndex, startColIndex, fuga.getCapacidadCarga());
    }

    private void writeRefrigerantesCommonData(Sheet sheet, int rowIndex, int startColIndex, Refrigerante refrigerante) {
        updateListCell(sheet, rowIndex, startColIndex++, refrigerante.getTipoEquipo().getNombre());
        updateListCell(sheet, rowIndex, startColIndex++, refrigerante.getTipoRefrigerante().getNombre());
        writeCell(sheet, rowIndex, startColIndex++, refrigerante.getNumeroEquipos());
        writeCell(sheet, rowIndex, startColIndex, refrigerante.getCapacidadCarga());
    }

    private void writeMesesData(Sheet sheet, int rowIndex, int startColIndex, Meses meses) {
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

    private String readCell(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell != null) {
            String value = cell.getStringCellValue().trim();
            return value.isBlank() ? null : value;
        }
        return null;
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
