package com.towers.huellacarbonobackend.dto;

import java.util.List;

public record DetalleDto(
        Long id,
        String area,
        String suministro,
        Double superficie,
        String medidor,
        String descripcion,
        Long tipoCombustible,
        MesesDto meses,
        Long categoriaInstitucion,
        Long actividad,
        ClinkerDto clinker,
        RefrigeranteDto refrigerante,
        FugaDto fuga,
        PFCDto pfc,
        GanadoDto ganado,
        FertilizanteDto fertilizante,
        EncaladoDto encalado,
        SueloGestionadoDto sueloGestionado,
        CultivoArrozDto cultivoArroz,
        QuemaBiomadaDto quemaBiomasa,
        EmbalseDto embalse,
        TransporteMaterialDto transporteMaterial,
        TransporteVehiculoDto transporteVehiculo,
        ConsumoPapelDto consumoPapel,
        GeneracionIndirectaNF3Dto generacionIndirectaNF3,
        GeneracionResiduosDto generacionResiduos,
        List<TransporteCasaTrabajoDto> transporteCasaTrabajos
) {
}
