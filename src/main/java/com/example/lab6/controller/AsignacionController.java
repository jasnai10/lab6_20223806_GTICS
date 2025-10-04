package com.example.lab6.controller;

import com.example.lab6.entity.AsignacionCancion;
import com.example.lab6.entity.CancionCriolla;
import com.example.lab6.repository.AsignacionCancionRepository;
import com.example.lab6.repository.CancionCriollaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/asignaciones")
public class AsignacionController {

    private final AsignacionCancionRepository asignacionRepo;
    private final CancionCriollaRepository cancionRepo;

    public AsignacionController(AsignacionCancionRepository asignacionRepo,
                                     CancionCriollaRepository cancionRepo) {
        this.asignacionRepo = asignacionRepo;
        this.cancionRepo = cancionRepo;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String verSolicitudes(Model model) {
        List<AsignacionCancion> solicitudes = asignacionRepo.findAll()
                .stream().filter(a -> a.getCancion() == null && !a.getAdivinada()).toList();

        List<CancionCriolla> canciones = cancionRepo.findAll();

        model.addAttribute("solicitudes", solicitudes);
        model.addAttribute("canciones", canciones);
        return "admin/asignaciones";
    }

    @PostMapping("/asignar/{idAsignacion}")
    @PreAuthorize("hasRole('ADMIN')")
    public String asignarCancion(@PathVariable Integer idAsignacion,
                                 @RequestParam Integer idCancion) {
        AsignacionCancion asignacion = asignacionRepo.findById(idAsignacion).orElse(null);
        CancionCriolla cancion = cancionRepo.findById(idCancion).orElse(null);

        if (asignacion != null && cancion != null) {
            asignacion.setCancion(cancion);
            asignacionRepo.save(asignacion);
        }
        return "redirect:/admin/asignaciones";
    }
}
