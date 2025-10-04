package com.example.lab6.controller;

import com.example.lab6.entity.Mesa;
import com.example.lab6.repository.MesaRepository;
import com.example.lab6.repository.ReservaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/mesas")
public class AdminReservaController {

    private final MesaRepository mesaRepo;
    private final ReservaRepository reservaRepo;

    public AdminReservaController(MesaRepository mesaRepo, ReservaRepository reservaRepo) {
        this.mesaRepo = mesaRepo;
        this.reservaRepo = reservaRepo;
    }


    @GetMapping
    public String listarMesas(Model model) {
        model.addAttribute("mesas", mesaRepo.findAll());
        model.addAttribute("ocupadas", mesaRepo.countByDisponibleFalse());
        model.addAttribute("libres", mesaRepo.countByDisponibleTrue());
        return "admin/mesas";
    }


    @PostMapping("/liberar/{id}")
    public String liberarMesa(@PathVariable Integer id) {
        Mesa mesa = mesaRepo.findById(id).orElse(null);
        if (mesa != null) {
            mesa.setDisponible(true);
            mesaRepo.save(mesa);
        }
        return "redirect:/admin/mesas";
    }


    @PostMapping("/editar/{id}")
    public String editarCapacidad(@PathVariable Integer id,
                                  @RequestParam Integer capacidad) {
        Mesa mesa = mesaRepo.findById(id).orElse(null);
        if (mesa != null && capacidad <= 4) {
            mesa.setCapacidad(capacidad);
            mesaRepo.save(mesa);
        }
        return "redirect:/admin/mesas";
    }
}
