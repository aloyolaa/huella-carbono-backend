package com.towers.huellacarbonobackend.entity.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "encalado")
@AllArgsConstructor
@NoArgsConstructor
public class Encalado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_cal_id", nullable = false)
    private TipoCal tipoCal;

    @Column(name = "cantidad_aplicada", nullable = false)
    private Double cantidadAplicada;
}