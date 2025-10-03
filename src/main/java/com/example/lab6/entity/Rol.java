package com.example.lab6.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Usuario> usuarios;
}
