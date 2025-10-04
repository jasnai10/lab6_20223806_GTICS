package com.example.lab6.repository;

import com.example.lab6.entity.Reserva;
import com.example.lab6.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    Optional<Reserva> findByUsuario(Usuario usuario);
}
