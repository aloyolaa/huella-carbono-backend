package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ganado")
@AllArgsConstructor
@NoArgsConstructor
public class Ganado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_animal_id", nullable = false)
    private TipoAnimal tipoAnimal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_tratamiento_id", nullable = false)
    private TipoTratamiento tipoTratamiento;

    @Column(name = "peso_promedio_animal", nullable = false)
    private Double pesoPromedioAnimal;

    @Column(name = "cantidad_anual_animales", nullable = false)
    private Integer cantidadAnualAnimales;
}