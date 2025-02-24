package com.towers.huellacarbonobackend.service.file;

import com.towers.huellacarbonobackend.entity.data.*;
import com.towers.huellacarbonobackend.service.data.*;
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
public class ImportAllService {
    private final DataService dataService;
    private final TipoCombustibleService tipoCombustibleService;
    private final CategoriaInstitucionService categoriaInstitucionService;
    private final SeccionService seccionService;
    private final ActividadService actividadService;
    private final AccionService accionService;
    private final TipoEquipoService tipoEquipoService;
    private final TipoRefrigeranteService tipoRefrigeranteService;
    private final TipoPFCService tipoPFCService;
    private final TipoAnimalService tipoAnimalService;
    private final TipoTratamientoService tipoTratamientoService;
    private final TipoFertilizanteService tipoFertilizanteService;
    private final ResiduoService residuoService;
    private final TipoCalService tipoCalService;
    private final TipoSueloService tipoSueloService;
    private final TipoCultivoService tipoCultivoService;
    private final ResiduoAgricolaService residuoAgricolaService;
    private final ZonaService zonaService;
    private final TipoVehiculoService tipoVehiculoService;
    private final TipoTransporteService tipoTransporteService;
    private final CondicionSEDSService condicionSEDSService;
    private final TipoHojaService tipoHojaService;

    @Transactional
    public void handleExcelImport(Long empresaId, Long archivoId, MultipartFile file) {
        Optional<DatosGenerales> optionalDatosGenerales = dataService.getOptionalByEmpresaAndAnio(empresaId, archivoId, null);

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
                case 1:
                    readGeneracionYOtraEnergia(sheet, datosGenerales, 23, 36);
                    break;
                case 2:
                    readFuentesFijas(sheet, datosGenerales);
                    break;
                case 3:
                    readFuentesMovilesYRefinacion(sheet, datosGenerales, 50);
                    break;
                case 4:
                    readFuentesMovilesYRefinacion(sheet, datosGenerales, 37);
                    break;
                case 5:
                    readVenteoYQuema(sheet, datosGenerales);
                    break;
                case 6:
                    readFugasProcesos(sheet, datosGenerales);
                    break;
                case 7:
                    readClinker(sheet, datosGenerales);
                    break;
                case 8:
                    readRefrigerantes(sheet, datosGenerales);
                    break;
                case 9:
                    readFugasSF6(sheet, datosGenerales);
                    break;
                case 10:
                    readPFC(sheet, datosGenerales);
                    break;
                case 11:
                    readGanado(sheet, datosGenerales);
                    break;
                case 12:
                    readFertilizantes(sheet, datosGenerales);
                    break;
                case 13:
                    readEncalado(sheet, datosGenerales);
                    break;
                case 14:
                    readSuelosGestionados(sheet, datosGenerales);
                    break;
                case 15:
                    readCultivo(sheet, datosGenerales);
                    break;
                case 16:
                    readBiomasa(sheet, datosGenerales);
                    break;
                case 17:
                    readEmbalses(sheet, datosGenerales);
                    break;
                case 18:
                    readConsumoElectricidad(sheet, datosGenerales);
                    break;
                case 19, 20:
                    readPerdidas(sheet, datosGenerales);
                    break;
                case 21:
                    readGeneracionYOtraEnergia(sheet, datosGenerales, 22, 35);
                    break;
                case 22:
                    readTransporteMaterial(sheet, datosGenerales, 22);
                    break;
                case 23:
                    readTransporteMaterial(sheet, datosGenerales, 24);
                    break;
                case 24, 25:
                    readTransporteVehiculo(sheet, datosGenerales);
                    break;
                case 26:
                    readTransporteCasaTrabajo(sheet, datosGenerales);
                    break;
                case 27:
                    readConsumoAgua(sheet, datosGenerales);
                    break;
                case 28:
                    readConsumoPapel(sheet, datosGenerales);
                    break;
                case 29:
                    readGeneracionIndirectaNF3(sheet, datosGenerales);
                    break;
                case 30:
                    readGeneracionResiduos(sheet, datosGenerales);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported archivo id: " + archivoId);
            }
            dataService.save(datosGenerales);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readCommonData(Sheet sheet, DatosGenerales datosGenerales) {
        datosGenerales.setNombre(readCell(sheet.getRow(7), 2));
        datosGenerales.setCargo(readCell(sheet.getRow(8), 2));
        datosGenerales.setCorreo(readCell(sheet.getRow(9), 2));
        datosGenerales.setLocacion(readCell(sheet.getRow(11), 2));
        datosGenerales.setComentarios(readCell(sheet.getRow(13), 2));
    }

    private void readGeneracionYOtraEnergia(Sheet sheet, DatosGenerales datosGenerales, int startRowIndex, int endRowIndex) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = startRowIndex; rowIndex <= endRowIndex; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String tipoCombustibleNombre = readCell(row, 1);
                if (tipoCombustibleNombre != null) {
                    Meses meses = readMeses(row, 3);
                    if (hasDataInAnyMonth(meses)) {
                        Detalle detalle = new Detalle();
                        tipoCombustibleNombre = tipoCombustibleNombre.replaceAll("\\(\\*\\)", "").trim();
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

    private void readFuentesFijas(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; rowIndex <= 35; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String tipoCombustibleNombre = readCell(row, 1);
                if (tipoCombustibleNombre != null) {
                    Meses meses = readMeses(row, 4);
                    if (hasDataInAnyMonth(meses)) {
                        Detalle detalle = new Detalle();
                        tipoCombustibleNombre = tipoCombustibleNombre.replaceAll("\\(\\*\\)", "").trim();
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

    private void readFuentesMovilesYRefinacion(Sheet sheet, DatosGenerales datosGenerales, int endRowIndex) {
        List<Detalle> detalles = new ArrayList<>();
        Seccion currentSeccion = null;
        boolean lastCellWasSection = false;

        for (int rowIndex = 22; rowIndex <= endRowIndex; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String tipoCombustibleNombre = readCell(row, 1);
                if (tipoCombustibleNombre != null) {
                    Optional<Seccion> optionalSeccion = seccionService.getOptionalByNombre(tipoCombustibleNombre);
                    if (optionalSeccion.isPresent()) {
                        if (!lastCellWasSection) {
                            currentSeccion = optionalSeccion.get();
                            lastCellWasSection = true;
                        }
                    } else {
                        lastCellWasSection = false;
                        Meses meses = readMeses(row, 3);
                        if (hasDataInAnyMonth(meses)) {
                            Detalle detalle = new Detalle();
                            tipoCombustibleNombre = tipoCombustibleNombre.replaceAll("\\(\\*\\)", "").trim();
                            detalle.setTipoCombustible(tipoCombustibleService.getByNombreAndArchivoAndSeccion(tipoCombustibleNombre, datosGenerales.getArchivo().getId(), currentSeccion.getId()));
                            detalle.setMeses(meses);
                            detalle.setDatosGenerales(datosGenerales);
                            detalles.add(detalle);
                        }
                    }
                }
            }
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readVenteoYQuema(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        Seccion currentSeccion = null;

        for (int rowIndex = 22; rowIndex <= 29; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String actividadNombre = readCell(row, 1);
                String accionNombre = readCell(row, 3);
                if (actividadNombre != null && accionNombre == null) {
                    Optional<Seccion> optionalSeccion = seccionService.getOptionalByNombre(actividadNombre);
                    if (optionalSeccion.isPresent()) {
                        currentSeccion = optionalSeccion.orElseThrow();
                    }
                }
                if (actividadNombre != null && accionNombre != null) {
                    Meses meses = readMeses(row, 4);
                    if (hasDataInAnyMonth(meses)) {
                        Detalle detalle = new Detalle();
                        actividadNombre = actividadNombre.replaceAll("\\(\\*\\)", "").trim();
                        Accion accion = accionService.getByNombre(accionNombre);
                        detalle.setActividad(actividadService.getByNombreAndArchivoAndSeccionAndAccion(actividadNombre, datosGenerales.getArchivo().getId(), currentSeccion.getId(), accion.getId()));
                        detalle.setMeses(meses);
                        detalle.setDatosGenerales(datosGenerales);
                        detalles.add(detalle);
                    }
                }
            }
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readFugasProcesos(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
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
                    Optional<Seccion> optionalSeccion = seccionService.getOptionalByNombreContains(actividadNombre);
                    if (optionalSeccion.isPresent()) {
                        if (!lastCellWasSection) {
                            currentSeccion = optionalSeccion.get();
                            lastCellWasSection = true;
                        }
                    } else {
                        lastCellWasSection = false;
                        Meses meses = readMeses(row, 3);
                        if (hasDataInAnyMonth(meses)) {
                            Detalle detalle = new Detalle();
                            actividadNombre = actividadNombre.replaceAll("\\(\\*\\)", "").trim();
                            detalle.setActividad(actividadService.getByNombreAndArchivoAndSeccion(actividadNombre, datosGenerales.getArchivo().getId(), currentSeccion.getId()));
                            detalle.setMeses(meses);
                            detalle.setDatosGenerales(datosGenerales);
                            detalles.add(detalle);
                        }
                    }
                }
            }
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readClinker(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 28; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String cemento = readCell(row, 1);
            if (cemento == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setClinker(
                    new Clinker(
                            null,
                            cemento,
                            readDoubleCell(row, 2),
                            readDoubleCell(row, 3),
                            readDoubleCell(row, 4),
                            readDoubleCell(row, 5)
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readRefrigerantes(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String tipoEquipoI = readListCell(row, 1);
            String tipoEquipoO = readListCell(row, 7);
            String tipoEquipoD = readListCell(row, 14);
            if (tipoEquipoI == null && tipoEquipoO == null && tipoEquipoD == null) {
                break;
            }

            Detalle detalle = new Detalle();

            if (tipoEquipoI != null) {
                detalle.setRefrigeranteInstalacion(
                        new RefrigeranteInstalacion(
                                null,
                                tipoEquipoService.getByNombre(tipoEquipoI),
                                tipoRefrigeranteService.getByNombre(readListCell(row, 2)),
                                readIntegerCell(row, 3),
                                readDoubleCell(row, 4),
                                readDoubleCell(row, 5)
                        )
                );
            }

            if (tipoEquipoO != null) {
                detalle.setRefrigeranteOperacion(
                        new RefrigeranteOperacion(
                                null,
                                tipoEquipoService.getByNombre(tipoEquipoO),
                                tipoRefrigeranteService.getByNombre(readListCell(row, 8)),
                                readIntegerCell(row, 9),
                                readDoubleCell(row, 10),
                                readDoubleCell(row, 11),
                                readDoubleCell(row, 12)
                        )
                );
            }

            if (tipoEquipoD != null) {
                detalle.setRefrigeranteDisposicion(
                        new RefrigeranteDisposicion(
                                null,
                                tipoEquipoService.getByNombre(tipoEquipoD),
                                tipoRefrigeranteService.getByNombre(readListCell(row, 15)),
                                readIntegerCell(row, 16),
                                readDoubleCell(row, 17),
                                readDoubleCell(row, 18),
                                readDoubleCell(row, 19)
                        )
                );
            }

            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readFugasSF6(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 21; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);

            if (row == null) {
                break;
            }

            String equipoI = readCell(row, 1);
            String equipoO = readCell(row, 6);
            String equipoD = readCell(row, 12);

            if (equipoI == null && equipoO == null && equipoD == null) {
                break;
            }

            Detalle detalle = new Detalle();

            if (equipoI != null) {
                detalle.setFugaInstalacion(
                        new FugaInstalacion(
                                null,
                                equipoI,
                                readIntegerCell(row, 2),
                                readDoubleCell(row, 3),
                                readDoubleCell(row, 4)
                        )
                );
            }

            if (equipoO != null) {
                detalle.setFugaOperacion(
                        new FugaOperacion(
                                null,
                                equipoO,
                                readIntegerCell(row, 7),
                                readDoubleCell(row, 8),
                                readDoubleCell(row, 9),
                                readDoubleCell(row, 10)
                        )
                );
            }

            if (equipoD != null) {
                detalle.setFugaDisposicion(
                        new FugaDisposicion(
                                null,
                                equipoD,
                                readIntegerCell(row, 13),
                                readDoubleCell(row, 14),
                                readDoubleCell(row, 15),
                                readDoubleCell(row, 16)
                        )
                );
            }

            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readPFC(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);

            if (row == null) {
                break;
            }

            String equipoI = readCell(row, 1);
            String equipoO = readCell(row, 7);
            String equipoD = readCell(row, 14);

            if (equipoI == null && equipoO == null && equipoD == null) {
                break;
            }

            Detalle detalle = new Detalle();

            if (equipoI != null) {
                detalle.setPfcInstalacion(
                        new PFCInstalacion(
                                null,
                                equipoI,
                                tipoPFCService.getByNombre(readCell(row, 2)),
                                readIntegerCell(row, 3),
                                readDoubleCell(row, 4),
                                readDoubleCell(row, 5)
                        )
                );
            }

            if (equipoO != null) {
                detalle.setPfcOperacion(
                        new PFCOperacion(
                                null,
                                equipoO,
                                tipoPFCService.getByNombre(readCell(row, 8)),
                                readIntegerCell(row, 9),
                                readDoubleCell(row, 10),
                                readDoubleCell(row, 11),
                                readDoubleCell(row, 12)
                        )
                );
            }

            if (equipoD != null) {
                detalle.setPfcDisposicion(
                        new PFCDisposicion(
                                null,
                                equipoD,
                                tipoPFCService.getByNombre(readCell(row, 15)),
                                readIntegerCell(row, 16),
                                readDoubleCell(row, 17),
                                readDoubleCell(row, 18),
                                readDoubleCell(row, 19)
                        )
                );
            }

            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readGanado(Sheet sheet, DatosGenerales datosGenerales) {
        datosGenerales.setGanadoData(new GanadoData(null, readDoubleCell(sheet.getRow(19), 3), datosGenerales));
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 24; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String tipoAnimal = readListCell(row, 1);
            if (tipoAnimal == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setGanado(
                    new Ganado(
                            null,
                            tipoAnimalService.getByNombre(tipoAnimal),
                            tipoTratamientoService.getByNombre(readListCell(row, 3)),
                            readDoubleCell(row, 5),
                            readIntegerCell(row, 6)
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readFertilizantes(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 24; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String tipoFertilizante = readListCell(row, 1);
            if (tipoFertilizante == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setFertilizante(
                    new Fertilizante(
                            null,
                            tipoFertilizanteService.getByNombre(tipoFertilizante),
                            residuoService.getByNombre(readListCell(row, 3)),
                            readDoubleCell(row, 5),
                            readDoubleCell(row, 6)
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readEncalado(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String tipoCal = readListCell(row, 1);
            if (tipoCal == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setEncalado(
                    new Encalado(
                            null,
                            tipoCalService.getByNombre(tipoCal),
                            readDoubleCell(row, 3)
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readSuelosGestionados(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 23; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String tipoSuelo = readListCell(row, 1);
            if (tipoSuelo == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setSueloGestionado(
                    new SueloGestionado(
                            null,
                            tipoSueloService.getByNombre(tipoSuelo),
                            readDoubleCell(row, 4)
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readCultivo(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 24; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String tipoCultivo = readListCell(row, 1);
            if (tipoCultivo == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setCultivoArroz(
                    new CultivoArroz(
                            null,
                            tipoCultivoService.getByNombre(tipoCultivo),
                            readIntegerCell(row, 3),
                            readDoubleCell(row, 4),
                            tipoFertilizanteService.getByNombre(readListCell(row, 5)),
                            residuoService.getByNombre(readListCell(row, 7)),
                            readDoubleCell(row, 9),
                            readDoubleCell(row, 10)
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readBiomasa(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String residuoAgricola = readListCell(row, 1);
            if (residuoAgricola == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setQuemaBiomasa(
                    new QuemaBiomasa(
                            null,
                            residuoAgricolaService.getByNombre(residuoAgricola),
                            readDoubleCell(row, 3),
                            readDoubleCell(row, 4),
                            readDoubleCell(row, 5)
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readEmbalses(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String embalse = readListCell(row, 1);
            if (embalse == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setEmbalse(
                    new Embalse(
                            null,
                            embalse,
                            readCell(row, 2),
                            zonaService.getByNombre(readListCell(row, 3)),
                            readDoubleCell(row, 5),
                            readIntegerCell(row, 6),
                            readDoubleCell(row, 7)
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readConsumoElectricidad(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String area = readCell(row, 1);
            if (area == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setArea(area);
            detalle.setSuministro(readCell(row, 2));
            detalle.setMeses(readMeses(row, 3));
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readPerdidas(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String descripcion = readCell(row, 1);
            if (descripcion == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setDescripcion(descripcion);
            detalle.setMeses(readMeses(row, 2));
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readTransporteMaterial(Sheet sheet, DatosGenerales datosGenerales, int startRowIndex) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = startRowIndex; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String descripcion = readCell(row, 1);
            if (descripcion == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setTransporteMaterial(
                    new TransporteMaterial(
                            null,
                            descripcion,
                            readIntegerCell(row, 2),
                            readCell(row, 3),
                            readDoubleCell(row, 4),
                            readDoubleCell(row, 5),
                            tipoVehiculoService.getByNombre(readListCell(row, 6))
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readTransporteVehiculo(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 23; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String tramo = readCell(row, 1);
            if (tramo == null) {
                break;
            }

            int distanciaRecorridaRowIndex = 2;
            int personasViajandoRowIndex = 3;
            TipoTransporte tipoTransporte = null;
            if (datosGenerales.getArchivo().getId() == 25) {
                tipoTransporte = tipoTransporteService.getByNombre(readListCell(row, 2));
                distanciaRecorridaRowIndex = 3;
                personasViajandoRowIndex = 5;
            }

            Detalle detalle = new Detalle();
            detalle.setTransporteVehiculo(
                    new TransporteVehiculo(
                            null,
                            tramo,
                            readDoubleCell(row, distanciaRecorridaRowIndex),
                            readIntegerCell(row, personasViajandoRowIndex),
                            readIntegerCell(row, 4),
                            tipoTransporte
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readTransporteCasaTrabajo(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 23; ; rowIndex++) {
            List<TransporteCasaTrabajo> transportes = new ArrayList<>();
            Row row = sheet.getRow(rowIndex);

            if (row == null) {
                break;
            }

            String custer = readCell(row, 1);
            String combi = readCell(row, 7);
            String bus = readCell(row, 13);
            String tren = readCell(row, 19);
            String metropolitano = readCell(row, 25);
            String taxi = readCell(row, 31);
            String mototaxi = readCell(row, 37);
            String autoDB5 = readCell(row, 43);
            String autoGasohol = readCell(row, 49);
            String autoGLP = readCell(row, 55);
            String autoGNV = readCell(row, 61);

            if (custer == null && combi == null && bus == null &&
                    tren == null && metropolitano == null && taxi == null &&
                    mototaxi == null && autoDB5 == null && autoGasohol == null &&
                    autoGLP == null && autoGNV == null) {
                break;
            }

            Detalle detalle = new Detalle();

            if (custer != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, custer, 1L));
            }
            if (combi != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, combi, 2L));
            }
            if (bus != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, bus, 3L));
            }
            if (tren != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, tren, 4L));
            }
            if (metropolitano != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, metropolitano, 5L));
            }
            if (taxi != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, taxi, 6L));
            }
            if (mototaxi != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, mototaxi, 7L));
            }
            if (autoDB5 != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, autoDB5, 8L));
            }
            if (autoGasohol != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, autoGasohol, 9L));
            }
            if (autoGLP != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, autoGLP, 10L));
            }
            if (autoGNV != null) {
                transportes.add(createTransporteCasaTrabajo(row, detalle, autoGNV, 11L));
            }

            detalle.setTransporteCasaTrabajos(transportes);
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readConsumoAgua(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            String value = readCell(row, 1);
            if (value == null) {
                break;
            }
            Double superficie = Double.parseDouble(value);
            Detalle detalle = new Detalle();
            detalle.setSuperficie(superficie);
            detalle.setMedidor(String.valueOf(readIntegerCell(row, 2)));
            detalle.setMeses(readMeses(row, 3));
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readConsumoPapel(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 22; rowIndex <= 28; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String tipoHojaNombre = readCell(row, 1);
                if (tipoHojaNombre != null) {
                    Integer comprasAnuales = readIntegerCell(row, 2);
                    if (comprasAnuales != null) {
                        Detalle detalle = new Detalle();
                        detalle.setConsumoPapel(
                                new ConsumoPapel(
                                        null,
                                        tipoHojaService.getTipoHojaByNombre(tipoHojaNombre),
                                        comprasAnuales,
                                        readCell(row, 4),
                                        readDoubleCell(row, 5),
                                        readCell(row, 6),
                                        readDoubleCell(row, 7)
                                ));
                        detalle.setDatosGenerales(datosGenerales);
                        detalles.add(detalle);
                    }
                }
            }
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readGeneracionIndirectaNF3(Sheet sheet, DatosGenerales datosGenerales) {
        List<Detalle> detalles = new ArrayList<>();
        for (int rowIndex = 23; ; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            Integer pantallas = readIntegerCell(row, 1);
            if (pantallas == null) {
                break;
            }
            Detalle detalle = new Detalle();
            detalle.setGeneracionIndirectaNF3(
                    new GeneracionIndirectaNF3(
                            null,
                            pantallas,
                            readDoubleCell(row, 2),
                            readDoubleCell(row, 3)
                    )
            );
            detalle.setDatosGenerales(datosGenerales);
            detalles.add(detalle);
        }
        datosGenerales.setDetalles(detalles);
    }

    private void readGeneracionResiduos(Sheet sheet, DatosGenerales datosGenerales) {
        Detalle detalle = new Detalle();
        GeneracionResiduos generacionResiduos = new GeneracionResiduos();
        List<GeneracionResiduosDetalle> generacionResiduosDetalles = new ArrayList<>();
        generacionResiduos.setAnioHuella(readIntegerCell(sheet.getRow(19), 2));
        generacionResiduos.setPrecipitacion(readDoubleCell(sheet.getRow(22), 2));
        generacionResiduos.setAnioInicio(readIntegerCell(sheet.getRow(19), 6));
        generacionResiduos.setTemperatura(readDoubleCell(sheet.getRow(22), 6));
        generacionResiduos.setContenidoGrasas(readListCell(sheet.getRow(27), 2) == "Sí");
        generacionResiduos.setTasaCrecimiento(readDoubleCell(sheet.getRow(39), 2));
        generacionResiduos.setCondicionSEDS(condicionSEDSService.getByNombre(readListCell(sheet.getRow(24), 2)));
        for (int rowIndex = 31; rowIndex <= 36; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                break;
            }
            Integer anio = readIntegerCell(row, 1);
            if (anio == null) {
                break;
            }

            GeneracionResiduosDetalle generacionResiduosDetalle = new GeneracionResiduosDetalle(
                    null,
                    anio,
                    readDoubleCell(row, 2),
                    readDoubleCell(row, 3),
                    readDoubleCell(row, 4),
                    readDoubleCell(row, 5),
                    readDoubleCell(row, 6),
                    readDoubleCell(row, 7),
                    readDoubleCell(row, 8),
                    generacionResiduos
            );

            generacionResiduosDetalles.add(generacionResiduosDetalle);
        }
        generacionResiduos.setGeneracionResiduosDetalles(generacionResiduosDetalles);
        detalle.setGeneracionResiduos(generacionResiduos);
        detalle.setDatosGenerales(datosGenerales);
        datosGenerales.setDetalles(List.of(detalle));
    }

    private TransporteCasaTrabajo createTransporteCasaTrabajo(Row row, Detalle detalle, String descripcion, Long tipoMovilidadId) {
        int addCol = (tipoMovilidadId.intValue() - 1) * 6;
        return new TransporteCasaTrabajo(
                null,
                descripcion,
                readIntegerCell(row, 2 + addCol),
                readIntegerCell(row, 3 + addCol),
                readIntegerCell(row, 4 + addCol),
                readDoubleCell(row, 5 + addCol),
                new TipoMovilidad(tipoMovilidadId),
                detalle
        );
    }

    private boolean hasDataInAnyMonth(Meses meses) {
        return meses.getEnero() != null || meses.getFebrero() != null || meses.getMarzo() != null ||
                meses.getAbril() != null || meses.getMayo() != null || meses.getJunio() != null ||
                meses.getJulio() != null || meses.getAgosto() != null || meses.getSeptiembre() != null ||
                meses.getOctubre() != null || meses.getNoviembre() != null || meses.getDiciembre() != null;
    }

    private Meses readMeses(Row row, int startColIndex) {
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

    private String readListCell(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell != null && cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        }
        return null;
    }

    private String readCell(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell != null) {
            String value = cell.getStringCellValue().trim();
            return value.isBlank() ? null : value;
        }
        return null;
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
}
