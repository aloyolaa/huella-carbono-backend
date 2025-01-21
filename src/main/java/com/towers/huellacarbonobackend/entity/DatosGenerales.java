package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "datos_generales")
public class DatosGenerales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "cargo", nullable = false)
    private String cargo;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "locacion", nullable = false)
    private String locacion;

    @Column(name = "comentarios")
    private String comentarios;

    @Column(name = "anio")
    private Integer anio;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "archivo_id", nullable = false)
    private Archivo archivo;

    @OneToMany(mappedBy = "datosGenerales", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Detalle> detalles = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.anio = Year.now().getValue();
    }
}