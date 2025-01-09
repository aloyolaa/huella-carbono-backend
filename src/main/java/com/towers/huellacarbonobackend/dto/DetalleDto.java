package com.towers.huellacarbonobackend.dto;

public record DetalleDto(
        Long id,
        String area,
        String suministro,
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
        TransporteVehiculoDto transporteVehiculo
) {
}
