package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consumo_papel")
@AllArgsConstructor
@NoArgsConstructor
public class ConsumoPapel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_hoja_id", nullable = false)
    private TipoHoja tipoHoja;

    @Column(name = "compras_anuales", nullable = false)
    private Integer comprasAnuales;

    @Column(name = "unidad", nullable = false)
    private String unidad;

    @Column(name = "reciclado", nullable = false)
    private Double reciclado;

    @Column(name = "certificado", nullable = false)
    private String certificado;

    @Column(name = "densidad", nullable = false)
    private Double densidad;
}