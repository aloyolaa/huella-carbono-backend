package com.towers.huellacarbonobackend.entity.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "suelo_gestionado")
@AllArgsConstructor
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
}