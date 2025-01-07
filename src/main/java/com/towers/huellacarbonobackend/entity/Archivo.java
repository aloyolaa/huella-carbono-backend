package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "archivo")
@NoArgsConstructor
public class Archivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "clave", nullable = false)
    private String clave;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(optional = false)
    @JoinColumn(name = "alcance_id", nullable = false)
    private Alcance alcance;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Archivo archivo = (Archivo) o;
        return Objects.equals(id, archivo.id) && Objects.equals(clave, archivo.clave) && Objects.equals(nombre, archivo.nombre) && Objects.equals(alcance, archivo.alcance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clave, nombre, alcance);
    }

    public Archivo(Long id) {
        this.id = id;
    }
}