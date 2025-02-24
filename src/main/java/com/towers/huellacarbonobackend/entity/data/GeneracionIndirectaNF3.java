package com.towers.huellacarbonobackend.entity.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "generacion_indirecta_nf_3")
@AllArgsConstructor
@NoArgsConstructor
public class GeneracionIndirectaNF3 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "numero_pantallas", nullable = false)
    private Integer numeroPantallas;

    @Column(name = "alto", nullable = false)
    private Double alto;

    @Column(name = "ancho", nullable = false)
    private Double ancho;
}