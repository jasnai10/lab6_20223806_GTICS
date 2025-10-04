package com.example.lab6.repository;

import com.example.lab6.entity.AsignacionCancion;
import com.example.lab6.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsignacionCancionRepository extends JpaRepository<AsignacionCancion, Integer> {

    Optional<AsignacionCancion> findByUsuario(Usuario usuario);
    Optional<AsignacionCancion> findByUsuarioAndAdivinadaFalse(Usuario usuario);

    List<AsignacionCancion> findTop10ByAdivinadaTrueOrderByIntentosAsc();
}
