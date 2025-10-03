package com.example.lab6.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "intenciones")
public class Intencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "fecha")
    private Timestamp fecha;
}
