package com.towers.huellacarbonobackend.entity.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "datos_generales", indexes = {
        @Index(name = "idx_datosgenerales_anio", columnList = "anio, empresa_id"),
        @Index(name = "idx_datosgenerales_anio_mes", columnList = "anio, mes, empresa_id, archivo_id")
})
public class DatosGenerales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "El Nombre de Datos Generales es requerido")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "El Cargo de Datos Generales es requerido")
    @Column(name = "cargo", nullable = false)
    private String cargo;

    @NotBlank(message = "El Correo de Datos Generales es requerido")
    @Column(name = "correo", nullable = false)
    private String correo;

    @NotBlank(message = "La Locación de Datos Generales es requerida")
    @Column(name = "locacion", nullable = false)
    private String locacion;

    @Column(name = "comentarios")
    private String comentarios;

    @Column(name = "anio")
    private Integer anio;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "emision")
    private Double emision;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "archivo_id", nullable = false)
    private Archivo archivo;

    @OneToOne(mappedBy = "datosGenerales", cascade = CascadeType.ALL, orphanRemoval = true)
    private GanadoData ganadoData;

    @OneToMany(mappedBy = "datosGenerales", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy
    private List<Detalle> detalles = new ArrayList<>();
}