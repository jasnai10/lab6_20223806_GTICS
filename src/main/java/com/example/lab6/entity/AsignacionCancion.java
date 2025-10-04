package com.example.lab6.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "asignaciones_cancion")
public class AsignacionCancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancion_id", nullable = false)
    private CancionCriolla cancion;

    @Column(name = "intentos")
    private Integer intentos = 0;

    @Column(name = "adivinada")
    private Boolean adivinada = false;

    public AsignacionCancion() {
    }

    public AsignacionCancion(Integer id, Usuario usuario, CancionCriolla cancion, Integer intentos, Boolean adivinada) {
        this.setId(id);
        this.setUsuario(usuario);
        this.setCancion(cancion);
        this.setIntentos(intentos);
        this.setAdivinada(adivinada);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public CancionCriolla getCancion() {
        return cancion;
    }

    public void setCancion(CancionCriolla cancion) {
        this.cancion = cancion;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public Boolean getAdivinada() {
        return adivinada;
    }

    public void setAdivinada(Boolean adivinada) {
        this.adivinada = adivinada;
    }
}
