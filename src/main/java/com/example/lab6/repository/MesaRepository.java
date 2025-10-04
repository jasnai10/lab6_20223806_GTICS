package com.example.lab6.repository;

import com.example.lab6.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    List<Mesa> findByDisponibleTrue();
    long countByDisponibleTrue();
    long countByDisponibleFalse();
}
