package com.towers.huellacarbonobackend.service.file.excel.imp;

import com.towers.huellacarbonobackend.service.file.excel.format.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ImportConfig {
    @Bean
    public Map<Long, ImportOperation> importOperations(
            GeneracionYOtraEnergiaFormat generacionYOtraEnergiaFormat,
            FuentesFijasFormat fuentesFijasFormat,
            FuentesMovilesYRefinacionFormat fuentesMovilesYRefinacionFormat,
            VenteoYQuemaFormat venteoYQuemaFormat,
            FugasProcesosFormat fugasProcesosFormat,
            ClinkerFormat clinkerFormat,
            RefrigerantesFormat refrigerantesFormat,
            FugasSF6Format fugasSF6Format,
            PFCFormat pfcFormat,
            GanadoFormat ganadoFormat,
            FertilizantesFormat fertilizantesFormat,
            EncaladoFormat encaladoFormat,
            SuelosGestionadosFormat suelosGestionadosFormat,
            CultivoFormat cultivoFormat,
            BiomasaFormat biomasaFormat,
            EmbalsesFormat embalsesFormat,
            ConsumoElectricidadFormat consumoElectricidadFormat,
            PerdidasFormat perdidasFormat,
            TransporteMaterialFormat transporteMaterialFormat,
            TransporteVehiculoFormat transporteVehiculoFormat,
            TransporteCasaTrabajoFormat transporteCasaTrabajoFormat,
            ConsumoAguaFormat consumoAguaFormat,
            ConsumoPapelFormat consumoPapelFormat,
            GeneracionIndirectaNF3Format generacionIndirectaNF3Format,
            GeneracionResiduosFormat generacionResiduosFormat
    ) {
        Map<Long, ImportOperation> map = new HashMap<>();
        map.put(1L, generacionYOtraEnergiaFormat);
        map.put(2L, fuentesFijasFormat);
        map.put(3L, fuentesMovilesYRefinacionFormat);
        map.put(4L, fuentesMovilesYRefinacionFormat);
        map.put(5L, venteoYQuemaFormat);
        map.put(6L, fugasProcesosFormat);
        map.put(7L, clinkerFormat);
        map.put(8L, refrigerantesFormat);
        map.put(9L, fugasSF6Format);
        map.put(10L, pfcFormat);
        map.put(11L, ganadoFormat);
        map.put(12L, fertilizantesFormat);
        map.put(13L, encaladoFormat);
        map.put(14L, suelosGestionadosFormat);
        map.put(15L, cultivoFormat);
        map.put(16L, biomasaFormat);
        map.put(17L, embalsesFormat);
        map.put(18L, consumoElectricidadFormat);
        map.put(19L, perdidasFormat);
        map.put(20L, perdidasFormat);
        map.put(21L, generacionYOtraEnergiaFormat);
        map.put(22L, transporteMaterialFormat);
        map.put(23L, transporteMaterialFormat);
        map.put(24L, transporteVehiculoFormat);
        map.put(25L, transporteVehiculoFormat);
        map.put(26L, transporteCasaTrabajoFormat);
        map.put(27L, consumoAguaFormat);
        map.put(28L, consumoPapelFormat);
        map.put(29L, generacionIndirectaNF3Format);
        map.put(30L, generacionResiduosFormat);
        return map;
    }
}
