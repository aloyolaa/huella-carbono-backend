package com.towers.huellacarbonobackend.entity.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "empresa")
@NoArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @Column(name = "ruc", nullable = false)
    private String ruc;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "telefono", nullable = false)
    private Double telefono;

    @Column(name = "correo", nullable = false)
    private String correo;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmpresaArchivo> empresaArchivos = new LinkedHashSet<>();

    public Empresa(Long id) {
        this.id = id;
    }
}