package com.example.lab6.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "canciones_criollas")
public class CancionCriolla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "letra")
    private String letra;

    @OneToMany(mappedBy = "cancion", cascade = CascadeType.ALL)
    private List<AsignacionCancion> asignaciones;

    public CancionCriolla() {
    }

    public CancionCriolla(Integer id, String titulo, String letra) {
        this.setId(id);
        this.setTitulo(titulo);
        this.setLetra(letra);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public List<AsignacionCancion> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<AsignacionCancion> asignaciones) {
        this.asignaciones = asignaciones;
    }
}
