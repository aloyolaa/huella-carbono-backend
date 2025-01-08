package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "suelo_gestionado")
@NoArgsConstructor
public class SueloGestionado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_suelo_id", nullable = false)
    private TipoSuelo tipoSuelo;

    @Column(name = "area_gestionada", nullable = false)
    private Double areaGestionada;

    public SueloGestionado(Long id, TipoSuelo tipoSuelo, Double areaGestionada) {
        this.id = id;
        this.tipoSuelo = tipoSuelo;
        this.areaGestionada = areaGestionada;
    }
}