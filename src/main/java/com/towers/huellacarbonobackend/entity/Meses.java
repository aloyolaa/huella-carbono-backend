package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "meses")
@AllArgsConstructor
@NoArgsConstructor
public class Meses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "enero")
    private Double enero;

    @Column(name = "febrero")
    private Double febrero;

    @Column(name = "marzo")
    private Double marzo;

    @Column(name = "abril")
    private Double abril;

    @Column(name = "mayo")
    private Double mayo;

    @Column(name = "junio")
    private Double junio;

    @Column(name = "julio")
    private Double julio;

    @Column(name = "agosto")
    private Double agosto;

    @Column(name = "septiembre")
    private Double septiembre;

    @Column(name = "octubre")
    private Double octubre;

    @Column(name = "noviembre")
    private Double noviembre;

    @Column(name = "diciembre")
    private Double diciembre;
}