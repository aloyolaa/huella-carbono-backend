package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cultivo_arroz")
@AllArgsConstructor
@NoArgsConstructor
public class CultivoArroz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_cultivo_id", nullable = false)
    private TipoCultivo tipoCultivo;

    @Column(name = "periodo_cultivo", nullable = false)
    private Integer periodoCultivo;

    @Column(name = "area_cultivo", nullable = false)
    private Double areaCultivo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_fertilizante_id", nullable = false)
    private TipoFertilizante tipoFertilizante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "residuo_id", nullable = false)
    private Residuo residuo;

    @Column(name = "contenido_nitrogeno", nullable = false)
    private Double contenidoNitrogeno;

    @Column(name = "cantidad_empleada", nullable = false)
    private Double cantidadEmpleada;
}