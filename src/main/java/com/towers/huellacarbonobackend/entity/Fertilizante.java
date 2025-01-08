package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fertilizante")
@NoArgsConstructor
public class Fertilizante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

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

    public Fertilizante(Long id, TipoFertilizante tipoFertilizante, Residuo residuo, Double contenidoNitrogeno, Double cantidadEmpleada) {
        this.id = id;
        this.tipoFertilizante = tipoFertilizante;
        this.residuo = residuo;
        this.contenidoNitrogeno = contenidoNitrogeno;
        this.cantidadEmpleada = cantidadEmpleada;
    }
}